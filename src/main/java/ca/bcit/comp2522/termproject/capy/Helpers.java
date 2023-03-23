package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXMLLoader;

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
}
