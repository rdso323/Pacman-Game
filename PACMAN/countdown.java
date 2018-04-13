package PACMAN;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.*;
import javafx.scene.layout.BackgroundImage;

public class countdown {

	static Scene countDown;

	public void setCD(Stage primaryStage){

		StackPane countDownLayout = new StackPane();
		countDownLayout.setPadding(new Insets(10,10,10,10));
		countDownLayout.setOpacity(0.9);
		countDown = new Scene(countDownLayout, 1024,768, Color.BLACK);

	}

	public static Scene getScene(){
		return countDown;
	}
}
