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

public class HighScoreScreen {

	static Scene highScoreScene;
	Button buttonHomeScreen;

	public void highScore(Stage primaryStage){

		buttonHomeScreen = new Button("Back");
		buttonHomeScreen.setMinSize(100, 50);
		buttonHomeScreen.setOnAction(e -> primaryStage.setScene(mainScreen.getScene()));

		VBox highScoreLayout = new VBox(20);
		highScoreLayout.setPadding(new Insets(10,10,10,10));
		highScoreLayout.getChildren().add(buttonHomeScreen);
		highScoreScene = new Scene(highScoreLayout, 1024,768);

	}

	public static Scene getScene(){
		return highScoreScene;
	}
}
