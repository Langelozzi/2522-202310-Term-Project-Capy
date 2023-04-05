package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.CapyApplication;
import ca.bcit.comp2522.termproject.capy.models.Game;
import ca.bcit.comp2522.termproject.capy.utils.Helpers;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    private Button leaderboardBtn;
    @FXML
    private Button upgradeWeaponBtn;
    @FXML
    private Button upgradeArmourBtn;
    @FXML
    private Button quitBtn;
    @FXML
    private Text leaderboardText;


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

        if (continueBtn != null) {
            continueBtn.setVisible(Game.hasSavedGame());
        }
        if (upgradeWeaponBtn != null) {
            upgradeWeaponBtn.setDisable(!Game.hasSavedGame());
        }
        if (upgradeArmourBtn != null) {
            upgradeArmourBtn.setDisable(!Game.hasSavedGame());
        }

        // Add this line to request focus for the AnchorPane
        Platform.runLater(() -> anchorPane.requestFocus());

        // Change this line to add an event filter for key presses on the stage
        CapyApplication.getStage().addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);

        Helpers.playBackgroundMusic();

    }


    /**
     * Change the size, color and font of the button when mousing over for visual cue.
     *
     * @param event the mouse event triggered when hovering over the button
     */
    public void onMouseEntered(final MouseEvent event) {
        Button button = (Button) event.getTarget();
        
        if(button.isDisabled())
            return;

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
        
        if(button.isDisabled())
            return;

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
    public void onUpgradeWeaponClick() {
        Helpers.openUpgradesMenu("Weapon");
    }

    public void onUpgradeArmourClick() {
        Helpers.openUpgradesMenu("Armour");
    }

    /**
     * Return back to the current level, and it's state when "Continue" button is clicked.
     */
    public void onContinueClick() {
        Helpers.changeScene(CapyApplication.getGame().getCurrentLevel().getScene());
        Game.setPaused(false);
    }

    public void onLeaderboardClick() {
        Helpers.showLeaderboard(true, null);
    }

    /**
     * Exit the game when the "Quit" button is clicked.
     */
    public void onQuitClick() {
        Platform.exit();
    }

    public void onKeyPressed(KeyEvent event) {
        System.out.println("Key pressed: " + event.getCode()); // Add this line for debugging
        if (event.getCode() == KeyCode.SPACE) {
            Helpers.showLeaderboard(true, null);
        }
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
