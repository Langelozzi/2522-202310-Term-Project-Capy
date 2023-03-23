package ca.bcit.comp2522.termproject.capy.models;

import ca.bcit.comp2522.termproject.capy.CapyApplication;
import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.controllers.MouseInputController;
import ca.bcit.comp2522.termproject.capy.controllers.LevelController;
import ca.bcit.comp2522.termproject.capy.controllers.KeyboardInputController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Level {
    private final Scene scene;
    private final LevelController controller;

    private final Player player;
    private final ArrayList<Enemy> enemies;

    public Level(final Player player, final int numEnemies) {
        this.controller = Helpers.getFxmlController("level-view.fxml");
        this.scene = this.controller.getScene();

        this.player = player;
        this.enemies = generateEnemies(numEnemies);

        setUpPlayer();
        controller.renderSprite(enemies.get(0).getSprite(), 0, 0);
    }

    public Scene getScene() {
        return this.scene;
    }

    private ArrayList<Enemy> generateEnemies(final int amount) {
        ArrayList<Enemy> newEnemies = new ArrayList<>();
        for (int count = 0; count < amount; count++) {
            newEnemies.add(new Enemy(1));
        }
        return newEnemies;
    }

    private void setUpPlayer() {
        final int startingX = (LevelController.BACKGROUND_WIDTH / 2) - 20;
        final int startingY = (LevelController.BACKGROUND_HEIGHT / 2) - 20;

        controller.renderSprite(this.player.getSprite(), startingX, startingY);

        KeyboardInputController movementController = new KeyboardInputController();
        movementController.makeMovable(this.player, this.scene);

        MouseInputController rotationController = new MouseInputController();
        rotationController.makeCursorRotatable(this.player, this.scene);
    }
}
