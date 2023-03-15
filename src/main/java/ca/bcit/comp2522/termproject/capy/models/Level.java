package ca.bcit.comp2522.termproject.capy.models;

import ca.bcit.comp2522.termproject.capy.CapyApplication;
import ca.bcit.comp2522.termproject.capy.controllers.LevelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Level {
    private final Scene scene;
    private final URL fxmlFile;

    private final Player player;
    private final ArrayList<Enemy> enemies;

    private final LevelController controller;

    public Level(final Player player, final int numEnemies) {
        this.player = player;
        this.enemies = generateEnemies(numEnemies);

        this.fxmlFile = CapyApplication.class.getResource("level-view.fxml");
        FXMLLoader loader = new FXMLLoader(this.fxmlFile);

        try {
            Parent root = (Parent) loader.load();
            this.controller =  loader.getController();

            controller.loadSprite(enemies.get(0).getSprite());
            controller.loadSprite(this.player.getSprite());

            this.scene = new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
}
