package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.CapyApplication;
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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * GameMenuController handles the functions of the Game Menu scene.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
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

    /*
    Default font of buttons
     */
    private final Font btnFont = new Font("Trebuchet MS", 32);
    /*
    Font of buttons when being hovered over
     */
    private final Font btnFontHover = new Font("Trebuchet MS", 38);

    /**
     * Initialize the game menu with specific properties, which are different depending on if the user has a saved game.
     *
     * @param url            url of the fxml, passed automatically by javafx
     * @param resourceBundle resources for the fxml, passed automatically by javafx
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        anchorPane.setMinHeight(Game.BACKGROUND_HEIGHT);
        anchorPane.setMinWidth(Game.BACKGROUND_WIDTH);

        if (!Game.hasSavedGame()) {
            continueBtn.setVisible(false);
            saveBtn.setVisible(false);
        }
    }

    /**
     * Change the size, color and font of the button when mousing over for visual cue.
     *
     * @param event the mouse event triggered when hovering over the button
     */
    public void onMouseEntered(final MouseEvent event) {
        Button button = (Button) event.getTarget();

        final String btnBackgroundColorHover = "rgba(234, 249, 235, 0.85)";
        button.setStyle("-fx-background-color: " + btnBackgroundColorHover);

        final int hoverWidth = 315;
        final int hoverHeight = 135;
        button.setMinWidth(hoverWidth);
        button.setMinHeight(hoverHeight);

        button.setFont(this.btnFontHover);
    }

    /**
     * Change the button properties back to default when the mouse stops hovering over the button.
     *
     * @param event the mouse event triggered when the mouse stops hovering
     */
    public void onMouseExited(final MouseEvent event) {
        Button button = (Button) event.getTarget();

        final String btnBackgroundColor = "rgba(234, 249, 235, 0.6)";
        button.setStyle("-fx-background-color: " + btnBackgroundColor);

        final int defaultWidth = 280;
        final int defaultHeight = 100;
        button.setMinWidth(defaultWidth);
        button.setMinHeight(defaultHeight);

        button.setFont(this.btnFont);
    }

    /**
     * Start a new game when "New Game" button is clicked.
     * @throws Exception
     */
    public void onNewGameClick() throws Exception {
        CapyApplication.setGame(new Game());
        CapyApplication.getGame().start();
    }

    /**
     * Handles the action when the "Upgrades" button is clicked.
     */
    public void onUpgradeClick() {
        Helpers.openUpgradesMenu();
    }

    /**
     * Return back to the current level, and it's state when "Continue" button is clicked.
     */
    public void onContinueClick() {
        Helpers.changeScene(CapyApplication.getGame().getCurrentLevel().getScene());
        Game.setPaused(false);
    }

    /**
     * Exit the game when the "Quit" button is clicked.
     */
    public void onQuitClick() {
        Platform.exit();
    }

//    public void onUpgradesClick() {
//        Helpers.changeScene();
//    }

    /**
     * Return the parent AnchorPane as the scene for this page.
     *
     * @return the parent AnchorPane as a Scene
     */
    @Override
    public Scene getScene() {
        return new Scene(anchorPane);
    }

}
