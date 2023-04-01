package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Bullet class represents a bullet in the game.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Bullet {
    public static final int BULLET_SPEED = 5;

    private Circle bullet;
    private double speed;
    private double directionX;
    private double directionY;

    /**
     * Instantiates a new Bullet with specified coordinates and speed.
     *
     * @param startX     the starting x coordinate
     * @param startY     the starting y coordinate
     * @param directionX the x direction of the bullet's movement
     * @param directionY the y direction of the bullet's movement
     */
    public Bullet(
            final double startX,
            final double startY,
            final double directionX,
            final double directionY) {
        this.speed = BULLET_SPEED;
        this.directionX = directionX;
        this.directionY = directionY;

        this.bullet = new Circle(startX, startY, 5); // Set the width and height of the rectangle
        this.bullet.setFill(Color.YELLOW); // Set the color of the rectangle
    }

    /**
     * Updates the bullet's position based on its speed and direction.
     */
    public void update() {
        bullet.setCenterX(bullet.getCenterX() + directionX * speed);
        bullet.setCenterY(bullet.getCenterY() + directionY * speed);
    }

    /**
     * Returns the Circle object representing the bullet.
     *
     * @return the bullet Circle object
     */
    public Circle getBullet() {
        return bullet;
    }
}
