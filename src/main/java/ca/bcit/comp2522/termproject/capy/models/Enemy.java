package ca.bcit.comp2522.termproject.capy.models;

import javafx.scene.image.Image;

public class Enemy extends Character {
    private final int ATTACK_MULTIPLIER = 3;
    private final int difficulty;
    private final int attackDamage;
    // private SugarCane sugarCane;

    public Enemy(final int difficulty) {
        super(new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/crocodile.png"));
        this.difficulty = difficulty;
        this.attackDamage = ATTACK_MULTIPLIER * difficulty;
    }

    @Override
    public void move() {

    }
}
