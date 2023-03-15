package ca.bcit.comp2522.termproject.capy.models;

import ca.bcit.comp2522.termproject.capy.CapyApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Level {
    private final Scene scene;
    private final URL fxmlFile;

    private final Player player;
    private final ArrayList<Enemy> enemies;

    public Level(final Player player, final int numEnemies) {
        this.player = player;
        this.enemies = generateEnemies(numEnemies);

        this.fxmlFile = CapyApplication.class.getResource("level-view.fxml");
        FXMLLoader loader = new FXMLLoader(this.fxmlFile);
        try {
            this.scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Scene getScene() {
        return this.scene;
    }

    private ArrayList<Enemy> generateEnemies(final int amount) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int count = 0; count < amount; count++) {
            enemies.add(new Enemy(1));
        }
        return enemies;
    }
}
