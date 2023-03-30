package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.ImageView;

/**
 * Weapon class represents a weapon that a player can buy and use to inflict damage to enemies.
 * Subclass of Item.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Weapon extends Item {

    // INITIALIZATION  =================================================================================================

    private int damagepoints;

    /**
     * Instantiate a new Weapon object.
     *
     * @param sprite       the sprite image of the weapon
     * @param cost         the cost of the weapon
     * @param level        the level of the weapon
     * @param name         the name of the weapon
     * @param damagepoints the damage points of the weapon
     */
    public Weapon(
            final ImageView sprite,
            final int cost,
            final int level,
            final String name,
            final int damagepoints) {
        super(sprite, cost, level, name);
        this.damagepoints = damagepoints;
    }

    // GETTERS AND SETTERS =============================================================================================

    /**
     * Get the damage points of the weapon.
     *
     * @return the damage points of the weapon
     */
    public int getDamagepoints() {
        return this.damagepoints;
    }
}
