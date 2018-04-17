package PACMAN.Model;

import javafx.scene.*;
import javafx.stage.*;
import PACMAN.Main;
import PACMAN.Controller.multiPlayer;
import PACMAN.Controller.singlePlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PauseMenu {

	//pause menu for single player
	public static void pauseSin(Stage primaryStage){

		//set up new stage
		Stage pauseMenu = new Stage();
		pauseMenu.initModality(Modality.APPLICATION_MODAL);// no interaction with other stage
		pauseMenu.initStyle(StageStyle.UNDECORATED);

		//set up button and to resume game on click and key pressed
		pauseMenu.setTitle("Pause Menu");
		pauseMenu.setMinWidth(250);
		pauseMenu.setMinHeight(70);
		Button buttonResume = new Button("Resume");
		buttonResume.setMinSize(100, 40);
		buttonResume.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				pauseMenu.close();
				singlePlayer.gameResume();
			}
		});
		buttonResume.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == (KeyCode.P)){
					pauseMenu.close();
					singlePlayer.gameResume();
				}
			}
		});

		//set up exit game button to go to main menu if clicked
		Button buttonExit = new Button("exit");
		buttonExit.setMinSize(100, 40);
		buttonExit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Main main = new Main();
				try {
					main.start(primaryStage);
				} catch (Exception e) {
					//e.printStackTrace();
				}
				pauseMenu.close();
			}
		});

		//layout of the menu add to scene and display scene on stage
		HBox pauseLayout = new HBox(20);
		pauseLayout.setPadding(new Insets(15,15,15,15));
		pauseLayout.setAlignment(Pos.CENTER);
		pauseLayout.getChildren().addAll(buttonResume,buttonExit);
		Scene paused = new Scene(pauseLayout);
		pauseMenu.setScene(paused);
		pauseMenu.showAndWait();
	}

	//pause menu for multiplayer
	public static void pauseMul(Stage primaryStage){

		Stage pauseMenu = new Stage();
		pauseMenu.initModality(Modality.APPLICATION_MODAL);
		pauseMenu.initStyle(StageStyle.UNDECORATED);

		pauseMenu.setTitle("Pause Menu");
		pauseMenu.setMinWidth(250);
		pauseMenu.setMinHeight(70);

		Button buttonResume = new Button("Resume");
		buttonResume.setMinSize(100, 40);
		buttonResume.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				pauseMenu.close();
				multiPlayer.gameResume();
			}
		});
		buttonResume.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == (KeyCode.P)){
					pauseMenu.close();
					multiPlayer.gameResume();
				}
			}
		});


		Button buttonExit = new Button("exit");
		buttonExit.setMinSize(100, 40);
		buttonExit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Main main = new Main();
				try {
					main.start(primaryStage);
				} catch (Exception e) {
					//e.printStackTrace();
				}
				pauseMenu.close();
			}
		});

		HBox pauseLayout = new HBox(20);
		pauseLayout.setPadding(new Insets(15,15,15,15));
		pauseLayout.setAlignment(Pos.CENTER);
		pauseLayout.getChildren().addAll(buttonResume,buttonExit);
		Scene paused = new Scene(pauseLayout);
		pauseMenu.setScene(paused);
		pauseMenu.showAndWait();
	}

}
