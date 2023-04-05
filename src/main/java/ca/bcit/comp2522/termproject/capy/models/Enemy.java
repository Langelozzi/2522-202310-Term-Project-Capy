package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;

import java.util.List;

/**
 * Enemy class represents an enemy in the game. Subclass of Character.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Enemy extends Character {

    // INITIALIZATION  =================================================================================================

    private static final double ENEMY_SPEED = 1.0;
    private static final int ATTACK_MULTIPLIER = 3;
    private static final String DEFAULT_SPRITE_PATH = "file:src/main/resources/ca/bcit/comp2522/termproject/"
            + "capy/sprites/crocodile.png";
    private static final String LEFT_FOOT_SPRITE_PATH = "file:src/main/resources/ca/bcit/comp2522/termproject/"
            + "capy/sprites/crocodile-left-foot-forward.png";
    private static final String RIGHT_FOOT_SPRITE_PATH = "file:src/main/resources/ca/bcit/comp2522/termproject/"
            + "capy/sprites/crocodile-right-foot-forward.png";
    private static final String DEAD_SPRITE_PATH = "file:src/main/resources/ca/bcit/comp2522/termproject/"
            + "capy/sprites/crocodile-rip.png";

    private final int difficulty;
    private final int attackDamage;
    private int animationCounter;
    private final SugarCane sugarCane;
    private int hitsTaken;

    /**
     * Instantiate a new Enemy with a specific difficulty.
     *
     * @param speed      The speed at which the enemy can move.
     * @param difficulty the difficulty of the enemy, decides its amount of attack damage
     */
    public Enemy(final int speed, final int difficulty) {
        super(new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/crocodile.png"));
        this.difficulty = difficulty;
        this.attackDamage = ATTACK_MULTIPLIER * difficulty;
        animationCounter = 0;
        this.sugarCane = new SugarCane(this.difficulty);
    }

    // GETTERS AND SETTERS =============================================================================================

    /**
     * Get the Enemy difficulty.
     *
     * @return the enemy's difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Get the Enemy attack damage.
     *
     * @return the enemy's attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Get the number of hits that the enemy has taken.
     *
     * @return the number of hits that the enemy has taken
     */
    public int getHitsTaken() {
        return hitsTaken;
    }

    /**
     * Set the number of hits that the enemy has taken.
     *
     * @param hitsTaken the number of hits that the enemy has taken
     */
    public void setHitsTaken(final int hitsTaken) {
        this.hitsTaken = hitsTaken;
    }

    /**
     * Get the SugarCane object that the enemy is holding.
     *
     * @return the SugarCane object that the enemy is holding
     */
    public SugarCane getSugarCane() {
        return sugarCane;
    }

    // ENEMY ACTIONS ===================================================================================================

    /**
     * Check if the enemy is hit by a bullet.
     *
     * @param bullet the bullet to check for collision with the enemy
     * @return true if the enemy is hit by the bullet, false otherwise
     */
    public boolean isHitByBullet(final Bullet bullet) {
        return this.getSprite().getBoundsInParent().intersects(bullet.getBullet().getBoundsInParent());
    }

    /**
     * Set the sprite of the enemy to its dead state.
     */
    public void setDeadSprite() {
        this.getSprite().setImage(new Image(DEAD_SPRITE_PATH));
    }

    /**
     * Updates the sprite of the enemy by changing the image based on the animation counter.
     */
    public void updateSprite() {
        animationCounter++;
        Image updatedImage;
        if (animationCounter % 30 < 10) {
            updatedImage = new Image(LEFT_FOOT_SPRITE_PATH);
        } else if (animationCounter % 30 < 20) {
            updatedImage = new Image(DEFAULT_SPRITE_PATH);
        } else {
            updatedImage = new Image(RIGHT_FOOT_SPRITE_PATH);
        }
        this.getSprite().setImage(updatedImage);
    }

    /**
     * Updates the enemy by rotating the sprite to face the player and moving it towards the player.
     *
     * @param player  The player that the enemy will move towards.
     * @param enemies A list of all the enemies in the game.
     */
    public void update(final Player player, final List<Enemy> enemies) {
        // Calculate the angle between the enemy and the player
        double deltaX = player.getSprite().getLayoutX() - this.getSprite().getLayoutX();
        double deltaY = player.getSprite().getLayoutY() - this.getSprite().getLayoutY();
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));

        // Rotate the enemy sprite to face the player
        this.getSprite().setRotate(angle + 90); // Add 90 degrees to make the top of the image (head) face the player

        // Move the enemy towards the player
        this.moveTowardsPlayer(player, enemies);
    }

    /**
     * Reset the enemy state.
     */
    public void reset() {
        this.setHitsTaken(0);
        this.setHitPoints(DEFAULT_HIT_POINTS);
    }

    // ENEMY MOVEMENT ==================================================================================================

    /**
     * Moves the enemy towards the player.
     *
     * @param player  The player that the enemy will move towards.
     * @param enemies A list of all the enemies in the game.
     */
    public void moveTowardsPlayer(final Player player, final List<Enemy> enemies) {
        double deltaX = player.getSprite().getLayoutX() - this.getSprite().getLayoutX();
        double deltaY = player.getSprite().getLayoutY() - this.getSprite().getLayoutY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double moveX = ENEMY_SPEED * deltaX / distance;
        double moveY = ENEMY_SPEED * deltaY / distance;

        double newX = this.getSprite().getLayoutX() + moveX;
        double newY = this.getSprite().getLayoutY() + moveY;

        // Check for collision with other enemies before updating the position
        if (!isEnemyCollision(enemies, newX, newY)) {
            this.getSprite().setLayoutX(newX);
            this.getSprite().setLayoutY(newY);
        }
        this.updateSprite();
    }

    /**
     * Checks if an enemy would collide with another enemy if it was moved to the specified position.
     *
     * @param enemies      A list of all the enemies in the game.
     * @param newX         The x-coordinate of the position that the enemy would be moved to.
     * @param newY         The y-coordinate of the position that the enemy would be moved to.
     * @return True if the enemy would collide with another enemy, false otherwise.
     */
    private boolean isEnemyCollision(
            final List<Enemy> enemies,
            final double newX,
            final double newY) {
        for (Enemy enemy : enemies) {
            if (enemy == this) {
                continue;
            }

            double deltaX = newX - enemy.getSprite().getLayoutX();
            double deltaY = newY - enemy.getSprite().getLayoutY();
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            double minDistance = 50; // Adjust this value for the minimum distance between enemies

            if (distance < minDistance) {
                return true;
            }
        }
        return false;
    }
}
