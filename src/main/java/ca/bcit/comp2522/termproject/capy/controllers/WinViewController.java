package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.utils.Helpers;
import ca.bcit.comp2522.termproject.capy.models.Player;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * WinViewController handles the functions related to the win scene.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class WinViewController implements SceneController {

    @FXML
    private AnchorPane anchorPane;

    /**
     * Set the event listeners for this screen.
     * 
     * @param scene  the scene to set the listeners on
     * @param player the player to check for new high score on
     */
    public void setListeners(final Scene scene, final Player player) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                Helpers.showLeaderboard(false, player);
            }
        });
    }

    /**
     * Return the scene of this controller.
     * 
     * @return the scene of this controller
     */
    @Override
    public Scene getScene() {
        return new Scene(this.anchorPane);
    }
}
