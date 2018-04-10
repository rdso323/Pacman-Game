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

	@Override
	public void start(Stage primaryStage){

		primaryStage.setTitle("PAC-MAN Dragon Edition");

		mainScreen mainS = new mainScreen();
		mainS.menu(primaryStage);

		primaryStage.setScene(mainScreen.getScene());
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
