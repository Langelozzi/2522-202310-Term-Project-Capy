package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;

public class Player extends Character {
    public static final int DEFAULT_MOVEMENT_SPEED = 2;

    private int points;
    private Level currentLevel;

    private Weapon weapon;
    private Armour armour;

    private int movementSpeed;

    public Player(final Image sprite) {
        super(sprite);

        this.points = 0;
        this.currentLevel = null;
        this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
    }

    public Player(final Image sprite, final int playerSpeed) {
        super(sprite);

        if (playerSpeed < 0) {
            throw new IllegalArgumentException("Speed must be a positive integer");
        }

        this.points = 0;
        this.currentLevel = null;
        this.movementSpeed = playerSpeed;
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

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(final int newSpeed) {
        if (newSpeed < 0) {
            throw new IllegalArgumentException("Speed must be a positive integer");
        }
        this.movementSpeed = newSpeed;
    }

    public void shoot() {};

    @Override
    public void move(final Direction direction) {
        switch (direction) {
            case UP -> this.getSprite().setLayoutY(this.getSprite().getLayoutY() - this.movementSpeed);
            case DOWN -> this.getSprite().setLayoutY(this.getSprite().getLayoutY() + this.movementSpeed);
            case LEFT -> this.getSprite().setLayoutX(this.getSprite().getLayoutX() - this.movementSpeed);
            case RIGHT -> this.getSprite().setLayoutX(this.getSprite().getLayoutX() + this.movementSpeed);
            default -> {
                return;
            }
        }
    }
}
