package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.controllers.GameMenuController;
import ca.bcit.comp2522.termproject.capy.models.Game;
import ca.bcit.comp2522.termproject.capy.utils.Helpers;
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

    // INITIALIZATION  =================================================================================================

    private static Stage stage;
    private static Game game;

    // GETTERS AND SETTERS =============================================================================================

    /**
     * Get the stage/window of the application.
     * @return the stage/window of the application
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Return the Game object that runs the game.
     * @return the Game object
     */
    public static Game getGame() {
        return game;
    }

    /**
     * Set the Game object to be played.
     * @param newGame the new Game object to be played
     */
    public static void setGame(final Game newGame) {
        game = newGame;
    }

    // GAME START ======================================================================================================

    /**
     * Start the application with the provided stage, effectively starting the game with default settings.
     * @param mainStage the Stage that will be launched in the application window
     */
    @Override
    public void start(final Stage mainStage) {
        CapyApplication.stage = mainStage;
        CapyApplication.game = new Game();

        GameMenuController startViewController = (GameMenuController) Helpers.getFxmlController("game-menu-view.fxml");

        mainStage.setTitle("Capy Pulls Up!");
        mainStage.getIcons().add(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/imgs/medium_pixel_capy.png")
        );
        mainStage.setMinHeight(Game.BACKGROUND_HEIGHT);
        mainStage.setMinWidth(Game.BACKGROUND_WIDTH);

        mainStage.setScene(startViewController.getScene());
        mainStage.show();
    }

    /**
     * Main method. Launch the application.
     * @param args array of arguments to pass to the main method
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
