package ca.bcit.comp2522.termproject.capy.models;

import ca.bcit.comp2522.termproject.capy.Game;
import javafx.scene.image.Image;

/**
 * Player class represents a user's player character in the game. Subclass of Character.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Player extends Character {

    /**
     * The default amount of sugar cane points a player starts with.
     */
    public static final int DEFAULT_SUGAR_CANE_POINTS = 1000;

    private int points;

    private Weapon weapon;
    private Armour armour;

    /**
     * Instantiate a Player object.
     * @param sprite the Image to use as the player's ImageView sprite
     */
    public Player(final Image sprite) {
        super(sprite);

        this.points = DEFAULT_SUGAR_CANE_POINTS;
    }

    /**
     * Instantiate a player object with a custom player speed.
     * @param sprite the Image to use as the player's ImageView sprite
     * @param playerSpeed the custom speed of the player
     */
    public Player(final Image sprite, final int playerSpeed) {
        super(sprite, playerSpeed);

        this.points = DEFAULT_SUGAR_CANE_POINTS;
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
            case UP -> {
                this.getSprite().setLayoutY(this.getSprite().getLayoutY() - this.getMovementSpeed());
                if (this.getSprite().getLayoutY() < 0) {
                    this.getSprite().setLayoutY(0);
                }
            }
            case DOWN -> {
                this.getSprite().setLayoutY(this.getSprite().getLayoutY() + this.getMovementSpeed());

                final double calculatedDownLimit = Game.BACKGROUND_HEIGHT - this.getSprite().getImage().getHeight();
                if (this.getSprite().getLayoutY() > calculatedDownLimit) {
                    this.getSprite().setLayoutY(calculatedDownLimit);
                }
            }
            case LEFT -> {
                this.getSprite().setLayoutX(this.getSprite().getLayoutX() - this.getMovementSpeed());
                if (this.getSprite().getLayoutX() < 0) {
                    this.getSprite().setLayoutX(0);
                }
            }
            case RIGHT -> {
                this.getSprite().setLayoutX(this.getSprite().getLayoutX() + this.getMovementSpeed());

                final double calculatedRightLimit = Game.BACKGROUND_WIDTH - this.getSprite().getImage().getWidth();
                if (this.getSprite().getLayoutX() > calculatedRightLimit) {
                    this.getSprite().setLayoutX(calculatedRightLimit);
                }
            }
            default -> {
                return;
            }
        }
    }
}
