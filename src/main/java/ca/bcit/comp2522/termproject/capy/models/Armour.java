package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.ImageView;

/**
 * Armour class represents an armor item that the player can purchase and equip. It is a subclass of Item.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Armour extends Item {
    private int hitpoints;

    /**
     * Constructs a new Armour with a specified sprite, cost, level, name and hitpoints.
     *
     * @param sprite    the sprite that represents the armour
     * @param cost      the cost of the armour in the store
     * @param level     the level required to unlock the armour
     * @param name      the name of the armour
     * @param hitpoints the hitpoints that the armour provides
     */
    public Armour(final ImageView sprite,
                  final int cost,
                  final int level,
                  final String name,
                  final int hitpoints) {
        super(sprite, cost, level, name);
        this.hitpoints = hitpoints;
    }

    /**
     * Gets the hitpoints that the armour provides.
     *
     * @return the hitpoints that the armour provides
     */
    public int getHitpoints() {
        return this.hitpoints;
    }
}
