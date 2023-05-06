/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author asus
 */
public class MusicController {

    public MusicController() {
    }
    
    private MediaPlayer mediaPlayer;

    public void playMusic(String musicPath) {
        Media music = new Media(new File(musicPath).toURI().toString());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.play();
    }

    public void stopMusic() {
        mediaPlayer.stop();
    }
}
