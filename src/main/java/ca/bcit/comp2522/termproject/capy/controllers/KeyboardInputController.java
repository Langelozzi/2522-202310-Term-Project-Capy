package ca.bcit.comp2522.termproject.capy.controllers;


import ca.bcit.comp2522.termproject.capy.Helpers;
import ca.bcit.comp2522.termproject.capy.models.Character;
import ca.bcit.comp2522.termproject.capy.models.Direction;

import javafx.animation.AnimationTimer;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Ellipse;

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

    @FXML
    private Ellipse border;

    /**
     * Move the character when specific key is pressed.
     */
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(final long l) {
            if (wPressed.get()) {
                moveCharacter(character, Direction.UP, border);
            }
            if (sPressed.get()) {
                moveCharacter(character, Direction.DOWN, border);
            }
            if (aPressed.get()) {
                moveCharacter(character, Direction.LEFT, border);
            }
            if (dPressed.get()) {
                moveCharacter(character, Direction.RIGHT, border);
            }
        }
    };

    private ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bool, Boolean t1) {
                if (!bool) {
                    timer.start();
                } else {
                    timer.stop();
                }
            }
        };

    private final EventHandler<KeyEvent> keyPressEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            if (e.getCode() == KeyCode.ESCAPE) {
                character.setPreviousXCoordinate();
                character.setPreviousYCoordinate();
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
            e.consume();
        }
    };

    private final EventHandler<KeyEvent> keyReleaseEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
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
            e.consume();
        }
    };

    /**
     * Set all the keyboard input listeners. Set the movement controls for the Player and Game Menu button.
     *
     * @param player the character to set the movement on
     * @param newScene the scene to which the listeners are attached
     * @param border the border of the game map
     */
    public void assignKeyboardInput(final Character player, final Scene newScene, final Ellipse border) {
        resetControls();

        this.character = player;
        this.scene = newScene;
        this.border = border;

        setListeners();
        keyPressed.addListener(changeListener);
    }

    public void removeKeyboardInput() {
        resetControls();
        this.timer.stop();
        this.removeListeners();
    }

    /*
    Attach the key event listeners to the scene.
     */
    private void setListeners() {
        scene.setOnKeyPressed(keyPressEvent);
        scene.setOnKeyReleased(keyReleaseEvent);
    }

    private void removeListeners() {
        keyPressed.removeListener(changeListener);
        scene.setOnKeyPressed(null);
        scene.setOnKeyReleased(null);
    }

    private void moveCharacter(final Character character, final Direction direction, final Ellipse border) {
        double currentX = character.getSprite().getLayoutX();
        double currentY = character.getSprite().getLayoutY();

        double newX = currentX;
        double newY = currentY;

        switch (direction) {
            case UP -> newY = currentY - character.getMovementSpeed();
            case DOWN -> newY = currentY + character.getMovementSpeed();
            case LEFT -> newX = currentX - character.getMovementSpeed();
            case RIGHT -> newX = currentX + character.getMovementSpeed();
        }

        // must subtract the border position, as the contains() method compares to the "local coordinate space",
        // where its layoutX and layoutY are considered 0
        if (border.contains(newX - border.getLayoutX(), newY - border.getLayoutY())) {
            character.getSprite().setLayoutX(newX);
            character.getSprite().setLayoutY(newY);
        }
    }

    private void resetControls() {
        this.wPressed.set(false);
        this.aPressed.set(false);
        this.sPressed.set(false);
        this.dPressed.set(false);
    }
}
