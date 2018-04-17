package PACMAN.Model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class backGMusic {

	public void bMusic(){

		//set music file make new media player and play
		String musicFile = "Resources/pacman.wav";

		Media sound = new Media(new File(musicFile).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);//go forever
		mediaPlayer.play();

	}

}
