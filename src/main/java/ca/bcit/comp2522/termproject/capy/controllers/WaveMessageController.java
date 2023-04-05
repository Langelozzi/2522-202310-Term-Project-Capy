package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * WaveMessageController handles functions related to the next wave screen.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class WaveMessageController implements SceneController {

    @FXML
    private Pane mainPane;
    @FXML
    private Text waveText;

    /**
     * Set the number representing which wave the user is on.
     * @param waveNumber the wave number
     */
    public void setWaveNumber(final int waveNumber) {
        this.waveText.setText("Wave " + waveNumber);
    }

    /**
     * Return the scene for this controller.
     * @return the scene for this controller.
     */
    @Override
    public Scene getScene() {
        return new Scene(mainPane);
    }
}
