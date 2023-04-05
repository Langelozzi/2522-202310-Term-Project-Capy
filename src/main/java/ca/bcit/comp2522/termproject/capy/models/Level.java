package ca.bcit.comp2522.termproject.capy.models;
import ca.bcit.comp2522.termproject.capy.utils.Helpers;
import ca.bcit.comp2522.termproject.capy.CapyApplication;
import ca.bcit.comp2522.termproject.capy.utils.KeyboardInputController;
import ca.bcit.comp2522.termproject.capy.controllers.LevelController;
import ca.bcit.comp2522.termproject.capy.utils.MouseInputController;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;

import java.time.Duration;
import java.time.LocalDateTime;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;
import java.util.Iterator;

import static ca.bcit.comp2522.termproject.capy.utils.Helpers.playPickUpSound;

/**
 * Level class stores data related to a level of the game, including control over the LevelController.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Level {

    // INITIALIZATION  =================================================================================================

    private final Scene scene;
    private final LevelController controller;
    private final Player player;
    private final Ellipse swampBoundary;
    private final ArrayList<Enemy> enemies;
    private ArrayList<LocalDateTime> lastDamageTimes;
    private final ArrayList<SugarCane> droppedSugarCane;
    private final double playerStartingXPosition = (Game.BACKGROUND_WIDTH / 2.0) - 20;
    private final double playerStartingYPosition = (Game.BACKGROUND_HEIGHT / 2.0) - 20;
    private boolean started;
    private final KeyboardInputController keyboardInputController;
    private final Game game;



    /**
     * Instantiate a new Level.
     *
     * @param player     the Player object that will be playing in the level
     * @param numEnemies the amount of enemies that will be rendered in the level
     */
    public Level(final Game game, final Player player, final int numEnemies, final int enemyDifficulty) {
        this.lastDamageTimes = new ArrayList<>();
        this.droppedSugarCane = new ArrayList<>();
        for (int i = 0; i < numEnemies; i++) {
            this.lastDamageTimes.add(LocalDateTime.now());
        }
        this.controller = (LevelController) Helpers.getFxmlController("level-view.fxml");
        this.scene = this.controller.getScene();
        this.player = player;
        this.enemies = generateEnemies(numEnemies, enemyDifficulty);
        this.swampBoundary = controller.getSwampBorder();
        this.started = false;
        this.keyboardInputController = new KeyboardInputController();
        this.game = game;
    }

    public void initializeGameObjects() {
        setUpPlayer();
        spawnEnemies();
    }

    public void bindUserControls() {
        keyboardInputController.assignKeyboardInput(
                this.player, this.scene, this.getSwampBoundary()
        );

        MouseInputController rotationController = new MouseInputController();
        rotationController.makeCursorRotatable(this.player, this.getScene());
        rotationController.handleShooting(this.player, this.getScene(), bullet -> {
            this.getGameLayer().getChildren().add(bullet.getBullet());
            bullet.getBullet().toBack();
            this.player.getSprite().toFront();
        });
    }

    // GETTERS AND SETTERS =============================================================================================

    /**
     * Return the scene object of the level.
     *
     * @return the scene object of the level
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Returns the boundary of the swamp area in the game.
     *
     * @return The boundary of the swamp area in the game.
     */
    public Ellipse getSwampBoundary() {
        return this.swampBoundary;
    }

    /**
     * Returns the list of enemies in the game.
     *
     * @return The list of enemies in the game.
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Returns the list of last damage times of enemies in the game.
     *
     * @return The list of last damage times of enemies in the game.
     */
    public ArrayList<LocalDateTime> getLastDamageTimes() {
        return lastDamageTimes;
    }

    // LEVEL RELATED ===================================================================================================

    /**
     * Returns the game layer of the game.
     *
     * @return The game layer of the game.
     */
    public Pane getGameLayer() {
        return this.controller.getGameLayer();
    }

    public boolean play() {
        // everything that we want to happen only once at the beginning of the level goes in this if statement
        if (!this.started) {
            this.started = true;
            this.initializeGameObjects();
            this.bindUserControls();

            CapyApplication.getStage().setScene(this.getScene());
            CapyApplication.getStage().show();
        }
        // this stuff will run every frame
        updatePlayerOverlayInformation();
        updateEnemies();
        updateBullets();
        checkSugarCaneCollected();
        if (this.enemies.size() == 0 && allSugarCaneCollected()) {
            keyboardInputController.removeKeyboardInput();
            return true;
        }
        return false;
    }

    /**
     * Reset the state of the level back to default (beginning).
     */
    public void resetLevel() {
        this.started = false;
        this.resetPlayer();
        this.resetEnemies();
        this.updatePlayerOverlayInformation();
    }

    // PLAYER ACTIONS ==================================================================================================

    /*
     * Initialize the position and properties of the player on the Level.
     */
    private void setUpPlayer() {
        controller.renderSprite(this.player.getSprite(), this.playerStartingXPosition, this.playerStartingYPosition);
    }

    /*
     * Reset the player back to its default state and position.
     */
    private void resetPlayer() {
        this.player.getSprite().setLayoutX(this.playerStartingXPosition);
        this.player.getSprite().setLayoutY(this.playerStartingYPosition);
        this.player.reset();
    }

    /**
     * Set the player health bar and sugar can information for the level overlay.
     */
    public void updatePlayerOverlayInformation() {
        this.controller.getSugarCanePoints().setText(String.valueOf(this.player.getPoints()));

        if (this.player.getHitPoints() <= 20) {
            this.controller.getHealthBar().setStyle("-fx-accent: red");
        } else if (this.player.getHitPoints() <= 50) {
            this.controller.getHealthBar().setStyle("-fx-accent: #f8e407");
        }

        final double progressAmount = this.player.getHitPoints() / 100.0;
        this.controller.getHealthBar().setProgress(progressAmount);
    }

    // SUGARCANE ACTIONS ===============================================================================================

    /**
     * Drops the sugar cane carried by the enemy.
     *
     * @param enemy the enemy whose sugar cane will be dropped
     */
    public void dropSugarCane(final Enemy enemy) {
        final double sugarCaneX = enemy.getSprite().getLayoutX();
        final double sugarCaneY = enemy.getSprite().getLayoutY();
        final SugarCane sugarCane = enemy.getSugarCane();

        this.controller.renderSprite(sugarCane.getSprite(), sugarCaneX, sugarCaneY);
        sugarCane.setDropped(true);
        this.droppedSugarCane.add(sugarCane);

    }

    /**
     * Checks if the player has collected the dropped sugar cane.
     */
    public void checkSugarCaneCollected() {
        for (SugarCane sugarCane : this.droppedSugarCane) {
            if (sugarCane.checkCollision(this.player) && !sugarCane.isCollected()) {
                sugarCane.setCollected(true);
                this.player.collectSugarCane(sugarCane);
                this.controller.getGameLayer().getChildren().remove(sugarCane.getSprite());
                playPickUpSound();

            }
        }
    }

    private boolean allSugarCaneCollected() {
        for (SugarCane sugarCane : this.droppedSugarCane) {
            if (!sugarCane.isCollected()) {
                return false;
            }
        }
        return true;
    }

    // ENEMY ACTIONS ===================================================================================================

    /*
    Reset all the enemies back to their default spawn locations and states.
     */
    private void resetEnemies() {
        // TODO: Logic to reset the enemies to default state
    }

    /*
    Generate an ArrayList of enemies and populate it with amount number of enemies.
     */
    private ArrayList<Enemy> generateEnemies(final int amount, final int difficulty) {
        ArrayList<Enemy> newEnemies = new ArrayList<>();

        final int enemySpeed = 1;
        for (int count = 0; count < amount; count++) {
            newEnemies.add(new Enemy(enemySpeed, difficulty));
        }

        return newEnemies;
    }

    private void spawnEnemies() {
        for (Enemy enemy : this.enemies) {
            double[] spawnCoords;
            boolean locationFound = false;
            int maxAttempts = 100; // Maximum attempts to find a suitable spawn location
            int attemptCount = 0;

            while (!locationFound && attemptCount < maxAttempts) {
                spawnCoords = this.chooseSpawnLocation();
                double spawnX = spawnCoords[0];
                double spawnY = spawnCoords[1];

                if (isSpawnLocationSafe(spawnX, spawnY, this.player, 100.0) && !isTooCloseToOtherEnemies(spawnX, spawnY, 50.0)) {
                    this.controller.renderSprite(enemy.getSprite(), spawnX, spawnY);
                    locationFound = true;
                }

                attemptCount++;
            }
        }
    }

    private boolean isTooCloseToOtherEnemies(double x, double y, double minDistance) {
        for (Enemy enemy : this.enemies) {
            double deltaX = enemy.getSprite().getLayoutX() - x;
            double deltaY = enemy.getSprite().getLayoutY() - y;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance < minDistance) {
                return true;
            }
        }

        return false;
    }


    private double[] chooseSpawnLocation() {
        double centerX = Game.BACKGROUND_WIDTH / 2.0;
        double centerY = Game.BACKGROUND_HEIGHT / 2.0;
        double radiusX = swampBoundary.getRadiusX();
        double radiusY = swampBoundary.getRadiusY();

        double theta = Math.random() * 2 * Math.PI;
        double enemyX = centerX + radiusX * Math.cos(theta);
        double enemyY = centerY + radiusY * Math.sin(theta);

        return new double[] { enemyX, enemyY };
    }




    /*
    Checks if a spawn location is safe.
     */
    private boolean isSpawnLocationSafe(
            final double x,
            final double y,
            final Player playerSprite,
            final double safeDistance
    ) {
        double deltaX = playerSprite.getSprite().getLayoutX() - x;
        double deltaY = playerSprite.getSprite().getLayoutY() - y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance >= safeDistance;
    }

    // ENEMY LOGIC =====================================================================================================

    private void updateEnemies() {
        LocalDateTime currentTime = LocalDateTime.now();
        for (int i = 0; i < this.getEnemies().size(); i++) {
            Enemy enemy = this.getEnemies().get(i);
            enemy.update(player, this.getEnemies());
            if (player.isCollidingWithEnemy(enemy)) {
                // Check if the player's hit points are already 0 or below
                if (player.getHitPoints() <= 0) {
                    // Game over logic
                    game.handleGameOver();
                } else if (Duration.between(this.getLastDamageTimes().get(i), currentTime).getSeconds() >= 1) {
                    int damage = 20; // Player takes 20 damage per collision
                    player.setHitPoints(player.getHitPoints() - damage);

                    // Update the player's health bar and other overlay information
                    this.updatePlayerOverlayInformation();
                    this.getLastDamageTimes().set(i, currentTime);
                    // Check if the player's hit points have reached 0 or below after taking damage
                    if (player.getHitPoints() <= 0) {
                        // Game over logic
                        game.handleGameOver();

                    }
                }
            }
        }
    }



    private void updateBullets() {
        // Update bullets
        for (Bullet bullet : player.getBullets()) {
            bullet.update();
        }

        // Check for bullet collisions with enemies
        Iterator<Bullet> bulletIterator = player.getBullets().iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            boolean collided = false;

            Iterator<Enemy> enemyIterator = this.getEnemies().iterator();
            while (enemyIterator.hasNext() && !collided) {
                Enemy enemy = enemyIterator.next();
                if (enemy.isHitByBullet(bullet)) {
                    collided = true;

                    // Increment the enemy's hits taken and remove it if it has taken 5 hits
                    enemy.setHitsTaken(enemy.getHitsTaken() + 1);
                    if (enemy.getHitsTaken() >= 5) {
                         enemyIterator.remove(); // Remove the enemy from the list

                        // Remove the enemy from the game layer
                        this.getGameLayer().getChildren().remove(enemy.getSprite());
                        enemy.setDeadSprite(); // Set the enemy sprite to the dead crocodile

                        this.dropSugarCane(enemy);

                        // TODO: Add points system
                    }
                    bulletIterator.remove(); // Remove the bullet from the list

                    // Remove the bullet from the game layer
                    this.getGameLayer().getChildren().remove(bullet.getBullet());
                }
            }

            // Remove bullets that are off-screen
            if (!collided && isOffScreen(bullet.getBullet())) {
                bulletIterator.remove();

                // Remove the bullet from the game layer
                this.getGameLayer().getChildren().remove(bullet.getBullet());
            }
        }
    }

    private boolean isOffScreen(final Circle bullet) {
        double x = bullet.getCenterX();
        double y = bullet.getCenterY();

        return x < 0 || x > Game.BACKGROUND_WIDTH || y < 0 || y > Game.BACKGROUND_HEIGHT;
    }

    // Level.java
    public boolean isPlayerDead() {
        return player.getHitPoints() <= 0;
    }


}
