package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.Bullet;
import ca.bcit.comp2522.termproject.capy.models.Character;
import ca.bcit.comp2522.termproject.capy.models.Player;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

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
    private Player player;
    @FXML
    private Scene scene;

    /**
     * Enable cursor rotation functionality on the character for the scene.
     *
     * @param character the player that will get the rotation functionality
     * @param player    the player that will get the shooting functionality
     * @param newScene  the scene that the rotation applies to
     */
    public void makeCursorRotatable(final Character character, final Player player, final Scene newScene) {
        this.character = character;
        this.player = player;
        this.scene = newScene;

        rotationSetup();
        this.scene.setOnMouseClicked(this::handleMouseClick);
    }

    /*
    Set the rotation for both mouse move event and mouse drag event
    so that user can click and rotate at the same time.
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
        // atan2 returns theta based on rectangular coordinates, aka the
        // circular angle between two rectangular coords
        double angle = Math.toDegrees(Math.atan2(mouseY - playerY, mouseX - playerX)); // - Math.PI / 2);

        this.character.getSprite().setRotate(angle);
    }

    private void handleMouseClick(final MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            player.shoot(event.getSceneX(), event.getSceneY());
        }
    }

    /**
     * Handles shooting of the player by creating a new Bullet and adding itto the list of player's
     * bullets on mouse click.
     *
     * @param player the player to handle shooting for
     * @param scene the Scene on which mouse click event occurred
     * @param bulletConsumer a Consumer of Bullet objects that will accept the new Bullet object created
     */
    public void handleShooting(final Player player, final Scene scene, final Consumer<Bullet> bulletConsumer) {
        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                ImageView sprite = player.getSprite();
                double centerX = sprite.getLayoutX() + sprite.getBoundsInLocal().getWidth() / 2;
                double centerY = sprite.getLayoutY() + sprite.getBoundsInLocal().getHeight() / 2;

                double deltaX = event.getX() - centerX;
                double deltaY = event.getY() - centerY;
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                double directionX = deltaX / distance;
                double directionY = deltaY / distance;

                Bullet bullet = new Bullet(centerX, centerY, directionX, directionY, 5);
                player.getBullets().add(bullet);
                bulletConsumer.accept(bullet);
            }
        });
    }
}

