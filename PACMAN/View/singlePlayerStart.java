package PACMAN.View;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import PACMAN.Controller.singlePlayer;
import PACMAN.Model.countdown;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.text.*;

public class singlePlayerStart {

	static Scene singleSScene;
	static String namePlayer;

	//single player story setup and get player name
	public void sinStart(Stage primaryStage) throws Exception{

		//create new instances of the classes
		countdown cd = new countdown();
		singlePlayer sinPlay = new singlePlayer();
		sinPlay.start(primaryStage);
		singlePlayer.gamePause();

		//test box and label for getting player name
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

		//set up button to only work when something is entered in the text box
		//if nothing is entered then alert the user with warning
		Button buttonStart = new Button("Start");
		buttonStart.setMinSize(150, 50);
		buttonStart.setTranslateX(665);
		buttonStart.setTranslateY(370);
		buttonStart.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					if(!(name.getText().isEmpty())){
						namePlayer = name.getText(); //store users name
						primaryStage.setScene(singlePlayer.getScene());
						cd.setCD(primaryStage);
					}else{
						nameAlert alertName = new nameAlert(); // alert user
						alertName.alert();
					}
				}
		});

		//set up layout for the button and text box
		VBox namestart = new VBox(50);
		namestart.getChildren().add(buttonStart);
		namestart.getChildren().add(label1);
		namestart.getChildren().add(name);

		//set up the rest of the layout and add image and set scene
		BorderPane singleSLayout = new BorderPane();
		singleSLayout.setPadding(new Insets(10,10,10,10));
		singleSLayout.setCenter(namestart);
		singleSLayout.setStyle("-fx-background-image: url(\"file:Resources/singlePlScreen.png\"); -fx-background-size: 1024,768 ; -fx-background-repeat: no-repeat;");
		singleSScene = new Scene(singleSLayout,1024,768);
	}

	//return the scene
	public static Scene getScene(){
		return singleSScene;
	}

	//return the players name that was entered
	public static String getName(){
		return namePlayer;
	}

}
