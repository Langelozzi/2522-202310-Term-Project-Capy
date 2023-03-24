package ca.bcit.comp2522.termproject.capy.models;

import ca.bcit.comp2522.termproject.capy.Game;
import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.controllers.LevelController;
import javafx.scene.Scene;

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

    /**
     * Instantiate a new Level.
     * @param player the Player object that will be playing in the level
     * @param numEnemies the amount of enemies that will be rendered in the level
     */
    public Level(final Player player, final int numEnemies) {
        this.controller = (LevelController) Helpers.getFxmlController("level-view.fxml");
        this.scene = this.controller.getScene();

        this.player = player;
        this.enemies = generateEnemies(numEnemies);

        setUpPlayer();
        controller.renderSprite(enemies.get(0).getSprite(), 0, 0);
    }

    /**
     * Return the scene object of the level.
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

    private void resetPlayer() {
        this.player.getSprite().setLayoutX(this.playerStartingXPosition);
        this.player.getSprite().setLayoutY(this.playerStartingYPosition);
    }

    private void resetEnemies() {
        // TODO: Logic to reset the enemies to default state
    }

    /*
    Generate an ArrayList of enemies and populate it with amount number of enemies.
     */
    private ArrayList<Enemy> generateEnemies(final int amount) {
        ArrayList<Enemy> newEnemies = new ArrayList<>();
        for (int count = 0; count < amount; count++) {
            newEnemies.add(new Enemy(1));
        }
        return newEnemies;
    }

    /*
    Initialize the position and properties of the player on the Level.
     */
    private void setUpPlayer() {
        controller.renderSprite(this.player.getSprite(), this.playerStartingXPosition, this.playerStartingYPosition);
    }
}
