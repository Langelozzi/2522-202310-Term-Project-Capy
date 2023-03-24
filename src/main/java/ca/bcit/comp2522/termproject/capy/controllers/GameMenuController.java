package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.Game;
import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameMenuController implements Initializable, SceneController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button continueBtn;
    @FXML
    private Button newGameBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button upgradesBtn;
    @FXML
    private Button quitBtn;

    private Game game;

    private final String btnBackgroundColor = "rgba(234, 249, 235, 0.6)";
    private final String btnBackgroundColorHover = " rgba(234, 249, 235, 0.85)";

    private final Font btnFont = new Font("Luminari", 26);
    private final Font btnFontHover = new Font("Luminari", 32);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.setMinHeight(Game.BACKGROUND_HEIGHT);
        anchorPane.setMinWidth(Game.BACKGROUND_WIDTH);

        this.game = new Game();

        if (!Game.hasSavedGame()) {
            continueBtn.setVisible(false);
            saveBtn.setVisible(false);
        }
    }

    public void onMouseEntered(final MouseEvent event) {
        Button button = (Button) event.getTarget();
        button.setStyle("-fx-background-color:" + this.btnBackgroundColorHover);

        button.setMinWidth(270);
        button.setMinHeight(110);

        button.setFont(this.btnFontHover);
    }

    public void onMouseExited(final MouseEvent event) {
        Button button = (Button) event.getTarget();
        button.setStyle("-fx-background-color:" + this.btnBackgroundColor);

        button.setMinWidth(230);
        button.setMinHeight(80);

        Font font = new Font("Luminari", 26);
        button.setFont(this.btnFont);
    }

    public void onNewGameClick() {
        this.game.start();
    }

    public void onContinueClick() {
        Helpers.changeScene(this.game.getCurrentLevel().getScene());
    }

    public void onQuitClick() {
        Platform.exit();
    }

    @Override
    public Scene getScene() {
        return new Scene(anchorPane);
    }

}
