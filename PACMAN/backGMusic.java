package PACMAN;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class backGMusic {

	MediaPlayer player;

	public void bMusic(){

		Media bgMusic = new Media("file:Resources/pacman-db.mp3");
		player = new MediaPlayer(bgMusic);
		player.setAutoPlay(true);
		player.setVolume(0.2);

	}

}
