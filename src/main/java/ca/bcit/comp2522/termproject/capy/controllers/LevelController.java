package ca.bcit.comp2522.termproject.capy.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LevelController implements Initializable {
    @FXML
    private Pane pane;

    private final int BACKGROUND_WIDTH = 865;
    private final int BACKGROUND_HEIGHT = 645;

    // initialize method gives access to FXML elements whereas constructor doesn't
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:src/swamp_level.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(pane.getWidth(), pane.getHeight(), true, true, true, true)
        );
        Background background = new Background(backgroundImage);

        this.pane.setMinWidth(BACKGROUND_WIDTH);
        this.pane.setMinHeight(BACKGROUND_HEIGHT);
        this.pane.setBackground(background);
    }
}