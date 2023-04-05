package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.Player;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import ca.bcit.comp2522.termproject.capy.utils.Helpers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * GameOverController handles the functions of the Game Over scene.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class GameOverController implements SceneController {
    @FXML
    private AnchorPane anchorPane;

    /**
     * Get the scene of the GameOverController.
     * @return the scene of the controller
     */
    @Override
    public Scene getScene() {
        return new Scene(anchorPane);
    }

    /**
     * Set the keyboard event listeners on the game over scene.
     * @param scene the scene to set the listeners on
     * @param player the player that will be checked for the leaderboard
     */
    public void setListeners(final Scene scene, final Player player) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                Helpers.showLeaderboard(false, player);
            }
        });
    }
}
