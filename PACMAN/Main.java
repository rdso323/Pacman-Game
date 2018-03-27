package PACMAN;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Main extends Application {

	Scene homeScene;
	Scene helpScene;
	Scene singlePlayerScene;
	Scene multiPlayerScene;
	Scene highScoreScene;

	Button buttonSinglePlayer;
	Button buttonMultiPlayer;
	Button buttonHelp;
	Button buttonHighScore;
	Button buttonHomeScreen; //helper button

	@Override
	public void start(Stage primaryStage){

		primaryStage.setTitle("PAC-MAN Dragon Edition");

		buttonSinglePlayer = new Button("Single Player");
		buttonSinglePlayer.setOnAction(e -> primaryStage.setScene(singlePlayerScene));
		buttonMultiPlayer = new Button("Multi-Player");
		buttonHelp = new Button("Help");
		buttonHighScore = new Button("High Score");

		buttonHomeScreen = new Button("Main menu");
		buttonHomeScreen.setOnAction(e -> primaryStage.setScene(homeScene));

		VBox homeLayout = new VBox(20);
		homeLayout.getChildren().add(buttonSinglePlayer);
		homeLayout.getChildren().add(buttonMultiPlayer);
		homeLayout.getChildren().add(buttonHelp);
		homeLayout.getChildren().add(buttonHighScore);
		homeScene = new Scene(homeLayout,1024,768);

		StackPane singlePlayerLayout = new StackPane();
		singlePlayerLayout.getChildren().add(buttonHomeScreen);
		singlePlayerScene = new Scene(singlePlayerLayout, 1024,768);

		primaryStage.setScene(homeScene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
