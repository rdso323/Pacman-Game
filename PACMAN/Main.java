package PACMAN;

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

		// Initialize the music function
		backGMusic bgm = new backGMusic();
		bgm.bMusic();

		primaryStage.setScene(mainScreen.getScene());
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
