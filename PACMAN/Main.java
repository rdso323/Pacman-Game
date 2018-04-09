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
	Button buttonPause,buttonHomeScreen1,buttonHomeScreen2,buttonHomeScreen3; //helper button

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

		buttonPause = new Button("Pause");
		buttonPause.setOnAction(e -> PauseMenu.pause());
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
		menu.setPadding(new Insets(10,10,160,10));
		menu.setLeft(homeLayout1);
		menu.setBottom(homeLayout);
		menu.setStyle("-fx-background-image: url(\"file:homescreen.png\"); -fx-background-size: 1024,768 ; -fx-background-repeat: no-repeat;");
		homeScene = new Scene(menu,1024,768);

		StackPane singlePlayerLayout = new StackPane();
		singlePlayerLayout.setPadding(new Insets(10,10,10,10));
		singlePlayerLayout.getChildren().add(buttonPause);
		singlePlayerScene = new Scene(singlePlayerLayout, 1024,768);

		StackPane multiPlayerLayout = new StackPane();
		multiPlayerLayout.setPadding(new Insets(10,10,10,10));
		multiPlayerLayout.getChildren().add(buttonHomeScreen1);
		multiPlayerScene = new Scene(multiPlayerLayout, 1024,768);


//		Text storyLine = new Text ("Story-Line: \nTribute to the series Dragon Ball Super. \n"
//				+ "Our main protagonist Goku has fought several battles throughout \n"
//				+ "his time, including matchups with Jiren, Hit, Kefla and Toppo. \n"
//				+ "However, in every battle he’s always required a full belly to operate.\n"
//				+ "\n"
//				+ "You need to traverse throughout the levels eating as much Ramen as possible,\n"
//				+ "whilst avoiding his enemies in this weakened state. There are collectibles \n"
//				+ "(Dragon Balls) scattered throughout the level which may assist you \n"
//				+ "(Not always the case). Can you make it through all the levels successfully? \n\n");
//		storyLine.setFont(Font.font("Verdana",20));

		Image helpIM = new Image("file:helpdcreen.png");
		ImageView helpV = new ImageView(helpIM);
		helpV.fitWidthProperty().bind(primaryStage.widthProperty());
		helpV.fitHeightProperty().bind(primaryStage.heightProperty());


		VBox helpLayout = new VBox(20);
		//helpLayout.setPadding(new Insets(10,10,10,10));
		helpLayout.getChildren().add(buttonHomeScreen2);
		//helpLayout.getChildren().add(storyLine);
		//helpLayout.getChildren().add(helpV);
		BorderPane helpM = new BorderPane();
		helpM.setPadding(new Insets(10,10,10,10));
		helpM.setLeft(helpLayout);
		helpM.setStyle("-fx-background-image: url(\"file:helpdcreen.png\"); -fx-background-size: 1024,768 ; -fx-background-repeat: no-repeat;");
		//helpM.setBackground(000000);
		helpScene = new Scene(helpM, 1024,768);

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
