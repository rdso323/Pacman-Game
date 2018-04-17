package PACMAN;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;

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
	
//	MediaPlayer mediaPlayer;
//	public void bMusic(){
//	    String bip = "file:Resources/pacman-db.mp3";
//	    Media hit = new Media("file:Resources/pacman.wav");
//	    mediaPlayer = new MediaPlayer(hit);
//	    mediaPlayer.play();
//	}

}
