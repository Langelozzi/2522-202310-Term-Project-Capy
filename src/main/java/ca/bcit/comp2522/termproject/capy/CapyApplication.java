package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.models.Level;
import ca.bcit.comp2522.termproject.capy.models.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class CapyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Player player = new Player(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/test_player.png")
        );
        Level levelOne = new Level(player, 3);

        stage.setTitle("Capy Pulls Up!");
        // stage.getIcons().add(capy gun image)

        stage.setScene(levelOne.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}