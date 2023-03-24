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
        final double startingX = (Game.BACKGROUND_WIDTH / 2.0) + 20;
        final double startingY = (Game.BACKGROUND_HEIGHT / 2.0) + 20;

        controller.renderSprite(this.player.getSprite(), startingX, startingY);
    }
}
