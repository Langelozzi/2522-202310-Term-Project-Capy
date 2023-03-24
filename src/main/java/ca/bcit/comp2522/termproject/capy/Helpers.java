package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.controllers.GameMenuController;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Helpers {
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

    public static void changeScene(final Scene scene) {
        CapyApplication.getStage().setScene(scene);
        CapyApplication.getStage().show();
    }

    public static void openGameMenu() {
        Game.setHasSavedGame(true);
        GameMenuController menuController = (GameMenuController) Helpers.getFxmlController(
            "game-menu-view.fxml"
        );
        Helpers.changeScene(menuController.getScene());
    }
}
