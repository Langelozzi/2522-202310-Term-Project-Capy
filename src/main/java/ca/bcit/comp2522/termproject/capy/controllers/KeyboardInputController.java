package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.models.Character;
import ca.bcit.comp2522.termproject.capy.models.Direction;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * KeyboardInputController handles the keyboard input functionality.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class KeyboardInputController {

    private final BooleanProperty wPressed = new SimpleBooleanProperty();
    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty sPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();

    // using BooleanProperty allows us to use BooleanBinding which can have a listener attached to it
    private final BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    @FXML
    private Character character;

    @FXML
    private Scene scene;

    /**
     * Move the character when specific key is pressed.
     */
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(final long l) {
            if (wPressed.get()) {
                character.move(Direction.UP);
            }
            if (sPressed.get()) {
                character.move(Direction.DOWN);
            }
            if (aPressed.get()) {
                character.move(Direction.LEFT);
            }
            if (dPressed.get()) {
                character.move(Direction.RIGHT);
            }
        }
    };

    /**
     * Set all the keyboard input listeners. Set the movement controls for the Player and Game Menu button.
     *
     * @param player the character to set the movement on
     * @param newScene the scene to which the listeners are attached
     */
    public void assignKeyboardInput(final Character player, final Scene newScene) {
        this.character = player;
        this.scene = newScene;

        setListeners();

        keyPressed.addListener(((observableValue, bool, t1) -> {
            if (!bool) {
                timer.start();
            } else {
                timer.stop();
            }
        }));
    }

    /*
    Attach the key event listeners to the scene.
     */
    private void setListeners() {
        this.scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Helpers.openGameMenu();
            }
            if (e.getCode() == KeyCode.W) {
                wPressed.set(true);
            }
            if (e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }
            if (e.getCode() == KeyCode.S) {
                sPressed.set(true);
            }
            if (e.getCode() == KeyCode.D) {
                dPressed.set(true);
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W) {
                wPressed.set(false);
            }
            if (e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }
            if (e.getCode() == KeyCode.S) {
                sPressed.set(false);
            }
            if (e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }
        });
    }
}
