package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.ImageView;

/**
 * Represents an Item in the game. An abstract class that should be extended
 * by all item types (i.e. weapons, armor, etc).
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public abstract class Item {
    private final ImageView sprite;
    private final int cost;
    private final int level;
    private final String name;

    /**
     * Constructs a new Item object with the specified sprite, cost, level, and name.
     *
     * @param sprite the sprite image for the item
     * @param cost   the cost of the item in the game
     * @param level  the level required to use the item
     * @param name   the name of the item
     */
    public Item(final ImageView sprite, final int cost, final int level, final String name) {
        this.sprite = sprite;
        this.cost = cost;
        this.level = level;
        this.name = name;
    }

    /**
     * Returns the cost of the item.
     *
     * @return the cost of the item
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Returns the level required to use the item.
     *
     * @return the level required to use the item
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the name of the item.
     *
     * @return the name of the item
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the sprite image for the item.
     *
     * @return the sprite image for the item
     */
    public ImageView getSprite() {
        return this.sprite;
    }
}
