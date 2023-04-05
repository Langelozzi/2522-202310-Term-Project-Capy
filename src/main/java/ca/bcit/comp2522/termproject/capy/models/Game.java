package ca.bcit.comp2522.termproject.capy.models;

import ca.bcit.comp2522.termproject.capy.controllers.WaveMessageController;

import ca.bcit.comp2522.termproject.capy.utils.Helpers;
import javafx.animation.AnimationTimer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


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

        availableItems.add(new Weapon(
            new ImageView(new Image(spritesPath + "level-1-weapon.png", 0, imageHeigh, true, true)), 
            100, 1, "handgun", 3,
            new Image(spritesPath + "player_weapon_1.png")));
        availableItems.add(new Weapon(
            new ImageView(new Image(spritesPath + "level-2-weapon.png", 0, imageHeigh, true, true)), 
            0 /*stub to test menu */, 2, "rifle", 15,
            new Image(spritesPath + "player_weapon_2.png")));
        availableItems.add(new Weapon(
            new ImageView(new Image(spritesPath + "level-3-weapon.png", 0, imageHeigh, true, true)), 
            0 /*stub to test menu */, 3, "automatic rifle", 25,
            new Image(spritesPath + "player_weapon_3.png")));
        availableItems.add(new Weapon(
            new ImageView(new Image(spritesPath + "level-4-weapon.png", 0, imageHeigh, true, true)), 
            600, 4, "blaster", 35,
            new Image(spritesPath + "player_weapon_4.png")));

        availableItems.add(new Armour(new ImageView(new Image(spritesPath + "level-1-armor.png",
                0, imageHeigh, true, true)), 600, 1, "armor_1", 35));
        availableItems.add(new Armour(new ImageView(new Image(spritesPath + "level-1-armor.png",
                0, imageHeigh, true, true)), 0 /*stub to test menu */, 2, "armor_2", 45));
        availableItems.add(new Armour(new ImageView(new Image(spritesPath + "level-1-armor.png",
                0, imageHeigh, true, true)), 0 /*stub to test menu */, 3, "armor_3", 55));
    }

    private ArrayList<Level> levels;
    private ListIterator<Level> levelsIterator;
    private Level currentLevel;
    private Player player;
    private int waveCount;
    private AnimationTimer gameLoop;

    /**
     * Instantiate a new Game object.
     */
    public Game() {
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
     * @throws Exception
     */
    public void start() throws Exception {
        Game.setHasSavedGame(false);

        this.player = new Player(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/player_weapon_1.png"),
                getWeaponForLevel(1),
                getArmourForLevel(1)
        );

        this.levels = generateLevels();
        // this.levels = new ArrayList<Level>() {{
        //     add(new Level(player, 3, 1));
        // }};
        this.levelsIterator = this.levels.listIterator();
        this.currentLevel = this.levelsIterator.next();
        this.waveCount = 1;

        Game.setHasSavedGame(true);
        Game.setPaused(false);

        showWaveMessage();

        Helpers.delay(3000, this::startGameLoop);
    }

    public Player getPlayer(){
        return this.player;
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                if (!Game.paused) {
                    boolean levelComplete = currentLevel.play();

                    if (levelComplete && !levelsIterator.hasNext()) {
                        onWinGame(this);
                    } else if (levelComplete) {
                        startNextWave(this);
                    } else if (player.getHitPoints() <= 0) {
                        handleGameOver();
                    }
                }
            }
        };
        gameLoop.start();
    }

    private void onWinGame(AnimationTimer gameLoop) {
        gameLoop.stop();
        Helpers.showWinScreen(this.player);
    }

    private boolean hasCollided(final Bullet bullet, final Enemy enemy) {
        return bullet.getBullet().intersects(enemy.getSprite().getBoundsInLocal());
    }

    private ArrayList<Level> generateLevels() {
        ArrayList<Level> levels = new ArrayList<>();

        int minDifficulty = 1;
        int maxDifficulty = 3;
        int minNumEnemies = 3;
        int maxNumEnemies = 6;

        for (int difficulty = minDifficulty; difficulty <= maxDifficulty; difficulty++) {
            for (int numEnemies = minNumEnemies; numEnemies <= maxNumEnemies; numEnemies++) {
                levels.add(new Level(this, this.player, numEnemies, difficulty));
            }
        }

        return levels;
    }

    private void startNextWave(AnimationTimer gameLoop) {
        gameLoop.stop();

        this.waveCount++;
        showWaveMessage();

        Helpers.delay(3000, () -> {
            currentLevel = levelsIterator.next();
            gameLoop.start();
        });
    }

    private void showWaveMessage() {
        WaveMessageController waveMessageController = (WaveMessageController) Helpers.getFxmlController(
                "wave-message-view.fxml"
        );
        waveMessageController.setWaveNumber(this.waveCount);
        Helpers.changeScene(waveMessageController.getScene());
    }

    private static Weapon getWeaponForLevel(int level) throws Exception{
        for(Item item: availableItems){
            if(Weapon.class.isInstance(item) && item.getLevel() == level)
                return (Weapon)item;
        }
        throw new Exception("No weapon for level: " + level);
    }

    private static Armour getArmourForLevel(int level) throws Exception{
        for(Item item: availableItems){
            if(Armour.class.isInstance(item) && item.getLevel() == level)
                return (Armour)item;
        }
        throw new Exception("No armour for level: " + level);
    }

    /**
     * Handles game over events.
     */
    public void handleGameOver() {
        gameLoop.stop();
        Helpers.showGameOverScreen(this.player);
    }

}

