package PACMAN;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.*;
import javafx.scene.layout.BackgroundImage;

public class countdown {

	private static final Integer stime = 3;
	private Timeline timeline;
	private Text timelabel = new Text();
	private Integer timeSeconds = stime;

	public void setCD(Stage primaryStage){

		Stage countD = new Stage();
		//countD.initModality(Modality.APPLICATION_MODAL);
		countD.initStyle(StageStyle.UNDECORATED);
		countD.initStyle(StageStyle.TRANSPARENT);
		countD.setOpacity(0.5);

		timelabel.setText("Ready");
		timelabel.setFont(Font.font("ARIAL", 50));
		timelabel.setFill(Color.RED);
		//timelabel.textProperty().bind(null);
		//timelabel.setText("Set").after(Duration.seconds(1));

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
					CDend(primaryStage);
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

//		PauseTransition delay = new PauseTransition(Duration.seconds(3));
//		delay.setOnFinished(new EventHandler<ActionEvent>(){
//			@Override
//			public void handle(ActionEvent event) {
//				countD.close();
//				CDend();
//			}
//		});
//		delay.play();

	}

	public void CDend(Stage primaryStage){
		singlePlayer sinPlay = new singlePlayer();
		//sinPlay.lvlTime(primaryStage);
		sinPlay.gameResume();
//		timer gtime = new timer();
//		gtime.lvlTime(primaryStage);
//		gtime.starttimer();
	}
}
