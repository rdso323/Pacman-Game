package PACMAN;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class gamOvr {

	public void gmeOvr(Stage primaryStage){

		singlePlayer sinPlay = new singlePlayer();
		sinPlay.gamePause();

		Stage gameO = new Stage();
		gameO.initModality(Modality.APPLICATION_MODAL);
		gameO.initStyle(StageStyle.UNDECORATED);
		gameO.initStyle(StageStyle.TRANSPARENT);
		gameO.setOpacity(0.5);

		Text GOText = new Text();
		GOText.setText("Game Over");
		GOText.setFont(Font.font("ARIAL", 75));
		GOText.setFill(Color.RED);

		StackPane GOLayout = new StackPane();
		GOLayout.setPadding(new Insets(15,15,15,15));
		GOLayout.setAlignment(Pos.CENTER);
		GOLayout.getChildren().add(GOText);

		Scene gameOve = new Scene(GOLayout,1024,768);


		gameO.setScene(gameOve);
		gameO.show();

		PauseTransition delay = new PauseTransition(Duration.seconds(5));
		delay.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Main main = new Main();
				try {
					main.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				gameO.close();
			}
		});
		delay.play();

	}

}
