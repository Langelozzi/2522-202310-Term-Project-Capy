package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;

/**
 * Player class represents a user's player character in the game. Subclass of Character.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Player extends Character {
    /**
     * The default movement speed of the player.
     */
    public static final int DEFAULT_MOVEMENT_SPEED = 2;

    private int points;

    private Weapon weapon;
    private Armour armour;

    private int movementSpeed;

    /**
     * Instantiate a Player object.
     * @param sprite the Image to use as the player's ImageView sprite
     */
    public Player(final Image sprite) {
        super(sprite);

        this.points = 0;
        this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
    }

    /**
     * Instantiate a player object with a custom player speed.
     * @param sprite the Image to use as the player's ImageView sprite
     * @param playerSpeed the custom speed of the player
     * @throws IllegalArgumentException if the custom player speed is less than 0
     */
    public Player(final Image sprite, final int playerSpeed) {
        super(sprite);

        if (playerSpeed < 0) {
            throw new IllegalArgumentException("Speed must be a positive integer");
        }

        this.points = 0;
        this.movementSpeed = playerSpeed;
    }

    /**
     * Get the current amount of points that the player has.
     * @return the current amount of points that the player has
     */
    public int getPoints() {
        return points;
    }

    /**
     * Update the amount of points the player has.
     * @param newPoints the updated amount of points
     */
    public void setPoints(final int newPoints) {
        this.points = Math.max(newPoints, 0);
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
     * Shoot the weapon that the player is holding.
     */
    public void shoot() {

    };

    /**
     * Move the character in any of the 4 directions.
     * @param direction the direction to move the character
     */
    @Override
    public void move(final Direction direction) {
        switch (direction) {
            case UP -> this.getSprite().setLayoutY(this.getSprite().getLayoutY() - this.movementSpeed);
            case DOWN -> this.getSprite().setLayoutY(this.getSprite().getLayoutY() + this.movementSpeed);
            case LEFT -> this.getSprite().setLayoutX(this.getSprite().getLayoutX() - this.movementSpeed);
            case RIGHT -> this.getSprite().setLayoutX(this.getSprite().getLayoutX() + this.movementSpeed);
            default -> {
                return;
            }
        }
    }
}
