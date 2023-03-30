package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SugarCane {

    private final int value;
    private boolean dropped;
    private boolean collected;

    private final ImageView sprite;

    SugarCane(final int value) {
        this.value = value;
        this.dropped = false;
        this.collected = false;

        this.sprite = new ImageView(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/sugar_cane.png")
        );
    }

    public int getValue() {
        return value;
    }

    public boolean isDropped() {
        return dropped;
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public ImageView getSprite() {
        return sprite;
    }
}
