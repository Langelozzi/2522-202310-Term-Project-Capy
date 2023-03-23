package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.models.Level;
import ca.bcit.comp2522.termproject.capy.models.Player;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Game {

    private Scene currentScene;
    private final Stage stage;

    public static final int BACKGROUND_WIDTH = 865;
    public static final int BACKGROUND_HEIGHT = 645;

    public Game(final Stage stage) {
        Player player = new Player(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/test_player.png")
        );
        final int numberOfEnemies = 3;
        Level levelOne = new Level(player, numberOfEnemies);
        this.currentScene = levelOne.getScene();
        this.stage = stage;
    }

    public void start() {
        this.stage.setScene(this.currentScene);
        this.stage.show();
    }
}
