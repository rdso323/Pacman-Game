package PACMAN;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class timer {

	private static final Integer stime = 120;
	private Timeline timeline;
	private Text timelabel = new Text();
	private Integer timeSeconds = stime;

	public void lvlTime(Stage primaryStage){

		timelabel.setText(timeSeconds.toString());
		timelabel.setFont(Font.font("ARIAL", 30));
		timelabel.setFill(Color.WHITE);


		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				timeSeconds--;
				timelabel.setText(timeSeconds.toString());
				System.out.println(timelabel);
				if(timeSeconds <= 0){
					gamOvr gO = new gamOvr();
					gO.gmeOvr(primaryStage);
					timeline.stop();
				}
			}
		}));
		timeline.playFromStart();

		//scene.getChildren.add(timelabel);
	}

	public Text timeRemain(){
		return timelabel;
	}

	public void pauseTimer(){
		timeline.pause();
	}

	public void starttimer(){
		timeline.playFromStart();
	}

	public void resumeTimer(){
		timeline.play();
	}

}
