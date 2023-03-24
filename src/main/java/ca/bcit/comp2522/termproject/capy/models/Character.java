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
    private final ImageView sprite;
    private int hitPoints;

    Character(final Image spriteImage) {
        this.hitPoints = 0;

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
