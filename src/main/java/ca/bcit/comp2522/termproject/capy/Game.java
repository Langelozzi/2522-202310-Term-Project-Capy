package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.controllers.KeyboardInputController;
import ca.bcit.comp2522.termproject.capy.controllers.MouseInputController;
import ca.bcit.comp2522.termproject.capy.models.Level;
import ca.bcit.comp2522.termproject.capy.models.Player;
import ca.bcit.comp2522.termproject.capy.models.Enemy;
import ca.bcit.comp2522.termproject.capy.models.Bullet;

import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Game class manages the entire game, including the player, levels and scenes.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Game {

    /**
     * The background/window width of the game.
     */
    public static final int BACKGROUND_WIDTH = 1160;
    /**
     * The background/window height of the game.
     */
    public static final int BACKGROUND_HEIGHT = 864;

    private static boolean hasSavedGame = false;

    private final HashMap<Integer, Level> levels = new HashMap<>();
    private Level currentLevel;
    private final Player player;

    /**
     * Instantiate a new Game object starting current level at level 1.
     */
    public Game() {
        this.player = new Player(new Image("file:src/main/resources/ca/bcit/comp2522/termproject/"
                + "capy/sprites/test_player.png"));

        final int numberOfEnemies = 3;
        Level level1 = new Level(player, numberOfEnemies);

        this.levels.put(1, level1);

        this.currentLevel = this.levels.get(1);

        KeyboardInputController keyboardInputController = new KeyboardInputController();
        keyboardInputController.assignKeyboardInput(
                this.player, this.currentLevel.getScene(), this.currentLevel.getSwampBoundary()
        );

        MouseInputController rotationController = new MouseInputController();
        rotationController.makeCursorRotatable(player, player, currentLevel.getScene());
        rotationController.handleShooting(player, currentLevel.getScene(), currentLevel.getGameLayer());

        startGameLoop();
    }


    /**
     * Instantiate a new game object starting at a level other than level 1.
     *
     * @param startingLevel the level to start the game at.
     */
    public Game(final Level startingLevel) {
        this.player = new Player(new Image("file:src/main/resources/ca/bcit/comp2522/termproject/"
                + "capy/sprites/test_player.png"));
    }

    /**
     * Return if there is saved data in this game object.
     *
     * @return true if there is a saved game, false otherwise
     */
    public static boolean hasSavedGame() {
        return hasSavedGame;
    }

    /**
     * Set the value of savedGame.
     *
     * @param savedGame the new value of savedGame
     */
    public static void setHasSavedGame(final boolean savedGame) {
        hasSavedGame = savedGame;
    }

    /**
     * Return the current Level that the player is on.
     *
     * @return the current Level the player is on
     */
    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    /**
     * Start a new game at level 1.
     */
    public void startNew() {
        Level level1 = this.levels.get(1);
        level1.resetLevel();
        CapyApplication.getStage().setScene(level1.getScene());
        CapyApplication.getStage().show();
    }

    private void updateEnemies() {
        LocalDateTime currentTime = LocalDateTime.now();
        for (int i = 0; i < currentLevel.getEnemies().size(); i++) {
            Enemy enemy = currentLevel.getEnemies().get(i);
            enemy.update(player, currentLevel.getEnemies());
            if (player.isCollidingWithEnemy(enemy)) {
                // Check if the player's hit points are already 0 or below
                if (player.getHitPoints() <= 0) {
                    // TODO: Game over logic
                } else if (Duration.between(currentLevel.getLastDamageTimes().get(i), currentTime).getSeconds() >= 1) {
                    int damage = 20; // Player takes 20 damage per collision
                    player.setHitPoints(player.getHitPoints() - damage);
                    currentLevel.updatePlayerOverlayInformation(); // Update the player's health bar and other overlay information
                    currentLevel.getLastDamageTimes().set(i, currentTime);
                    // Check if the player's hit points have reached 0 or below after taking damage
                    if (player.getHitPoints() <= 0) {
                        // TODO: Game over logic
                    }
                }
            }
        }
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                updateEnemies();
                updateBullets();
            }
        };
        gameLoop.start();
    }

    private void updateBullets() {
        // Update bullets
        for (Bullet bullet : player.getBullets()) {
            bullet.update();
        }

        // Check for bullet collisions with enemies
        Iterator<Bullet> bulletIterator = player.getBullets().iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            boolean collided = false;

            Iterator<Enemy> enemyIterator = currentLevel.getEnemies().iterator();
            while (enemyIterator.hasNext() && !collided) {
                Enemy enemy = enemyIterator.next();
                if (hasCollided(bullet, enemy)) {
                    collided = true;
                    int pointsGained = 10; // Set the points gained when an enemy is hit
                    player.setPoints(player.getPoints() + pointsGained);

                    int damage = 20; // Set the damage dealt to an enemy upon collision
                    enemy.setHitPoints(enemy.getHitPoints() - damage);
                    if (enemy.getHitPoints() <= 0) {
                        enemyIterator.remove(); // Remove the enemy from the list if its hit points reach 0
                    }
                }
            }

            // Remove bullets that are off-screen or have collided
            if (collided || isOffScreen(bullet.getBullet())) {
                bulletIterator.remove();
            }
        }
    }

    private boolean isOffScreen(Rectangle bullet) {
        double x = bullet.getX();
        double y = bullet.getY();

        return x < 0 || x > BACKGROUND_WIDTH || y < 0 || y > BACKGROUND_HEIGHT;
    }

    private boolean hasCollided(Bullet bullet, Enemy enemy) {
        return bullet.getBullet().intersects(enemy.getSprite().getBoundsInLocal());
    }
}

