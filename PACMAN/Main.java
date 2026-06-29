package PACMAN;

import PACMAN.Model.backGMusic;
import PACMAN.View.mainScreen;
// Imports
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{

		// set title of window(stage)
		primaryStage.setTitle("PAC-MAN Dragon Edition");

		// Initialize the main menu function and pass in the stage
		mainScreen mainS = new mainScreen();
		mainS.menu(primaryStage);

		primaryStage.setScene(mainScreen.getScene());
		primaryStage.setResizable(false);
		primaryStage.show();

		// Start music after the window is visible so audio failures do not block launch.
		backGMusic bgm = new backGMusic();
		bgm.bMusic();
	}
}
