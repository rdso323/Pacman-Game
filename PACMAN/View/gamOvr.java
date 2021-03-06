package PACMAN.View;

import PACMAN.Main;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class gamOvr {

	//game over display when game is over
	public void gmeOvr(Stage primaryStage){

		//set new stage a set it to be see through
		Stage gameO = new Stage();
		gameO.initStyle(StageStyle.UNDECORATED);
		gameO.initStyle(StageStyle.TRANSPARENT);
		gameO.setOpacity(0.5);

		//set the test for game over
		Text GOText = new Text();
		GOText.setText("Game Over");
		GOText.setFont(Font.font("ARIAL", 75));
		GOText.setFill(Color.RED);

		//make a layout for the scene and add it, set scene and show
		StackPane GOLayout = new StackPane();
		GOLayout.setPadding(new Insets(15,15,15,15));
		GOLayout.setAlignment(Pos.CENTER);
		GOLayout.getChildren().add(GOText);
		Scene gameOve = new Scene(GOLayout,1024,768);
		gameO.setScene(gameOve);
		gameO.show();

		//wait 5 seconds and then go to main menu and close this stage
		PauseTransition delay = new PauseTransition(Duration.seconds(5));
		delay.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Main main = new Main();
				try {
					main.start(primaryStage);
				} catch (Exception e) {
					//e.printStackTrace();
				}
				gameO.close();
			}
		});
		delay.play();//start delay

	}

}
