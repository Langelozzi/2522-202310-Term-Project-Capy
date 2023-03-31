package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class WaveMessageController implements SceneController {

    @FXML
    private Pane mainPane;
    @FXML
    private Text waveText;

    public void setWaveNumber(final int waveNumber) {
        this.waveText.setText("Wave " + waveNumber);
    }

    @Override
    public Scene getScene() {
        return new Scene(mainPane);
    }
}
