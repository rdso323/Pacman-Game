package PACMAN;

import javafx.scene.*;
import javafx.stage.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PauseMenu {

	public static void pause(Stage primaryStage){

		//singlePlayer sinPlay = new singlePlayer();
		singlePlayer.gamePause();
//		timer gtime = new timer();
//		gtime.pauseTimer();

		Stage pauseMenu = new Stage();
		pauseMenu.initModality(Modality.APPLICATION_MODAL);
		pauseMenu.initStyle(StageStyle.UNDECORATED);
		//pauseMenu.setOpacity(0.9);

		pauseMenu.setTitle("Pause Menu");
		pauseMenu.setMinWidth(250);
		pauseMenu.setMinHeight(70);

		Button buttonResume = new Button("Resume");
		buttonResume.setMinSize(100, 40);
		//buttonResume.setOnAction(e -> pauseMenu.close());
		buttonResume.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				pauseMenu.close();
				singlePlayer.gameResume();
				//gtime.resumeTimer();
			}
		});
		buttonResume.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == (KeyCode.P)){
					pauseMenu.close();
					singlePlayer.gameResume();
					//gtime.resumeTimer();
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
					e.printStackTrace();
				}
				pauseMenu.close();
			}
		});
		//setOnAction(e -> primaryStage.setScene(mainScreen.getScene()));

		HBox pauseLayout = new HBox(20);
		pauseLayout.setPadding(new Insets(15,15,15,15));
		pauseLayout.setAlignment(Pos.CENTER);
		pauseLayout.getChildren().addAll(buttonResume,buttonExit);

		Scene paused = new Scene(pauseLayout);
		//paused.setFill(Color.BLACK);

		pauseMenu.setScene(paused);
		pauseMenu.showAndWait();
	}

}
