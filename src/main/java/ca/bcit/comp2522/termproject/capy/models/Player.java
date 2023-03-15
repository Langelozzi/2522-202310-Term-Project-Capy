package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;

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
    public void move(final Direction direction) {
        final int movementVariable = 3;

        switch (direction) {
            case UP -> this.getSprite().setLayoutY(this.getSprite().getLayoutY() - movementVariable);
            case DOWN -> this.getSprite().setLayoutY(this.getSprite().getLayoutY() + movementVariable);
            case LEFT -> this.getSprite().setLayoutX(this.getSprite().getLayoutX() - movementVariable);
            case RIGHT -> this.getSprite().setLayoutX(this.getSprite().getLayoutX() + movementVariable);
            default -> {
                return;
            }
        }
    }
}
