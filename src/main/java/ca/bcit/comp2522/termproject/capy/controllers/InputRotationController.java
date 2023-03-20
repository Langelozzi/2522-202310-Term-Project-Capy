package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.Character;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class InputRotationController {

    @FXML
    private Character character;

    @FXML
    private Scene scene;

    public void makeRotatable(final Character character, final Scene scene) {
        this.character = character;
        this.scene = scene;

        rotationSetup();
    }

    private void rotationSetup() {
        this.scene.setOnMouseMoved(this::handleMouseRotation);
        // need the drag event too so that the rotation occurs even when clicking mouse button (for shooting)
        this.scene.setOnMouseDragged(this::handleMouseRotation);
    }

    private void handleMouseRotation(final MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getSceneX();
        double mouseY = mouseEvent.getSceneY();

        double playerX = this.character.getSprite().getLayoutX();
        double playerY = this.character.getSprite().getLayoutY();

        // subtract or add value to the atan2 return value to change which point of player follows cursor
        double angle = Math.toDegrees(Math.atan2(mouseY - playerY, mouseX - playerX)); // - Math.PI / 2);

        this.character.getSprite().setRotate(angle);
    }
}
