package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;

/**
 * Player class represents a user's player character in the game. Subclass of Character.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Player extends Character {

    private int points;

    private Weapon weapon;
    private Armour armour;

    /**
     * Instantiate a Player object.
     * @param sprite the Image to use as the player's ImageView sprite
     */
    public Player(final Image sprite) {
        super(sprite);

        this.points = 0;
    }

    /**
     * Instantiate a player object with a custom player speed.
     * @param sprite the Image to use as the player's ImageView sprite
     * @param playerSpeed the custom speed of the player
     */
    public Player(final Image sprite, final int playerSpeed) {
        super(sprite, playerSpeed);

        this.points = 0;
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
            case UP -> this.getSprite().setLayoutY(this.getSprite().getLayoutY() - this.getMovementSpeed());
            case DOWN -> this.getSprite().setLayoutY(this.getSprite().getLayoutY() + this.getMovementSpeed());
            case LEFT -> this.getSprite().setLayoutX(this.getSprite().getLayoutX() - this.getMovementSpeed());
            case RIGHT -> this.getSprite().setLayoutX(this.getSprite().getLayoutX() + this.getMovementSpeed());
            default -> {
                return;
            }
        }
    }
}
