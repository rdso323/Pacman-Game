package PACMAN.Model;

import PACMAN.Controller.multiPlayer;
import PACMAN.Controller.singlePlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;

public class countdown {

	//Initialize required variable
	private static final Integer stime = 3;
	private Timeline timeline;
	private Text timelabel = new Text();
	private Integer timeSeconds = stime;

	//3 second countdown for single player when starting game
	public void setCD(Stage primaryStage){

		//set stage as see through and undecorated
		Stage countD = new Stage();
		countD.initStyle(StageStyle.UNDECORATED);
		countD.initStyle(StageStyle.TRANSPARENT);
		countD.setOpacity(0.5);

		//set text for stage that will be changed as the counter progresses
		timelabel.setText("Ready");
		timelabel.setFont(Font.font("ARIAL", 50));
		timelabel.setFill(Color.RED);

		//timeline to countdown and set it to keep going
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){//every second run handle
			public void handle(ActionEvent event) {
				timeSeconds--;
				if(timeSeconds == 2){
					timelabel.setText("Set");
				}else if(timeSeconds == 1){
					timelabel.setText("Go!");
				}else if(timeSeconds <= 0){
					timeline.stop(); //stop timeline
					countD.close(); //close stage
					CDend(primaryStage); //call CDend function
				}
			}
		}));
		timeline.playFromStart(); // start timeline when its setup

		//set the layout of the stage
		StackPane cDLayout = new StackPane();
		cDLayout.setPadding(new Insets(15,15,15,15));
		cDLayout.setAlignment(Pos.CENTER);
		cDLayout.getChildren().add(timelabel);

		//set scene to stage and display
		Scene paused = new Scene(cDLayout,1024,768);
		countD.setScene(paused);
		countD.show();

	}

	//resume singele player game when countdown ends
	public void CDend(Stage primaryStage){
		singlePlayer.resettimer();
		singlePlayer.gameResume();
	}

	//3 second countdown for multiplayer
	public void setCDMul(Stage primaryStage){

		Stage countD = new Stage();
		countD.initStyle(StageStyle.UNDECORATED);
		countD.initStyle(StageStyle.TRANSPARENT);
		countD.setOpacity(0.5);

		timelabel.setText("Ready");
		timelabel.setFont(Font.font("ARIAL", 50));
		timelabel.setFill(Color.RED);

		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				timeSeconds--;
				if(timeSeconds == 2){
					timelabel.setText("Set");
				}else if(timeSeconds == 1){
					timelabel.setText("Go!");
				}else if(timeSeconds <= 0){
					timeline.stop();
					countD.close();
					CDendMul(primaryStage);
				}
			}
		}));
		timeline.playFromStart();


		StackPane cDLayout = new StackPane();
		cDLayout.setPadding(new Insets(15,15,15,15));
		cDLayout.setAlignment(Pos.CENTER);
		cDLayout.getChildren().add(timelabel);

		Scene paused = new Scene(cDLayout,1024,768);


		countD.setScene(paused);
		countD.show();

	}

	//resume game for multi player
	public void CDendMul(Stage primaryStage){
		multiPlayer.resettimer();
		multiPlayer.gameResume();
	}
}
