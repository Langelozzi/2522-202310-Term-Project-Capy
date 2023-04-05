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

/**
 * LeaderboardDialogController handles the functions of the new high score dialog for the leaderboard.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class LeaderboardDialogController implements SceneController, Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField nicknameInput;
    @FXML
    private Button submitBtn;

    private String nickname;

    /**
     * Initialize the limits on the nickname text field.
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNicknameLimits();
    }

    /**
     * Return the nickname entered by the user.
     * @return the nickname entered by the user
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Set the nickname.
     * @param newNickname the new nickname
     */
    public void setNickname(final String newNickname) {
        this.nickname = newNickname;
    }

    /**
     * Get the scene of the fxml file attached to this controller.
     * @return the scene of this fxml file
     */
    @Override
    public Scene getScene() {
        return new Scene(this.anchorPane);
    }

    /*
    Set limits on the nickname text field.
     */
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

    /*
    Handle the submit button click event. Retrieve the data in the text field and set it as the nickname, then close the
    dialog.
     */
    @FXML
    private void onSubmitClick() {
        this.nickname = this.nicknameInput.getText();
        Stage dialog = (Stage) this.anchorPane.getScene().getWindow();
        dialog.getOnCloseRequest().handle(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSE_REQUEST));
        dialog.close();
    }
}
