package ca.bcit.comp2522.termproject.capy;

import javafx.scene.image.ImageView;

public abstract class Character {
    private ImageView sprite;
    private int hitPoints;

    Character(final ImageView sprite) {
        this.hitPoints = 0;
        this.sprite = sprite;
    }

    public boolean isAlive() {
        return hitPoints > 0;
    }

    public abstract void move();
}
