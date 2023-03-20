package ca.bcit.comp2522.termproject.capy.controllers;

import ca.bcit.comp2522.termproject.capy.models.Character;
import ca.bcit.comp2522.termproject.capy.models.Direction;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class KeyboardInputController {

    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    // using BooleanProperty allows us to use BooleanBinding which can have a listener attached to it
    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    @FXML
    private Character character;

    @FXML
    private Scene scene;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
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

    public void makeMovable(Character character, Scene scene) {
        this.character = character;
        this.scene = scene;

        movementSetup();

        keyPressed.addListener(((observableValue, bool, t1) -> {
            if (!bool) {
                timer.start();
            } else {
                timer.stop();
            }
        }));
    }

    private void movementSetup() {
        scene.setOnKeyPressed(e -> {
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
