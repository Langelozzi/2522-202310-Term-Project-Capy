package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.controllers.GameMenuController;
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

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    /**
     * Start the application with the provided stage, effectively starting the game with default settings.
     * @param stage the Stage that will be launched in the application window
     */
    @Override
    public void start(final Stage stage) {
        CapyApplication.stage = stage;

        GameMenuController startViewController = (GameMenuController) Helpers.getFxmlController("game-menu-view.fxml");

        stage.setTitle("Capy Pulls Up!");
        stage.getIcons().add(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/imgs/medium_pixel_capy.png")
        );

        stage.setScene(startViewController.getScene());
        stage.setMinHeight(Game.BACKGROUND_HEIGHT);
        stage.setMinWidth(Game.BACKGROUND_WIDTH);
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
