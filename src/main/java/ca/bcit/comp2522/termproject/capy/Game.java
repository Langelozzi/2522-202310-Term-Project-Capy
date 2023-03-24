package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.controllers.KeyboardInputController;
import ca.bcit.comp2522.termproject.capy.controllers.MouseInputController;
import ca.bcit.comp2522.termproject.capy.models.Level;
import ca.bcit.comp2522.termproject.capy.models.Player;
import javafx.scene.image.Image;

import java.util.HashMap;

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
    public static final int BACKGROUND_WIDTH = 865;
    /**
     * The background/window height of the game.
     */
    public static final int BACKGROUND_HEIGHT = 645;

    private static boolean hasSavedGame = false;

    private final HashMap<Integer, Level> levels = new HashMap<>();
    private Level currentLevel;
    private final Player player;

    /**
     * Instantiate a new Game object starting current level at level 1.
     */
    public Game() {
        this.player = new Player(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/test_player.png")
        );

        final int numberOfEnemies = 3;
        Level level1 = new Level(player, numberOfEnemies);

        this.levels.put(1, level1);

        this.currentLevel = this.levels.get(1);

        KeyboardInputController keyboardInputController = new KeyboardInputController();
        keyboardInputController.assignKeyboardInput(
                this.player, this.currentLevel.getScene()
        );

        MouseInputController rotationController = new MouseInputController();
        rotationController.makeCursorRotatable(this.player, this.currentLevel.getScene());
    }

    /**
     * Instantiate a new game object starting at a level other than level 1.
     * @param startingLevel the level to start the game at.
     */
    public Game(final Level startingLevel) {
        this.player = new Player(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/test_player.png")
        );
    }

    /**
     * Return if there is saved data in this game object.
     * @return true if there is a saved game, false otherwise
     */
    public static boolean hasSavedGame() {
        return hasSavedGame;
    }

    /**
     * Set the value of savedGame.
     * @param savedGame the new value of savedGame
     */
    public static void setHasSavedGame(final boolean savedGame) {
        hasSavedGame = savedGame;
    }

    /**
     * Return the current Level that the player is on.
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
}
