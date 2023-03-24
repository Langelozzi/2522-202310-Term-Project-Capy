package ca.bcit.comp2522.termproject.capy;

import ca.bcit.comp2522.termproject.capy.controllers.KeyboardInputController;
import ca.bcit.comp2522.termproject.capy.controllers.MouseInputController;
import ca.bcit.comp2522.termproject.capy.models.Level;
import ca.bcit.comp2522.termproject.capy.models.Player;
import javafx.scene.image.Image;

public class Game {

    private Level currentLevel;

    private final Player player;

    public static final int BACKGROUND_WIDTH = 865;
    public static final int BACKGROUND_HEIGHT = 645;

    private static boolean hasSavedGame = false;

    public Game() {
        this.player = new Player(
                new Image("file:src/main/resources/ca/bcit/comp2522/termproject/capy/sprites/test_player.png")
        );
        final int numberOfEnemies = 3;
        this.currentLevel = new Level(player, numberOfEnemies);

        KeyboardInputController keyboardInputController = new KeyboardInputController();
        keyboardInputController.assignKeyboardInput(
                this.player, this.currentLevel.getScene()
        );

        MouseInputController rotationController = new MouseInputController();
        rotationController.makeCursorRotatable(this.player, this.currentLevel.getScene());
    }

    public static boolean hasSavedGame() {
        return hasSavedGame;
    }

    public static void setHasSavedGame(final boolean savedGame) {
        hasSavedGame = savedGame;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public void start() {
        CapyApplication.getStage().setScene(this.currentLevel.getScene());
        CapyApplication.getStage().show();
    }
}
