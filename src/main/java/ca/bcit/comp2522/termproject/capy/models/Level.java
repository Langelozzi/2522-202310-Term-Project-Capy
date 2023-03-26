package ca.bcit.comp2522.termproject.capy.models;

import ca.bcit.comp2522.termproject.capy.Game;
import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.controllers.LevelController;
import javafx.scene.Scene;

import javafx.animation.AnimationTimer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Level class stores data related to a level of the game, including control over the LevelController.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Level {
    private final Scene scene;
    private final LevelController controller;

    private final Player player;
    private final ArrayList<Enemy> enemies;

    private final double playerStartingXPosition = (Game.BACKGROUND_WIDTH / 2.0) - 20;
    private final double playerStartingYPosition = (Game.BACKGROUND_HEIGHT / 2.0) - 20;
    private ArrayList<LocalDateTime> lastDamageTimes;

    /**
     * Instantiate a new Level.
     *
     * @param player     the Player object that will be playing in the level
     * @param numEnemies the amount of enemies that will be rendered in the level
     */
    public Level(final Player player, final int numEnemies) {
        this.lastDamageTimes = new ArrayList<>();
        for (int i = 0; i < numEnemies; i++) {
            this.lastDamageTimes.add(LocalDateTime.now());
        }
        this.controller = (LevelController) Helpers.getFxmlController("level-view.fxml");
        this.scene = this.controller.getScene();

        this.player = player;
        this.enemies = generateEnemies(numEnemies);
        initializeGameObjects();

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

    /**
     * Return the scene object of the level.
     *
     * @return the scene object of the level
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Reset the state of the level back to default (beginning).
     */
    public void resetLevel() {
        this.resetPlayer();
        this.resetEnemies();
    }

    /**
    Set the player health bar and sugar can information for the level overlay.
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

    /*
    Reset the player back to its default state and position.
     */
    private void resetPlayer() {
        this.player.getSprite().setLayoutX(this.playerStartingXPosition);
        this.player.getSprite().setLayoutY(this.playerStartingYPosition);
    }

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
    Initialize the position and properties of the player on the Level.
     */
    private void setUpPlayer() {
        controller.renderSprite(this.player.getSprite(), this.playerStartingXPosition, this.playerStartingYPosition);
    }
    /**
     * Starts the game loop to continuously update the enemies in the game.
     * The game loop uses an AnimationTimer to call the updateEnemies method in each frame.
     * The handle method of the AnimationTimer is implemented as an anonymous inner class.
     */
    public void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                updateEnemies();
            }
        };
        gameLoop.start();
    }
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

    // THIS VERSION MAKES THE HEALTH BAR JUST REMAIN 0 INSTEAD OF FLASHING LIKE THE ONE COMMENTED OUT BELOW
    private void updateEnemies() {
        LocalDateTime currentTime = LocalDateTime.now();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update(player, enemies);
            if (player.isCollidingWithEnemy(enemy)) {
                // Check if the player's hit points are already 0 or below
                if (player.getHitPoints() <= 0) {
                    // TODO: Game over logic
                } else if (Duration.between(this.lastDamageTimes.get(i), currentTime).getSeconds() >= 1) {
                    int damage = 20; // Player takes 20 damage per collision
                    player.setHitPoints(player.getHitPoints() - damage);
                    updatePlayerOverlayInformation(); // Update the player's health bar and other overlay information
                    this.lastDamageTimes.set(i, currentTime);
                    // TODO: Check if the player's hit points have reached 0 or below after taking damage
                    if (player.getHitPoints() <= 0) {
                        // Game over logic
                    }
                }
            }
        }
    }
// THIS MAKES THE HP BAR FLASH IN RED WHEN IT REACHES ZERO
//    private void updateEnemies() {
//        LocalDateTime currentTime = LocalDateTime.now();
//        for (int i = 0; i < enemies.size(); i++) {
//            Enemy enemy = enemies.get(i);
//            enemy.update(player, enemies);
//            if (player.isCollidingWithEnemy(enemy)) {
//                if (Duration.between(this.lastDamageTimes.get(i), currentTime).getSeconds() >= 1) {
//                    int damage = 20; // Player takes 20 damage per collision
//                    player.setHitPoints(player.getHitPoints() - damage);
//                    // TODO: You can handle the game over scenario here if the player's health reaches 0
//                    if (player.getHitPoints() <= 0) {
//                        // TODO: Game over logic
//                    }
//                    updatePlayerOverlayInformation(); // Update the player's health bar and other overlay information
//                    this.lastDamageTimes.set(i, currentTime);
//                }
//            }
//        }
//    }
}
