package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Bullet {
    private Circle bullet;
    private double speed;
    private double directionX;
    private double directionY;

    public Bullet(double startX, double startY, double directionX, double directionY, int speed) {
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;

        this.bullet = new Circle(startX, startY, 5); // Set the width and height of the rectangle
        this.bullet.setFill(Color.YELLOW); // Set the color of the rectangle
    }

    public void update() {
        bullet.setCenterX(bullet.getCenterX() + directionX * speed);
        bullet.setCenterY(bullet.getCenterY() + directionY * speed);
    }

    public Circle getBullet() {
        return bullet;
    }
}
