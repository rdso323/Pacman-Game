package PACMAN.Model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class backGMusic {

	public void bMusic(){
		try {
			//set music file make new media player and play
			String musicFile = "Resources/pacman.wav";
			File file = new File(musicFile);
			if (!file.exists()) {
				System.err.println("Background music file not found: " + file.getAbsolutePath());
				return;
			}

			Media sound = new Media(file.toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);//go forever
			mediaPlayer.play();
		} catch (Exception e) {
			// Keep the game running if audio fails in packaged builds.
			System.err.println("Background music could not be started: " + e.getMessage());
		}
	}

}
