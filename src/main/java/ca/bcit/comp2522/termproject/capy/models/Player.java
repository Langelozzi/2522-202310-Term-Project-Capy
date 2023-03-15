package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends Character {
    private int points;

    // private Weapon weapon;
    // private Armour armour;
    private Level currentLevel;

    public Player(final Image sprite) {
        super(sprite);

        this.points = 0;
        this.currentLevel = null;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(final int newPoints) {
        this.points = Math.max(newPoints, 0);
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(final Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void shoot() {};

    @Override
    public void move() {

    }
}
