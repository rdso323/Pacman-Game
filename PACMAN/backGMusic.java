package PACMAN;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;

public class backGMusic {

	public void bMusic(){

		String musicFile = "Resources/pacman.wav";

		Media sound = new Media(new File(musicFile).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		//mediaPlayer.setStartTime(Duration.seconds(0));
		//mediaPlayer.setStopTime(Duration.seconds(13));
		//MediaView mediaView = new MediaView(mediaPlayer);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();

	}

}
