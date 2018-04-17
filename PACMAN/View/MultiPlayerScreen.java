package PACMAN.View;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import PACMAN.Controller.multiPlayer;
import PACMAN.Model.countdown;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.geometry.Insets;

public class MultiPlayerScreen {

	static Scene multiScene;

	//multiplayer story scene setup
	public void mPlay(Stage primaryStage) throws Exception{

		//crete new instances of the countdown class and multiplayer class
		countdown cd = new countdown();
		multiPlayer multiP = new multiPlayer();
		multiP.start(primaryStage);
		multiPlayer.gamePause();

		//set up button to get multiplayer scene and call the multiplayer countdown
		Button buttonStart = new Button("Start");
		buttonStart.setMinSize(150, 50);
		buttonStart.setTranslateX(20);
		buttonStart.setTranslateY(-50);
		buttonStart.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setScene(multiPlayer.getScene());
				cd.setCDMul(primaryStage);
			}
		});

		//set up layout add image and set scene
		BorderPane multiSLayout = new BorderPane();
		multiSLayout.setPadding(new Insets(10,10,10,10));
		multiSLayout.setCenter(buttonStart);
		multiSLayout.setStyle("-fx-background-image: url(\"file:Resources/multiPlScreen.png\"); -fx-background-size: 1024,768 ; -fx-background-repeat: no-repeat;");
		multiScene = new Scene(multiSLayout,1024,768);
	}

	//return the scene to be displayed
	public static Scene getScene(){
		return multiScene;
	}

}
