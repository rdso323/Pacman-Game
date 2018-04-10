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


public class HelpScreen {

	static Scene helpScene;
	Button buttonHomeScreen;

	public void hmenu(Stage primaryStage){

		buttonHomeScreen = new Button("Back");
		buttonHomeScreen.setMinSize(100, 50);
		buttonHomeScreen.setOnAction(e -> primaryStage.setScene(mainScreen.getScene()));

		VBox helpLayout = new VBox(20);
		helpLayout.getChildren().add(buttonHomeScreen);
		BorderPane helpM = new BorderPane();
		helpM.setPadding(new Insets(10,10,10,10));
		helpM.setLeft(helpLayout);
		helpM.setStyle("-fx-background-image: url(\"file:Resources/helpdcreen.png\"); -fx-background-size: 1024,768 ; -fx-background-repeat: no-repeat;");
		helpScene = new Scene(helpM, 1024,768);
	}

	public static Scene getScene(){
		return helpScene;
	}
}
