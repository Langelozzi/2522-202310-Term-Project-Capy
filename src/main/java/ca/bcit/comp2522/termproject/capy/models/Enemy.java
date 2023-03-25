package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;

/**
 * Enemy class represents an enemy in the game. Subclass of Character.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Enemy extends Character {

    private static final double ENEMY_SPEED = 1.0;
    private static final int ATTACK_MULTIPLIER = 3;
    private final int difficulty;
    private final int attackDamage;
    // private SugarCane sugarCane;

    private static final String DEFAULT_SPRITE_PATH = "file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/crocodile.png";
    private static final String LEFT_FOOT_SPRITE_PATH = "file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/crocodile-left-foot-forward.png";
    private static final String RIGHT_FOOT_SPRITE_PATH = "file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/crocodile-right-foot-forward.png";
    private int animationCounter;

    /**
     * Instantiate a new Enemy with a specific difficulty.
     * @param difficulty the difficulty of the enemy, decides its amount of attack damage
     */
    public Enemy(int speed, final int difficulty) {
        super(new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/crocodile.png"));
        this.difficulty = difficulty;
        this.attackDamage = ATTACK_MULTIPLIER * difficulty;
        animationCounter = 0;
    }

    public static void moveTowardsPlayer(Enemy enemy, Player player) {
        double deltaX = player.getSprite().getLayoutX() - enemy.getSprite().getLayoutX();
        double deltaY = player.getSprite().getLayoutY() - enemy.getSprite().getLayoutY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double moveX = ENEMY_SPEED * deltaX / distance;
        double moveY = ENEMY_SPEED * deltaY / distance;

        enemy.getSprite().setLayoutX(enemy.getSprite().getLayoutX() + moveX);
        enemy.getSprite().setLayoutY(enemy.getSprite().getLayoutY() + moveY);

        enemy.updateSprite();
    }

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

    public void update(Player player) {
        // Calculate the angle between the enemy and the player
        double deltaX = player.getSprite().getLayoutX() - this.getSprite().getLayoutX();
        double deltaY = player.getSprite().getLayoutY() - this.getSprite().getLayoutY();
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));

        // Rotate the enemy sprite to face the player
        this.getSprite().setRotate(angle + 90); // Add 90 degrees to make the top of the image (head) face the player

        // Move the enemy towards the player
        Enemy.moveTowardsPlayer(this, player);
    }


    /**
     * Get the Enemy difficulty.
     * @return the enemy's difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Get the Enemy attack damage.
     * @return the enemy's attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    @Override
    public void move(final Direction direction) {

    }
}
