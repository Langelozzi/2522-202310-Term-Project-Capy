package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Player class represents a user's player character in the game. Subclass of Character.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Player extends Character {

    // INITIALIZATION  =================================================================================================

    /**
     * The default amount of sugar cane points a player starts with.
     */
    public static final int DEFAULT_SUGAR_CANE_POINTS = 0;
    /**
     * The default speed value for the player in the game.
     */
    public static final int DEFAULT_PLAYER_SPEED = 3;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int points;
    private Weapon weapon;
    private Armour armour;

    /**
     * Instantiate a Player object.
     *
     * @param sprite the Image to use as the player's ImageView sprite
     */
    public Player(final Image sprite, final Weapon initialWeapon) {
        super(sprite, DEFAULT_PLAYER_SPEED);

        this.points = DEFAULT_SUGAR_CANE_POINTS;
        this.weapon = initialWeapon;
    }

    /**
     * Instantiate a player object with a custom player speed.
     *
     * @param sprite      the Image to use as the player's ImageView sprite
     * @param playerSpeed the custom speed of the player
     */
    public Player(final Image sprite, final int playerSpeed) {
        super(sprite, playerSpeed);

        this.points = DEFAULT_SUGAR_CANE_POINTS;
    }

    // GETTERS AND SETTERS =============================================================================================

    /**
     * Get the current amount of points that the player has.
     *
     * @return the current amount of points that the player has
     */
    public int getPoints() {
        return points;
    }

    /**
     * Update the amount of points the player has.
     *
     * @param newPoints the updated amount of points
     */
    public void setPoints(final int newPoints) {
        this.points = Math.max(newPoints, 0);
    }

    public Weapon getWeapon() {
        return this.weapon;
    }
    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    // SHOOTING LOGIC  =================================================================================================

    /**
     * Check if the player's sprite is colliding with the given enemy's sprite.
     *
     * @param enemy the enemy to check for collision
     * @return true if the player's sprite is colliding with the enemy's sprite, false otherwise
     */
    public boolean isCollidingWithEnemy(final Enemy enemy) {
        return this.getSprite().getBoundsInParent().intersects(enemy.getSprite().getBoundsInParent());
    }

    /**
     * Shoots a bullet from the player's current position towards a specified target.
     *
     * @param mouseX the x-coordinate of the target
     * @param mouseY the y-coordinate of the target
     */
    public void shoot(final double mouseX, final double mouseY) {
        double deltaX = mouseX - getSprite().getLayoutX();
        double deltaY = mouseY - getSprite().getLayoutY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        Bullet bullet = new Bullet(getSprite().getLayoutX(), getSprite().getLayoutY(), directionX, directionY);
        bullets.add(bullet);
    }

    /**
     * Returns the ArrayList of bullets fired by the player.
     *
     * @return the ArrayList of bullets fired by the player
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    // SUGARCANE LOGIC  ================================================================================================

    /**
     * Collects the SugarCane item and adds its value to the player's points.
     *
     * @param sugarCane the SugarCane item to be collected
     */
    public void collectSugarCane(final SugarCane sugarCane) {
        sugarCane.setCollected(true);

        this.points += sugarCane.getValue();
    }

    public void reset() {
        this.points = DEFAULT_SUGAR_CANE_POINTS;
        this.setHitPoints(Character.DEFAULT_HIT_POINTS);
    }
}
