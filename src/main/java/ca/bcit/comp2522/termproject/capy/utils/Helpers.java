package ca.bcit.comp2522.termproject.capy.utils;

import ca.bcit.comp2522.termproject.capy.CapyApplication;
import ca.bcit.comp2522.termproject.capy.models.Game;
import ca.bcit.comp2522.termproject.capy.controllers.GameMenuController;
import ca.bcit.comp2522.termproject.capy.controllers.LeaderboardController;
import ca.bcit.comp2522.termproject.capy.controllers.UpgradesController;
import ca.bcit.comp2522.termproject.capy.controllers.WinViewController;
import ca.bcit.comp2522.termproject.capy.models.Player;
import ca.bcit.comp2522.termproject.capy.models.SceneController;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 * Helpers class contains some generic helper methods for the game.
 *
 * @author COMP2522 Group 13
 * @version 1.0.0
 */
public final class Helpers {

    // INITIALIZATION  =================================================================================================
    private static MediaPlayer backgroundMusicPlayer;
    private static MediaPlayer gunshotPlayer;

    private static MediaPlayer playPickUpSound;
//    private static MediaPlayer victoryMusicPlayer;

    private Helpers() {

    }

    /**
     * Return the controller class for a specific fxml file.
     *
     * @param fxmlFileName the name of the fxml file
     * @return the controller object of fxml file
     * @throws RuntimeException if the fxml file fails to load
     */
    public static SceneController getFxmlController(final String fxmlFileName) {
        URL fxmlFile = CapyApplication.class.getResource(fxmlFileName);
        FXMLLoader loader = new FXMLLoader(fxmlFile);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return loader.getController();
    }

    // HELPER METHODS  =================================================================================================

    /**
     * Change the scene on the stage.
     *
     * @param scene the scene to change to
     */
    public static void changeScene(final Scene scene) {
        CapyApplication.getStage().setScene(scene);
        CapyApplication.getStage().show();
    }

    /**
     * Open the GameMenu scene on the stage.
     */
    public static void openGameMenu() {
        // Game.setHasSavedGame(true);
        Game.setPaused(true);
        GameMenuController menuController = (GameMenuController) Helpers.getFxmlController(
                "game-menu-view.fxml"
        );
        Helpers.changeScene(menuController.getScene());
    }

    /**
     * Open the Win screen.
     */
    public static void showWinScreen(final Player player) {
        Game.setPaused(true);
        Game.setHasSavedGame(false);

        WinViewController winViewController = (WinViewController) Helpers.getFxmlController(
                "win-view.fxml"
        );
        Scene winScene = winViewController.getScene();
        winViewController.setListeners(winScene, player);
        Helpers.changeScene(winScene);
    }

    public static void showLeaderboard(final boolean fromMenu, final Player player) {
        Game.setPaused(true);

        LeaderboardController leaderboardController = (LeaderboardController) Helpers.getFxmlController(
                "leaderboard-view.fxml"
        );
        leaderboardController.setFromMenu(fromMenu);

        if (!fromMenu) {
            Game.setHasSavedGame(false);
            boolean newHighScore = leaderboardController.checkForNewHighScore(player);
            if (newHighScore) {
                leaderboardController.showNewHighScoreForm();
            }
        }

        Scene winScene = leaderboardController.getScene();
        Helpers.changeScene(winScene);
    }

    /**
     * Opens the upgrades' menu. Pauses the game and switches to the upgrades menu scene.
     */
    public static void openUpgradesMenu(String itemType) {
        Game.setPaused(true);
        UpgradesController upgradesController = (UpgradesController) Helpers.getFxmlController(
                "upgrade-menu-view.fxml"
        );
        upgradesController.initData(itemType);
        Helpers.changeScene(upgradesController.getScene());
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    /**
     * Open the Game Over screen.
     *
     * @param player the player object to display score and other relevant information
     */
    public static void showGameOverScreen(final Player player) {
        Game.setPaused(true);
        Game.setHasSavedGame(false);
        GameMenuController menuController = (GameMenuController) Helpers.getFxmlController(
                "game-over-view.fxml"
        );
        Helpers.changeScene(menuController.getScene());
    }

    public static void playBackgroundMusic() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            return;
        }
        String musicFile = "/ca/bcit/comp2522/termproject/capy/audio/okay-i-pull-up.mp3";
        Media media = new Media(Helpers.class.getResource(musicFile).toExternalForm());
        backgroundMusicPlayer = new MediaPlayer(media);

        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusicPlayer.play();
    }
//    public static void stopBackgroundMusic() {
//        if (backgroundMusicPlayer != null && backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
//            backgroundMusicPlayer.stop();
//        }
//    }



    public static void playGunshotSound() {
        String gunshotFile = "/ca/bcit/comp2522/termproject/capy/audio/bow-shot-mc.mp3"; // Replace with the path to your gunshot audio file
        Media gunshotMedia = new Media(Helpers.class.getResource(gunshotFile).toExternalForm());
        gunshotPlayer = new MediaPlayer(gunshotMedia);

        gunshotPlayer.play();
    }

    public static void playPickUpSound() {
        String pickupFile = "/ca/bcit/comp2522/termproject/capy/audio/mc-pick-up.mp3"; // Replace with the path to your gunshot audio file
        Media pickupMedia = new Media(Helpers.class.getResource(pickupFile).toExternalForm());
        playPickUpSound = new MediaPlayer(pickupMedia);

        playPickUpSound.play();
    }


//    public static void playVictoryMusic() {
//        String musicFile = "/ca/bcit/comp2522/termproject/capy/audio/okay-i-pull-up.mp3"; // Replace with the path to your victory audio file
//        Media media = new Media(Helpers.class.getResource(musicFile).toExternalForm());
//        victoryMusicPlayer = new MediaPlayer(media);
//
//        victoryMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // This will make the victory music loop indefinitely
//        victoryMusicPlayer.play();
//    }


//    public static MediaPlayer playMainMenuMusic() {
//        String musicFile = "/ca/bcit/comp2522/termproject/capy/audio/okay-i-pull-up.mp3";
//        Media media = new Media(Helpers.class.getResource(musicFile).toExternalForm());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//
//        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // This will make the background music loop indefinitely
//        mediaPlayer.play();
//
//        return mediaPlayer;
//    }


}



