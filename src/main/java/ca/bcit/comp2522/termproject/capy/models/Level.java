package ca.bcit.comp2522.termproject.capy.models;

import ca.bcit.comp2522.termproject.capy.Game;
import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.controllers.LevelController;
import javafx.scene.Scene;

import java.time.LocalDateTime;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;

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

    /**
     * Instantiate a new Level.
     *
     * @param player     the Player object that will be playing in the level
     * @param numEnemies the amount of enemies that will be rendered in the level
     */
    public Level(final Player player, final int numEnemies) {
        this.lastDamageTimes = new ArrayList<>();
        this.droppedSugarCane = new ArrayList<>();
        for (int i = 0; i < numEnemies; i++) {
            this.lastDamageTimes.add(LocalDateTime.now());
        }
        this.controller = (LevelController) Helpers.getFxmlController("level-view.fxml");
        this.scene = this.controller.getScene();

        this.player = player;
        this.enemies = generateEnemies(numEnemies);
        initializeGameObjects();

        this.swampBoundary = controller.getSwampBorder();

        setUpPlayer();
        controller.renderSprite(enemies.get(0).getSprite(), 0, 0);

        updatePlayerOverlayInformation();
    }

    private void initializeGameObjects() {
        setUpPlayer();
        for (Enemy enemy : enemies) {
            controller.renderSprite(enemy.getSprite(),
                    Math.random() * Game.BACKGROUND_WIDTH,
                    Math.random() * Game.BACKGROUND_HEIGHT);
        }
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

    /**
     * Reset the state of the level back to default (beginning).
     */
    public void resetLevel() {
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
            }
        }
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
    private ArrayList<Enemy> generateEnemies(final int amount) {
        ArrayList<Enemy> newEnemies = new ArrayList<>();
        int gridRows = (int) Math.sqrt(amount);
        int gridCols = amount / gridRows;

        double cellWidth = (double) Game.BACKGROUND_WIDTH / gridCols;
        double cellHeight = (double) Game.BACKGROUND_HEIGHT / gridRows;

        double safeDistance = 500; // Set a safe distance around the player

        int count = 0;
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                if (count >= amount) {
                    break;
                }

                int speed = 1;
                int difficulty = 1;

                Enemy enemy = new Enemy(speed, difficulty);

                double enemyX;
                double enemyY;

                do {
                    enemyX = col * cellWidth + Math.random() * cellWidth;
                    enemyY = row * cellHeight + Math.random() * cellHeight;
                } while (!isSpawnLocationSafe(enemyX, enemyY, player, safeDistance));

                enemy.getSprite().setLayoutX(enemyX);
                enemy.getSprite().setLayoutY(enemyY);

                newEnemies.add(enemy);

                count++;
            }
        }
        return newEnemies;
    }

    /*
    Checks if a spawn location is safe.
     */
    private boolean isSpawnLocationSafe(
            final double x,
            final double y,
            final Player playerSprite,
            final double safeDistance) {
        double deltaX = playerSprite.getSprite().getLayoutX() - x;
        double deltaY = playerSprite.getSprite().getLayoutY() - y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance >= safeDistance;
    }
}
