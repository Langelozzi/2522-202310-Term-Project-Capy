package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.Character;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

/**
 * MouseInputController handles the logic for the mouse movement of the Player.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class MouseInputController {

    @FXML
    private Character character;

    @FXML
    private Scene scene;

    /**
     * Enable cursor rotation functionality on the character for the scene.
     * @param player the player that will get the rotation functionality
     * @param newScene the scene that the rotation applies to
     */
    public void makeCursorRotatable(final Character player, final Scene newScene) {
        this.character = player;
        this.scene = newScene;

        rotationSetup();
    }

    /*
    Set the rotation for both mouse move event and mouse drag event so that user can click and rotate at the same time.
     */
    private void rotationSetup() {
        this.scene.setOnMouseMoved(this::handleMouseRotation);
        // need the drag event too so that the rotation occurs even when clicking mouse button (for shooting)
        this.scene.setOnMouseDragged(this::handleMouseRotation);
    }

    /*
    Logic for rotating character toward the mouse cursor.
     */
    private void handleMouseRotation(final MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getSceneX();
        double mouseY = mouseEvent.getSceneY();

        double playerX = this.character.getSprite().getLayoutX();
        double playerY = this.character.getSprite().getLayoutY();

        // subtract or add value to the atan2 return value to change which point of player follows cursor
        // atan2 returns theta based on rectangular coordinates, aka the circular angle between two rectangular coords
        double angle = Math.toDegrees(Math.atan2(mouseY - playerY, mouseX - playerX)); // - Math.PI / 2);

        this.character.getSprite().setRotate(angle);
    }
}
