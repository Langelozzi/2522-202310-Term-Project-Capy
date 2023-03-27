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
                // character.move(Direction.UP);
                moveCharacter(character, Direction.UP, border);
            }
            if (sPressed.get()) {
                // character.move(Direction.DOWN);
                moveCharacter(character, Direction.DOWN, border);
            }
            if (aPressed.get()) {
                // character.move(Direction.LEFT);
                moveCharacter(character, Direction.LEFT, border);
            }
            if (dPressed.get()) {
                // character.move(Direction.RIGHT);
                moveCharacter(character, Direction.RIGHT, border);
            }
        }
    };

    /**
     * Set all the keyboard input listeners. Set the movement controls for the Player and Game Menu button.
     *
     * @param player the character to set the movement on
     * @param newScene the scene to which the listeners are attached
     */
    public void assignKeyboardInput(final Character player, final Scene newScene, final Ellipse border) {
        this.character = player;
        this.scene = newScene;
        this.border = border;

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
                this.character.setPreviousXCoordinate();
                this.character.setPreviousYCoordinate();
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
}
