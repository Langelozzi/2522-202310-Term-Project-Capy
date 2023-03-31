package ca.bcit.comp2522.termproject.capy;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private Map<SoundEffect, AudioClip> soundEffects;
    private MediaPlayer backgroundMusicPlayer;
//    private HashMap<String, AudioClip> soundEffects;

    public AudioManager() {
        initBackgroundMusic();
        initSoundEffects();
    }

    private void initBackgroundMusic() {
        String musicFile = "/ca/bcit/comp2522/termproject/capy/audio/afterparty-no-party-yet.mp3";
        Media music = new Media(getClass().getResource(musicFile).toExternalForm());
        backgroundMusicPlayer = new MediaPlayer(music);

        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusicPlayer.setVolume(0.5);
    }

    private void initSoundEffects() {
        soundEffects = new HashMap<>();

        soundEffects.put(SoundEffect.GUN_SHOT, new AudioClip(getClass().getResource("/ca/bcit/comp2522/termproject/capy/audio/shoot-sound.mp3").toExternalForm()));
        soundEffects.put(SoundEffect.HIT, new AudioClip(getClass().getResource("/ca/bcit/comp2522/termproject/capy/audio/hit-sound.mp3").toExternalForm()));
        soundEffects.put(SoundEffect.DEATH, new AudioClip(getClass().getResource("/ca/bcit/comp2522/termproject/capy/audio/enemy-bye-bye.mp3").toExternalForm()));
        soundEffects.put(SoundEffect.COLLISION, new AudioClip(getClass().getResource("/ca/bcit/comp2522/termproject/capy/audio/player-hit-sound.mp3").toExternalForm()));
        soundEffects.put(SoundEffect.PICKUP, new AudioClip(getClass().getResource("/ca/bcit/comp2522/termproject/capy/audio/item-pick-up.mp3").toExternalForm()));
    }


    public void playBackgroundMusic() {
        backgroundMusicPlayer.play();
    }

    public void playSoundEffect(SoundEffect soundEffect) {
        AudioClip audioClip = soundEffects.get(soundEffect);
        if (audioClip != null) {
            audioClip.play();
        }
    }


    public enum SoundEffect {
        GUN_SHOT,
        HIT,
        DEATH,
        COLLISION,
        PICKUP
    }

}
