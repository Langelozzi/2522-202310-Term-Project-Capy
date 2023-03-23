package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.Game;
import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StartViewController implements Initializable, SceneController {

    private Stage stage;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button startBtn;
    private final String startBtnBackgroundColor = "#16F342FF";
    private final String startBtnBackgroundColorHover = "#ABFFB2FF";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.setMinHeight(Game.BACKGROUND_HEIGHT);
        anchorPane.setMinWidth(Game.BACKGROUND_WIDTH);

        startBtn.setStyle("-fx-background-color:" + this.startBtnBackgroundColor);
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    public void onMouseEntered() {
        startBtn.setStyle("-fx-background-color:" + this.startBtnBackgroundColorHover);
    }

    public void onMouseExited() {
        startBtn.setStyle("-fx-background-color:" + this.startBtnBackgroundColor);
    }

    public void onClick() {
        Game game = new Game(this.stage);
        game.start();
    }

    @Override
    public Scene getScene() {
        return new Scene(anchorPane);
    }

}
