package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.Game;
import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StartViewController implements Initializable, SceneController {

    private Stage stage;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button continueBtn;
    @FXML
    private Button newGameBtn;
    @FXML
    private Button upgradesBtn;
    @FXML
    private Button settingsBtn;

    private final String btnBackgroundColor = "rgba(234, 249, 235, 0.6)";
    private final String btnBackgroundColorHover = " rgba(234, 249, 235, 0.85)";

    private final Font btnFont = new Font("Luminari", 26);
    private final Font btnFontHover = new Font("Luminari", 32);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.setMinHeight(Game.BACKGROUND_HEIGHT);
        anchorPane.setMinWidth(Game.BACKGROUND_WIDTH);

        if (!Game.hasSavedGame()) {
            continueBtn.setOpacity(0);
        }
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
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
        Game game = new Game(this.stage);
        game.start();
    }

    @Override
    public Scene getScene() {
        return new Scene(anchorPane);
    }

}
