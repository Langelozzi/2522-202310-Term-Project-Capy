package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * LevelController handles the functions of the Level scene.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class LevelController implements Initializable, SceneController {
    @FXML
    private Pane pane;
    @FXML
    private ProgressBar healthBar;
    @FXML
    private Text sugarCanePoints;
    @FXML
    private Ellipse swampBorder;

    /**
     * Initialize the Level scene with its initial properties.
     * @param url url of the fxml, passed automatically by javafx
     * @param resourceBundle resources for the fxml, passed automatically by javafx
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {

    }

    /**
     * Get the health bar ProgressBar object of the level.
     * @return the health bar ProgressBar
     */
    public ProgressBar getHealthBar() {
        return this.healthBar;
    }

    /**
     * Get the Text object that shows the players sugar cane points.
     * @return the Text object that shows the players sugar can points
     */
    public Text getSugarCanePoints() {
        return this.sugarCanePoints;
    }

    /**
     * Render an ImageView sprite on the level scene.
     * @param sprite the ImageView to render
     * @param initialXPosition the initial x coordinate of the sprite on the scene
     * @param initialYPosition the initial y coordinate of the sprite on the scene
     */
    public void renderSprite(final ImageView sprite, final double initialXPosition, final double initialYPosition) {
        // this.pane.getChildren().add(sprite);

        sprite.setLayoutX(initialXPosition);
        sprite.setLayoutY(initialYPosition);
    }

    /**
     * Return the Scene object of this level.
     * @return the Scene object of this level
     */
    public Scene getScene() {
        return new Scene(pane);
    }
}
