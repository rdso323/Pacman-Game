package PACMAN.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class nameAlert {

	//alert user if name not entered
	public void alert(){

		//set up new stage
		Stage alert = new Stage();
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle("Name Not Entered");
		alert.setMinHeight(250);
		alert.setMinHeight(50);

		//warning text to be displayed
		Text warning = new Text();
		warning.setText("Name was not entered \nPlease enter a name to continue");

		//set up button for user after understanding warning
		Button buttonok = new Button("OK");
		buttonok.setMinSize(50, 15);
		buttonok.setOnAction(e -> alert.close());

		//layout -> scene -> stage for alert
		VBox alertLayout = new VBox(20);
		alertLayout.setPadding(new Insets(15,15,15,15));
		alertLayout.setAlignment(Pos.CENTER);
		alertLayout.getChildren().add(warning);
		alertLayout.getChildren().add(buttonok);
		Scene alertScene = new Scene(alertLayout);
		alert.setScene(alertScene);
		alert.showAndWait();

	}

}
