package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.ImageView;

public abstract class Item {
    private final ImageView sprite;
    private final int cost;
    private final int level;
    private final String name;

    public Item(ImageView sprite, int cost, int level, String name) {
        this.sprite = sprite;
        this.cost = cost;
        this.level = level;
        this.name = name;
    }

    public int getCost() {
        return this.cost;
    }

    public int getLevel() {
        return this.level;
    }

    public String getName() {
        return this.name;
    }
}
