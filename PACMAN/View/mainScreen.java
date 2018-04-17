package PACMAN.View;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import PACMAN.Model.HighScoreScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class mainScreen {

	static Scene homeScene;

	Button buttonSinglePlayer;
	Button buttonMultiPlayer;
	Button buttonHelp;
	Button buttonHighScore;

	//set up the main screen menu
	public void menu(Stage primaryStage) throws Exception{

		//for each button run the scene setup function and on the action event get the scene from that class
		//set the scene to the stage, size of the button and text for them

		singlePlayerStart sStart = new singlePlayerStart();
		sStart.sinStart(primaryStage);
		buttonSinglePlayer = new Button("Single Player");
		buttonSinglePlayer.setMinSize(150, 50);
		buttonSinglePlayer.setOnAction(e -> primaryStage.setScene(singlePlayerStart.getScene()));

		MultiPlayerScreen multiPlay = new MultiPlayerScreen();
		multiPlay.mPlay(primaryStage);
		buttonMultiPlayer = new Button("Multi-Player");
		buttonMultiPlayer.setMinSize(150, 50);
		buttonMultiPlayer.setOnAction(e -> primaryStage.setScene(MultiPlayerScreen.getScene()));

		HelpScreen helpS = new HelpScreen();
		helpS.hmenu(primaryStage);
		buttonHelp = new Button("Help");
		buttonHelp.setMinSize(150, 50);
		buttonHelp.setOnAction(e -> primaryStage.setScene(HelpScreen.getScene()));

		HighScoreScreen hiScore = new HighScoreScreen();
		hiScore.highScore(primaryStage);
		buttonHighScore = new Button("High Score");
		buttonHighScore.setMinSize(150, 50);
		buttonHighScore.setOnAction(e -> primaryStage.setScene(HighScoreScreen.getScene()));

		//layout for a button
		VBox homeLayout1 = new VBox(20);
		homeLayout1.getChildren().add(buttonHighScore);

		//layout for the buttons
		HBox homeLayout = new HBox(100);
		homeLayout.getChildren().add(buttonSinglePlayer);
		homeLayout.getChildren().add(buttonMultiPlayer);
		homeLayout.getChildren().add(buttonHelp);
		homeLayout.setAlignment(Pos.CENTER);

		//add other layouts to borderpane and add image to background set it to the scene
		BorderPane menu = new BorderPane();
		menu.setPadding(new Insets(10,10,160,10));
		menu.setLeft(homeLayout1);
		menu.setBottom(homeLayout);
		menu.setStyle("-fx-background-image: url(\"file:Resources/homescreen.png\"); -fx-background-size: 1024,768 ; -fx-background-repeat: no-repeat;");
		homeScene = new Scene(menu,1024,768);
	}

	//returns the scene
	public static Scene getScene(){
		return homeScene;
	}

}
