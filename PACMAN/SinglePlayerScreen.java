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

public class SinglePlayerScreen {

	static Scene singlePlayerScene;
	Button buttonPause;

	public void sPlay(Stage primaryStage){

		buttonPause = new Button("Pause");
		buttonPause.setOnAction(e -> PauseMenu.pause(primaryStage));

		StackPane singlePlayerLayout = new StackPane();
		singlePlayerLayout.setPadding(new Insets(10,10,10,10));
		singlePlayerLayout.getChildren().add(buttonPause);
		singlePlayerScene = new Scene(singlePlayerLayout, 1024,768);
	}

	public static Scene getScene(){
		return singlePlayerScene;
	}
}
