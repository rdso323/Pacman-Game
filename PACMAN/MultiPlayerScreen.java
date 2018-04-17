package PACMAN;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.layout.BackgroundImage;

public class MultiPlayerScreen {

	static Scene multiScene;

	public void mPlay(Stage primaryStage) throws Exception{

//		countdown cd = new countdown();
		//singlePlayer sinPlay = new singlePlayer();
		//sinPlay.start(primaryStage);
		//sinPlay.gamePause();
		
		multiPlayer multiP = new multiPlayer();
		multiP.start(primaryStage);
		Button buttonStart = new Button("Start");
		buttonStart.setMinSize(150, 50);
		buttonStart.setTranslateX(250);
		buttonStart.setOnAction(e -> primaryStage.setScene(multiPlayer.getScene()));
//		buttonStart.setOnAction(new EventHandler<ActionEvent>(){
//			@Override
//			public void handle(ActionEvent arg0) {
//				primaryStage.setScene(singlePlayer.getScene());
//				cd.setCD(primaryStage);
//
//			}

//		});



		BorderPane multiSLayout = new BorderPane();
		multiSLayout.setPadding(new Insets(10,10,10,10));
		multiSLayout.setCenter(buttonStart);
		multiSLayout.setStyle("-fx-background-image: url(\"file:Resources/multiPlScreen.png\"); -fx-background-size: 1024,768 ; -fx-background-repeat: no-repeat;");
		multiScene = new Scene(multiSLayout,1024,768);
	}

	public static Scene getScene(){
		return multiScene;
	}

}
