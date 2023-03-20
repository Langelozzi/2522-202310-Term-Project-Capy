package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.models.Level;
import ca.bcit.comp2522.termproject.capy.models.Player;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Application class. Sets the stage and starts the application.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class CapyApplication extends Application {

    /**
     * Start the application with the provided stage, effectively starting the game with default settings.
     * @param stage the Stage that will be launched in the application window
     */
    @Override
    public void start(final Stage stage) {
        Player player = new Player(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/test_player.png")
        );
        final int numberOfEnemies = 3;
        Level levelOne = new Level(player, numberOfEnemies);

        stage.setTitle("Capy Pulls Up!");
        stage.getIcons().add(new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/imgs/icon"));

        stage.setScene(levelOne.getScene());
        stage.show();
    }

    /**
     * Main method. Launch the application.
     * @param args array of arguments to pass to the main method
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
