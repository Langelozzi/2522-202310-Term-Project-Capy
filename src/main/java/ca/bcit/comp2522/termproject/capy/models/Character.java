package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract Character class that represents the idea of a Character in the game.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public abstract class Character {
    /**
     * The default movement speed of a character.
     */
    public static final int DEFAULT_MOVEMENT_SPEED = 2;
    /**
     * The default amount of hit points the character starts with
     */
    public static final int DEFAULT_HIT_POINTS = 100;

    private final ImageView sprite;
    private int hitPoints;
    private int movementSpeed;
    private double previousXCoordinate;
    private double previousYCoordinate;

    Character(final Image spriteImage) {
        this.hitPoints = DEFAULT_HIT_POINTS;
        this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
        this.sprite = new ImageView(spriteImage);
    }

    Character(final Image spriteImage, final int movementSpeed) {
        if (movementSpeed < 0) {
            throw new IllegalArgumentException("Speed must be a positive integer");
        }

        this.hitPoints = DEFAULT_HIT_POINTS;
        this.movementSpeed = movementSpeed;
        this.sprite = new ImageView(spriteImage);
    }

    Character(final Image spriteImage, final int movementSpeed, final int hitPoints) {
        if (movementSpeed < 0) {
            throw new IllegalArgumentException("Speed must be a positive integer");
        }

        this.hitPoints = hitPoints;
        this.movementSpeed = movementSpeed;
        this.sprite = new ImageView(spriteImage);
    }

    /**
     * Return the ImageView sprite of the Character.
     * @return the ImageView sprite of the Character
     */
    public ImageView getSprite() {
        return sprite;
    }

    /**
     * Return the number of hitPoints the character currently has.
     * @return the number of hitPoints the character currently has
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Set the new hitPoint value of the Character.
     * @param newHitPoints the new hitPoint value
     */
    public void setHitPoints(final int newHitPoints) {
        this.hitPoints = newHitPoints;
    }

    /**
     * Get the current movement speed of the character.
     * @return the current movement speed of the character
     */
    public int getMovementSpeed() {
        return movementSpeed;
    }

    /**
     * Change the movementSpeed of the character.
     * @param newSpeed the updated speed
     * @throws IllegalArgumentException if the speed is less than 0
     */
    public void setMovementSpeed(final int newSpeed) {
        if (newSpeed < 0) {
            throw new IllegalArgumentException("Speed must be a positive integer");
        }
        this.movementSpeed = newSpeed;
    }

    /**
     * Get the most previously saved x coordinate of the character.
     * @return the most previously saved x coordinate of the character
     */
    public double getPreviousXCoordinate() {
        return previousXCoordinate;
    }

    /**
     * Set the previous x coordinate of the character to the current x coordinate.
     */
    public void setPreviousXCoordinate() {
        this.previousXCoordinate = this.getSprite().getLayoutX();
    }

    /**
     * Get the most previously saved y coordinate of the character.
     * @return the most previously saved y coordinate of the character
     */
    public double getPreviousYCoordinate() {
        return previousYCoordinate;
    }

    /**
     * Set the previous y coordinate to the current y coordinate.
     */
    public void setPreviousYCoordinate() {
        this.previousYCoordinate = this.getSprite().getLayoutY();
    }

    /**
     * Determine if the Character is still alive or not.
     * @return true if the characters hitPoints is more than 0, false otherwise
     */
    public boolean isAlive() {
        return hitPoints > 0;
    }

    /**
     * Move the ImageView sprite on the scene in the indicated direction. If the Character can move in more than one
     * direction, then this method should handle all the possible directions.
     * @param direction the direction to move the character
     */
    public abstract void move(Direction direction);
}
