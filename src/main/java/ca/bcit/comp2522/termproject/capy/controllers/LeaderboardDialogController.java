package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class LeaderboardDialogController implements SceneController, Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField nicknameInput;
    @FXML
    private Button submitBtn;

    private String nickname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNicknameLimits();
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(final String newNickname) {
        this.nickname = newNickname;
    }

    @Override
    public Scene getScene() {
        return new Scene(this.anchorPane);
    }

    private void setNicknameLimits() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.getControlNewText().length() <= 10) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        nicknameInput.setTextFormatter(textFormatter);
    }

    @FXML
    private void onSubmitClick() {
        this.nickname = this.nicknameInput.getText();
        Stage dialog = (Stage) this.anchorPane.getScene().getWindow();
        dialog.getOnCloseRequest().handle(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSE_REQUEST));
        dialog.close();
    }
}
