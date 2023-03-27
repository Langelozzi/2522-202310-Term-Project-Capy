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
public class UpgradesController implements Initializable, SceneController {

    @FXML
    private AnchorPane upgradesAnchorPane;
    @FXML
    private Button stubBtn;


    /*
    Default font of buttons
     */
    private final Font btnFont = new Font("Luminari", 26);
    /*
    Font of buttons when being hovered over
     */
    private final Font btnFontHover = new Font("Luminari", 32);

    /**
     * Initialize the game menu with specific properties, which are different depending on if the user has a saved game.
     * @param url url of the fxml, passed automatically by javafx
     * @param resourceBundle resources for the fxml, passed automatically by javafx
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        upgradesAnchorPane.setMinHeight(Game.BACKGROUND_HEIGHT);
        upgradesAnchorPane.setMinWidth(Game.BACKGROUND_WIDTH);
    }

    /**
     * Change the size, color and font of the button when mousing over for visual cue.
     * @param event the mouse event triggered when hovering over the button
     */
    public void onMouseEntered(final MouseEvent event) {
        Button button = (Button) event.getTarget();

        final String btnBackgroundColorHover = "rgba(234, 249, 235, 0.85)";
        button.setStyle("-fx-background-color: " + btnBackgroundColorHover);

        final int hoverWidth = 270;
        final int hoverHeight = 110;
        button.setMinWidth(hoverWidth);
        button.setMinHeight(hoverHeight);

        button.setFont(this.btnFontHover);
    }

    /**
     * Change the button properties back to default when the mouse stops hovering over the button.
     * @param event the mouse event triggered when the mouse stops hovering
     */
    public void onMouseExited(final MouseEvent event) {
        Button button = (Button) event.getTarget();

        final String btnBackgroundColor = "rgba(234, 249, 235, 0.6)";
        button.setStyle("-fx-background-color: " + btnBackgroundColor);

        final int defaultWidth = 230;
        final int defaultHeight = 80;
        button.setMinWidth(defaultWidth);
        button.setMinHeight(defaultHeight);

        button.setFont(this.btnFont);
    }

    public void onBackClick() {
        Helpers.openGameMenu();
    }


//    public void onUpgradeClick() {
//        Helpers.openUpgradeMenu();
//    }


    /**
     * Return the parent AnchorPane as the scene for this page.
     * @return the parent AnchorPane as a Scene
     */
    @Override
    public Scene getScene() {
        return new Scene(upgradesAnchorPane);
    }

}
