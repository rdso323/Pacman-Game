package PACMAN;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class nameAlert {

	public void alert(){
		Stage alert = new Stage();
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle("Name Not Entered");
		alert.setMinHeight(250);
		alert.setMinHeight(50);

		Text warning = new Text();
		warning.setText("Name was not entered \nPlease enter a name to continue");

		Button buttonok = new Button("OK");
		buttonok.setMinSize(50, 15);
		buttonok.setOnAction(e -> alert.close());

		VBox alertLayout = new VBox(20);
		alertLayout.setPadding(new Insets(15,15,15,15));
		//alertLayout.setBackground(FFFFFF);
		alertLayout.setAlignment(Pos.CENTER);
		alertLayout.getChildren().add(warning);
		alertLayout.getChildren().add(buttonok);

		Scene alertScene = new Scene(alertLayout);
		alert.setScene(alertScene);
		alert.showAndWait();

	}

}
