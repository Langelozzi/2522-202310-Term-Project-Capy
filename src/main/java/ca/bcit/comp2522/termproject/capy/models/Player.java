package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.ImageView;

public class Player extends Character {
    private int points;

    // private Weapon weapon;
    // private Armour armour;
    private Level currentLevel;

    Player(final ImageView sprite, final Level firstLevel) {
        super(sprite);

        this.points = 0;
        this.currentLevel = firstLevel;
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
