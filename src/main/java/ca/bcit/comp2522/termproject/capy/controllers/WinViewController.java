package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WinViewController implements SceneController, Initializable {

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setListeners(final Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                Helpers.openGameMenu();
            }
        });
    }

    @Override
    public Scene getScene() {
        return new Scene(this.anchorPane);
    }


}
