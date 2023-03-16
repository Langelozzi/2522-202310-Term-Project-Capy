package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.Character;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class InputRotationController {

    @FXML
    private Character character;

    @FXML
    private Scene scene;

    public void makeRotatable(Character character, Scene scene) {
        this.character = character;
        this.scene = scene;

        rotationSetup();
    }

    private void rotationSetup() {
        this.scene.setOnMouseMoved(mouseEvent -> {
            double mouseX = mouseEvent.getSceneX();
            double mouseY = mouseEvent.getSceneY();

            double playerX = this.character.getSprite().getX();
            double playerY = this.character.getSprite().getY();

            float angle = (float) Math.toDegrees(Math.atan2(mouseY - playerY, mouseX - playerX) - Math.PI / 2);
            // if (angle < 0) angle += 360;
            this.character.getSprite().setRotate(angle);
        });
    }
}
