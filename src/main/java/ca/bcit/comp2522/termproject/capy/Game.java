package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.controllers.KeyboardInputController;
import ca.bcit.comp2522.termproject.capy.controllers.MouseInputController;
import ca.bcit.comp2522.termproject.capy.models.*;

import javafx.animation.AnimationTimer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;


/**
 * Game class manages the entire game, including the player, levels and scenes.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public class Game {

    // INITIALIZATION  =================================================================================================

    /**
     * The background/window width of the game.
     */
    public static final int BACKGROUND_WIDTH = 1160;
    /**
     * The background/window height of the game.
     */
    public static final int BACKGROUND_HEIGHT = 864;
    private static boolean hasSavedGame = false;
    private static boolean paused = false;
    private static final List<Item> availableItems = new ArrayList<Item>();

    static {
        final String spritesPath = "file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/";
        final int imageHeigh = 100;
        availableItems.add(new Weapon(new ImageView(new Image(spritesPath + "level-1-weapon.png",
                0, imageHeigh, true, true)), 100, 1, "handgun", 3));
        availableItems.add(new Weapon(new ImageView(new Image(spritesPath + "level-2-weapon.png",
                0, imageHeigh, true, true)), 300, 2, "rifle", 15));
        availableItems.add(new Weapon(new ImageView(new Image(spritesPath + "level-3-weapon.png",
                0, imageHeigh, true, true)), 400, 3, "automatic rifle", 25));
        availableItems.add(new Weapon(new ImageView(new Image(spritesPath + "level-4-weapon.png",
                0, imageHeigh, true, true)), 600, 4, "blaster", 35));
    }

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
        rotationController.handleShooting(player, currentLevel.getScene(), bullet -> {
            currentLevel.getGameLayer().getChildren().add(bullet.getBullet());
            bullet.getBullet().toBack();
            player.getSprite().toFront();
        });

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

    // GETTERS AND SETTERS =============================================================================================


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
     * The list of available items in the game.
     *
     * @return the list of available items
     */
    public static List<Item> getAvailableItems() {
        return availableItems;
    }

    /**
     * Returns whether the game is currently paused.
     *
     * @return true if the game is paused, false otherwise
     */
    public static boolean isPaused() {
        return paused;
    }

    /**
     * Sets the pause state of the game.
     *
     * @param paused true to pause the game, false to resume
     */
    public static void setPaused(final boolean paused) {
        Game.paused = paused;
    }

    // GAME LOGIC ======================================================================================================

    /**
     * Return if there is saved data in this game object.
     *
     * @return true if there is a saved game, false otherwise
     */
    public static boolean hasSavedGame() {
        return hasSavedGame;
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

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                if (!Game.paused) {
                    updateEnemies();
                    updateBullets();
                    currentLevel.checkSugarCaneCollected();
                    currentLevel.updatePlayerOverlayInformation();
                }
            }
        };
        gameLoop.start();
    }

    // ENEMY LOGIC =====================================================================================================

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

                    // Update the player's health bar and other overlay information
                    currentLevel.updatePlayerOverlayInformation();
                    currentLevel.getLastDamageTimes().set(i, currentTime);
                    // Check if the player's hit points have reached 0 or below after taking damage
                    if (player.getHitPoints() <= 0) {
                        // TODO: Game over logic
                    }
                }
            }
        }
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
                if (enemy.isHitByBullet(bullet)) {
                    collided = true;

                    // Increment the enemy's hits taken and remove it if it has taken 5 hits
                    enemy.setHitsTaken(enemy.getHitsTaken() + 1);
                    if (enemy.getHitsTaken() >= 5) {
                        enemyIterator.remove(); // Remove the enemy from the list

                        // Remove the enemy from the game layer
                        currentLevel.getGameLayer().getChildren().remove(enemy.getSprite());
                        enemy.setDeadSprite(); // Set the enemy sprite to the dead crocodile

                        this.currentLevel.dropSugarCane(enemy);

                        // TODO: Add points system
                    }
                    bulletIterator.remove(); // Remove the bullet from the list

                    // Remove the bullet from the game layer
                    currentLevel.getGameLayer().getChildren().remove(bullet.getBullet());
                }
            }

            // Remove bullets that are off-screen
            if (!collided && isOffScreen(bullet.getBullet())) {
                bulletIterator.remove();

                // Remove the bullet from the game layer
                currentLevel.getGameLayer().getChildren().remove(bullet.getBullet());
            }
        }
    }

    private boolean isOffScreen(final Circle bullet) {
        double x = bullet.getCenterX();
        double y = bullet.getCenterY();

        return x < 0 || x > BACKGROUND_WIDTH || y < 0 || y > BACKGROUND_HEIGHT;
    }

    private boolean hasCollided(final Bullet bullet, final Enemy enemy) {
        return bullet.getBullet().intersects(enemy.getSprite().getBoundsInLocal());
    }

    private void checkSugarCaneCollected() {

    }
}

