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
    private Pane gameLayer;
    @FXML
    private ProgressBar healthBar;
    @FXML
    private Text sugarCanePoints;
    @FXML
    private Ellipse swampBorder;

    /**
     * Initialize the Level scene with its initial properties.
     *
     * @param url            url of the fxml, passed automatically by javafx
     * @param resourceBundle resources for the fxml, passed automatically by javafx
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        // Initialize the gameLayer
        gameLayer = new Pane();
        pane.getChildren().add(gameLayer);
    }

    /**
     * Get the health bar ProgressBar object of the level.
     *
     * @return the health bar ProgressBar
     */
    public ProgressBar getHealthBar() {
        return this.healthBar;
    }

    /**
     * Get the Text object that shows the players sugar cane points.
     *
     * @return the Text object that shows the players sugar cane points
     */
    public Text getSugarCanePoints() {
        return this.sugarCanePoints;
    }

    /**
     * Get the Ellipse object that represents the border of the swamp in the Level scene.
     *
     * @return the Ellipse object that represents the border of the swamp in the Level scene
     */
    public Ellipse getSwampBorder() {
        return this.swampBorder;
    }

    /**
     * Render an ImageView sprite on the level scene.
     *
     * @param sprite           the ImageView to render
     * @param initialXPosition the initial x coordinate of the sprite on the scene
     * @param initialYPosition the initial y coordinate of the sprite on the scene
     */
    public void renderSprite(final ImageView sprite, final double initialXPosition, final double initialYPosition) {
        if (!this.gameLayer.getChildren().contains(sprite)) {
            this.gameLayer.getChildren().add(sprite);
        }

        sprite.setLayoutX(initialXPosition);
        sprite.setLayoutY(initialYPosition);
    }

    /**
     * Return the Scene object of this level.
     *
     * @return the Scene object of this level
     */
    public Scene getScene() {
        return new Scene(pane);
    }

    /**
     * Returns the game layer.
     *
     * @return the game layer
     */
    public Pane getGameLayer() {
        return this.gameLayer;
    }
}
