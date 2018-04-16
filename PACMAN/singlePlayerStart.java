package PACMAN;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.layout.BackgroundImage;

public class singlePlayerStart {

	static Scene singleSScene;
	static String namePlayer;

	public void sinStart(Stage primaryStage) throws Exception{


		countdown cd = new countdown();
		singlePlayer sinPlay = new singlePlayer();
		sinPlay.start(primaryStage);
		singlePlayer.gamePause();

		Label label1 = new Label("Enter your Name:");
		label1.setFont(Font.font("ARIAL", 20));
		label1.setTextFill(Color.WHITE);
		label1.setTranslateX(625);
		label1.setTranslateY(195);
		TextField name = new TextField ();
		name.setPromptText("Name");
		name.setMaxWidth(225);
		name.setTranslateX(625);
		name.setTranslateY(155);

		Button buttonStart = new Button("Start");
		buttonStart.setMinSize(150, 50);
		buttonStart.setTranslateX(665);
		buttonStart.setTranslateY(370);
//		buttonSinglePlayer.setOnAction(e -> primaryStage.setScene(singlePlayer.getScene()));
		buttonStart.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					if(!(name.getText().isEmpty())){
						namePlayer = name.getText();
						primaryStage.setScene(singlePlayer.getScene());
						cd.setCD(primaryStage);
					}else{
						nameAlert alertName = new nameAlert();
						alertName.alert();
					}
				}
		});

		VBox namestart = new VBox(50);
		namestart.getChildren().add(buttonStart);
		namestart.getChildren().add(label1);
		namestart.getChildren().add(name);

		BorderPane singleSLayout = new BorderPane();
		singleSLayout.setPadding(new Insets(10,10,10,10));
		singleSLayout.setCenter(namestart);
		singleSLayout.setStyle("-fx-background-image: url(\"file:Resources/singlePlScreen.png\"); -fx-background-size: 1024,768 ; -fx-background-repeat: no-repeat;");
		singleSScene = new Scene(singleSLayout,1024,768);
	}

	public static Scene getScene(){
		return singleSScene;
	}

	public static String getName(){
		return namePlayer;
	}

}
