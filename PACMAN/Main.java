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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.*;;

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
	Button buttonHomeScreen,buttonHomeScreen1,buttonHomeScreen2,buttonHomeScreen3; //helper button

	//Image gokuHome = new Image("goku.png");

	@Override
	public void start(Stage primaryStage){

		primaryStage.setTitle("PAC-MAN Dragon Edition");

		buttonSinglePlayer = new Button("Single Player");
		buttonSinglePlayer.setMinSize(150, 50);
		buttonSinglePlayer.setOnAction(e -> primaryStage.setScene(singlePlayerScene));
		buttonMultiPlayer = new Button("Multi-Player");
		buttonMultiPlayer.setMinSize(150, 50);
		buttonMultiPlayer.setOnAction(e -> primaryStage.setScene(multiPlayerScene));
		buttonHelp = new Button("Help");
		buttonHelp.setMinSize(150, 50);
		buttonHelp.setOnAction(e -> primaryStage.setScene(helpScene));
		buttonHighScore = new Button("High Score");
		buttonHighScore.setMinSize(150, 50);
		buttonHighScore.setOnAction(e -> primaryStage.setScene(highScoreScene));

		buttonHomeScreen = new Button("Main menu");
		buttonHomeScreen.setOnAction(e -> primaryStage.setScene(homeScene));
		buttonHomeScreen1 = new Button("Main menu");
		buttonHomeScreen1.setOnAction(e -> primaryStage.setScene(homeScene));
		buttonHomeScreen2 = new Button("Back");
		buttonHomeScreen2.setOnAction(e -> primaryStage.setScene(homeScene));
		buttonHomeScreen3 = new Button("Back");
		buttonHomeScreen3.setOnAction(e -> primaryStage.setScene(homeScene));

		VBox homeLayout1 = new VBox(20);
		homeLayout1.getChildren().add(buttonHighScore);
		HBox homeLayout = new HBox(100);
		homeLayout.getChildren().add(buttonSinglePlayer);
		homeLayout.getChildren().add(buttonMultiPlayer);
		homeLayout.getChildren().add(buttonHelp);
		homeLayout.setAlignment(Pos.CENTER);
		BorderPane menu = new BorderPane();
		menu.setPadding(new Insets(10,10,200,10));
		menu.setLeft(homeLayout1);
		menu.setBottom(homeLayout);
		homeScene = new Scene(menu,1024,768);

		StackPane singlePlayerLayout = new StackPane();
		singlePlayerLayout.setPadding(new Insets(10,10,10,10));
		singlePlayerLayout.getChildren().add(buttonHomeScreen);
		singlePlayerScene = new Scene(singlePlayerLayout, 1024,768);

		StackPane multiPlayerLayout = new StackPane();
		multiPlayerLayout.setPadding(new Insets(10,10,10,10));
		multiPlayerLayout.getChildren().add(buttonHomeScreen1);
		multiPlayerScene = new Scene(multiPlayerLayout, 1024,768);

		VBox helpLayout = new VBox(20);
		helpLayout.setPadding(new Insets(10,10,10,10));
		helpLayout.getChildren().add(buttonHomeScreen2);
		helpScene = new Scene(helpLayout, 1024,768);

		VBox highScoreLayout = new VBox(20);
		highScoreLayout.setPadding(new Insets(10,10,10,10));
		highScoreLayout.getChildren().add(buttonHomeScreen3);
		highScoreScene = new Scene(highScoreLayout, 1024,768);

		primaryStage.setScene(homeScene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
