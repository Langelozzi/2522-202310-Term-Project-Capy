package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.controllers.GameMenuController;
import ca.bcit.comp2522.termproject.capy.controllers.UpgradesController;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

/**
 * Helpers class contains some generic helper methods for the game.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public final class Helpers {

    private Helpers() {

    }

    /**
     * Return the controller class for a specific fxml file.
     * @param fxmlFileName the name of the fxml file
     * @return the controller object of fxml file
     * @throws RuntimeException if the fxml file fails to load
     */
    public static SceneController getFxmlController(final String fxmlFileName) {
        URL fxmlFile = CapyApplication.class.getResource(fxmlFileName);
        FXMLLoader loader = new FXMLLoader(fxmlFile);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return loader.getController();
    }

    /**
     * Change the scene on the stage.
     * @param scene the scene to change to
     */
    public static void changeScene(final Scene scene) {
        CapyApplication.getStage().setScene(scene);
        CapyApplication.getStage().show();
    }

    /**
     * Open the GameMenu scene on the stage.
     */
    public static void openGameMenu() {
        // Game.setHasSavedGame(true);
        Game.setPaused(true);
        GameMenuController menuController = (GameMenuController) Helpers.getFxmlController(
            "game-menu-view.fxml"
        );
        Helpers.changeScene(menuController.getScene());
    }

    public static void openUpgradesMenu() {
        Game.setPaused(true);
        UpgradesController upgradesController = (UpgradesController) Helpers.getFxmlController(
                "upgrade-menu-view.fxml"
        );
        Helpers.changeScene(upgradesController.getScene());
    }
}
