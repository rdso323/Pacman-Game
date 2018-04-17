package PACMAN.View;

// Imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;


public class HelpScreen {

	// set up global variables
	static Scene helpScene;
	Button buttonHomeScreen;

	public void hmenu(Stage primaryStage){

		// Set values to button and add and event handler
		buttonHomeScreen = new Button("Back");
		buttonHomeScreen.setMinSize(100, 50);
		buttonHomeScreen.setOnAction(e -> primaryStage.setScene(mainScreen.getScene()));

		// Set up layout of the scene and add button
		VBox helpLayout = new VBox(20);
		helpLayout.getChildren().add(buttonHomeScreen);

		// Set up layout of the scene
		BorderPane helpM = new BorderPane();
		helpM.setPadding(new Insets(10,10,10,10));
		helpM.setLeft(helpLayout);
		// add image to layout
		helpM.setStyle("-fx-background-image: url(\"file:Resources/helpdcreen.png\"); -fx-background-size: 1024,768 ; -fx-background-repeat: no-repeat;");
		helpScene = new Scene(helpM, 1024,768);
	}

	// Function to return the Scene created for Help
	public static Scene getScene(){
		return helpScene;
	}
}
