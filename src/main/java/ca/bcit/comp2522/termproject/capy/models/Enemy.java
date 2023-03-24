package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;

/**
 * Enemy class represents an enemy in the game. Subclass of Character.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Enemy extends Character {
    private static final int ATTACK_MULTIPLIER = 3;
    private final int difficulty;
    private final int attackDamage;
    // private SugarCane sugarCane;

    /**
     * Instantiate a new Enemy with a specific difficulty.
     * @param difficulty the difficulty of the enemy, decides its amount of attack damage
     */
    public Enemy(final int difficulty) {
        super(new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/crocodile.png"));
        this.difficulty = difficulty;
        this.attackDamage = ATTACK_MULTIPLIER * difficulty;
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
