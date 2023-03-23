package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.ImageView;

public class Weapon extends Item{
    private int damagepoints;

    public Weapon(ImageView sprite, int cost, int level, String name, int damagepoints) {
        super(sprite, cost, level, name);
        this.damagepoints = damagepoints;
    }

    public int getDamagepoints() {
        return this.damagepoints;
    }
}
