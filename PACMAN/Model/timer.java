package PACMAN.Model;

import PACMAN.View.gamOvr;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

public class timer {

	private static final Integer stime = 120;
	private Timeline timeline;
	private Integer timeSeconds = stime;

	//2 minute timer for the game
	public void lvlTime(Stage primaryStage){

		//set up timeline to repeat
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		//set the timeline to trigger every second
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				timeSeconds--;
				if(timeSeconds <= 0){
					gamOvr gO = new gamOvr();
					gO.gmeOvr(primaryStage);
					timeline.stop();
				}
			}
		}));
		timeline.playFromStart();//start timeline
	}

	//return remaining time
	public Integer timeRemain(){
		return timeSeconds;
	}

	//pause the timer
	public void pauseTimer(){
		timeline.pause();
	}

	//start timer from the start
	public void starttimer(){
		timeline.playFromStart();
	}

	//resume the time
	public void resumeTimer(){
		timeline.play();
	}

}
