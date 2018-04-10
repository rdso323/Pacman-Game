package PACMAN;

import javafx.scene.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class PauseMenu {

	public static void pause(Stage primaryStage){

		Stage pauseMenu = new Stage();
		pauseMenu.initModality(Modality.APPLICATION_MODAL);

		pauseMenu.setTitle("Pause Menu");
		pauseMenu.setMinWidth(250);
		pauseMenu.setMinHeight(70);

		Button buttonResume = new Button("Resume");
		buttonResume.setMinSize(100, 40);
		buttonResume.setOnAction(e -> pauseMenu.close());


		Button buttonExit = new Button("exit");
		buttonExit.setMinSize(100, 40);
		buttonExit.setOnAction(e -> primaryStage.setScene(mainScreen.getScene()));

		HBox pauseLayout = new HBox(20);
		pauseLayout.setPadding(new Insets(15,15,15,15));
		pauseLayout.setAlignment(Pos.CENTER);
		pauseLayout.getChildren().addAll(buttonResume,buttonExit);

		Scene paused = new Scene(pauseLayout);

		pauseMenu.setScene(paused);
		pauseMenu.showAndWait();
	}

}
