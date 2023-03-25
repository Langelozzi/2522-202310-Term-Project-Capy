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

    private static final double ENEMY_SPEED = 1.0;
    private static final int ATTACK_MULTIPLIER = 3;
    private static final String DEFAULT_SPRITE_PATH = "file:src/main/resources/ca/bcit/comp2522/termproject/"
            + "capy/sprites/crocodile.png";
    private static final String LEFT_FOOT_SPRITE_PATH = "file:src/main/resources/ca/bcit/comp2522/termproject/"
            + "capy/sprites/crocodile-left-foot-forward.png";
    private static final String RIGHT_FOOT_SPRITE_PATH = "file:src/main/resources/ca/bcit/comp2522/termproject/"
            + "capy/sprites/crocodile-right-foot-forward.png";
    private final int difficulty;
    private final int attackDamage;
    private int animationCounter;
    // private SugarCane sugarCane;

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
    }

    /**
     * Moves the enemy towards the player.
     *
     * @param enemy   The enemy that will be moved.
     * @param player  The player that the enemy will move towards.
     * @param enemies A list of all the enemies in the game.
     */
    public static void moveTowardsPlayer(final Enemy enemy, final Player player, final List<Enemy> enemies) {
        double deltaX = player.getSprite().getLayoutX() - enemy.getSprite().getLayoutX();
        double deltaY = player.getSprite().getLayoutY() - enemy.getSprite().getLayoutY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double moveX = ENEMY_SPEED * deltaX / distance;
        double moveY = ENEMY_SPEED * deltaY / distance;

        double newX = enemy.getSprite().getLayoutX() + moveX;
        double newY = enemy.getSprite().getLayoutY() + moveY;

        // Check for collision with other enemies before updating the position
        if (!isEnemyCollision(enemy, enemies, newX, newY)) {
            enemy.getSprite().setLayoutX(newX);
            enemy.getSprite().setLayoutY(newY);
        }
        enemy.updateSprite();
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
        Enemy.moveTowardsPlayer(this, player, enemies);
    }

    /**
     * Checks if an enemy would collide with another enemy if it was moved to the specified position.
     *
     * @param currentEnemy The enemy that is being checked for collision.
     * @param enemies      A list of all the enemies in the game.
     * @param newX         The x-coordinate of the position that the enemy would be moved to.
     * @param newY         The y-coordinate of the position that the enemy would be moved to.
     * @return True if the enemy would collide with another enemy, false otherwise.
     */
    private static boolean isEnemyCollision(
            final Enemy currentEnemy,
            final List<Enemy> enemies,
            final double newX,
            final double newY) {
        for (Enemy enemy : enemies) {
            if (enemy == currentEnemy) {
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

    @Override
    public void move(final Direction direction) {

    }
}
