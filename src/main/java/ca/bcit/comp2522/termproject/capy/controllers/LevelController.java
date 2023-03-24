package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.Game;
import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LevelController implements Initializable, SceneController {
    @FXML
    private Pane pane;

    // initialize method gives access to FXML elements whereas constructor doesn't
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/imgs/swamp_level.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(pane.getWidth(), pane.getHeight(), true, true, true, true)
        );
        Background background = new Background(backgroundImage);

        this.pane.setMinWidth(Game.BACKGROUND_WIDTH);
        this.pane.setMinHeight(Game.BACKGROUND_HEIGHT);
        this.pane.setBackground(background);
    }

    public void renderSprite(final ImageView sprite, final int initialXPosition, final int initialYPosition) {
        this.pane.getChildren().add(sprite);

        sprite.setLayoutX(initialXPosition);
        sprite.setLayoutY(initialYPosition);
    }

    public Scene getScene() {
        return new Scene(pane);
    }


}