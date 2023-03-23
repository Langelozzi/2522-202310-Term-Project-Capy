package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.ImageView;

public class Armour extends Item{
    private int hitpoints;

    public Armour(ImageView sprite, int cost, int level, String name, int hitpoints) {
        super(sprite, cost, level, name);
        this.hitpoints = hitpoints;
    }

    public int getHitpoints() {
        return this.hitpoints;
    }
}
