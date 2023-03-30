package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * SugarCane class represents a sugar cane object in the game.
 * It contains information about the sugar cane's value, if it has been dropped and if it has been collected.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class SugarCane {

    // INITIALIZATION  =================================================================================================

    private final int value;
    private boolean dropped;
    private boolean collected;
    private final ImageView sprite;

    /**
     * Instantiates a new SugarCane with the specified value.
     *
     * @param value The value of the sugar cane.
     */
    SugarCane(final int value) {
        this.value = value;
        this.dropped = false;
        this.collected = false;

        this.sprite = new ImageView(
                new Image(
                        "file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/sugar_cane.png",
                        60.0,
                        60.0,
                        true,
                        false
                )
        );
    }

    // GETTERS AND SETTERS =============================================================================================

    /**
     * Get the value of the sugar cane.
     *
     * @return The value of the sugar cane.
     */
    public int getValue() {
        return value;
    }

    /**
     * Check if the sugar cane has been dropped.
     *
     * @return true if the sugar cane has been dropped, false otherwise.
     */
    public boolean isDropped() {
        return dropped;
    }

    /**
     * Set whether the sugar cane has been dropped.
     *
     * @param dropped True if the sugar cane has been dropped, false otherwise.
     */
    public void setDropped(final boolean dropped) {
        this.dropped = dropped;
    }

    /**
     * Check if the sugar cane has been collected.
     *
     * @return true if the sugar cane has been collected, false otherwise.
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * Set whether the sugar cane has been collected.
     *
     * @param collected True if the sugar cane has been collected, false otherwise.
     */
    public void setCollected(final boolean collected) {
        this.collected = collected;
    }

    /**
     * Get the sprite for rendering the sugar cane.
     *
     * @return The sprite for rendering the sugar cane.
     */
    public ImageView getSprite() {
        return sprite;
    }

    // SUGARCANE LOGIC =================================================================================================

    /**
     * Check if the sugar cane is colliding with the player.
     *
     * @param player The player to check collision with.
     * @return true if the sugar cane is colliding with the player, false otherwise.
     */
    public boolean checkCollision(final Player player) {
        return player.getSprite().getBoundsInParent().intersects(this.getSprite().getBoundsInParent());
    }
}
