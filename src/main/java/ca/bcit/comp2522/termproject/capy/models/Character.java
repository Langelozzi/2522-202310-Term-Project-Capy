package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.ImageView;

public abstract class Character {
    private ImageView sprite;
    private int hitPoints;

    Character(final ImageView sprite) {
        this.hitPoints = 0;
        this.sprite = sprite;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int newHitPoints) {
        this.hitPoints = newHitPoints;
    }

    public boolean isAlive() {
        return hitPoints > 0;
    }

    public abstract void move();
}
