package PACMAN;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Imports
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.layout.BackgroundImage;

public class HighScoreScreen {

	// Create global variables
	static Scene highScoreScene;
	Button buttonHomeScreen;

	static int score1,score2,score3,score4,score5,newScore;
	static String player1,player2,player3,player4,player5,newPlayer;

	public void highScore(Stage primaryStage) throws IOException{

		readFile();

		int x = 100;
		int y = 200;
		int sx = 445;
		int sy = -230;
		int textsize = 30;

		// Set values to button and add and event handler
		buttonHomeScreen = new Button("Back");
		buttonHomeScreen.setMinSize(100, 50);
		buttonHomeScreen.setOnAction(e -> primaryStage.setScene(mainScreen.getScene()));

		Label labelplayer1 = new Label(player1);
		labelplayer1.setTextFill(Color.WHITE);
		labelplayer1.setFont(Font.font("ARIAL", textsize));
		labelplayer1.setTranslateX(x);
		labelplayer1.setTranslateY(y);
		Label labelplayer2 = new Label(player2);
		labelplayer2.setTextFill(Color.WHITE);
		labelplayer2.setFont(Font.font("ARIAL", textsize));
		labelplayer2.setTranslateX(x);
		labelplayer2.setTranslateY(y);
		Label labelplayer3 = new Label(player3);
		labelplayer3.setTextFill(Color.WHITE);
		labelplayer3.setFont(Font.font("ARIAL", textsize));
		labelplayer3.setTranslateX(x);
		labelplayer3.setTranslateY(y);
		Label labelplayer4 = new Label(player4);
		labelplayer4.setTextFill(Color.WHITE);
		labelplayer4.setFont(Font.font("ARIAL", textsize));
		labelplayer4.setTranslateX(x);
		labelplayer4.setTranslateY(y);
		Label labelplayer5 = new Label(player5);
		labelplayer5.setTextFill(Color.WHITE);
		labelplayer5.setFont(Font.font("ARIAL", textsize));
		labelplayer5.setTranslateX(x);
		labelplayer5.setTranslateY(y);

		Label labelscore1 = new Label(Integer.toString(score1));
		labelscore1.setTextFill(Color.WHITE);
		labelscore1.setFont(Font.font("ARIAL", textsize));
		labelscore1.setTranslateX(sx);
		labelscore1.setTranslateY(sy);
		Label labelscore2 = new Label(Integer.toString(score2));
		labelscore2.setTextFill(Color.WHITE);
		labelscore2.setFont(Font.font("ARIAL", textsize));
		labelscore2.setTranslateX(sx);
		labelscore2.setTranslateY(sy);
		Label labelscore3 = new Label(Integer.toString(score3));
		labelscore3.setTextFill(Color.WHITE);
		labelscore3.setFont(Font.font("ARIAL", textsize));
		labelscore3.setTranslateX(sx);
		labelscore3.setTranslateY(sy);
		Label labelscore4 = new Label(Integer.toString(score4));
		labelscore4.setTextFill(Color.WHITE);
		labelscore4.setFont(Font.font("ARIAL", textsize));
		labelscore4.setTranslateX(sx);
		labelscore4.setTranslateY(sy);
		Label labelscore5 = new Label(Integer.toString(score5));
		labelscore5.setTextFill(Color.WHITE);
		labelscore5.setFont(Font.font("ARIAL", textsize));
		labelscore5.setTranslateX(sx);
		labelscore5.setTranslateY(sy);

		// Set up layout of the scene
		VBox scores = new VBox(50);
		scores.getChildren().addAll(labelplayer1,labelplayer2,labelplayer3,labelplayer4,labelplayer5);
		scores.getChildren().addAll(labelscore1,labelscore2,labelscore3,labelscore4,labelscore5);

		BorderPane hS = new BorderPane();
		hS.setPadding(new Insets(35,10,10,30));
		hS.setLeft(buttonHomeScreen);
		hS.setCenter(scores);
		hS.setStyle("-fx-background-image: url(\"file:Resources/highScore.png\"); -fx-background-size: 1024,768 ; -fx-background-repeat: no-repeat;");
		highScoreScene = new Scene(hS, 1024,768);

	}

	// Function to return the Scene created for High Scores
	public static Scene getScene(){
		return highScoreScene;
	}

	public static void setScore(){
		readFile();
	}

	public static void checkScores(){

		newPlayer = singlePlayerStart.getName();
		newScore = Integer.parseInt(singlePlayer.getScore());

		if(newScore > score1){
			player5 = player4;
			score5 = score4;
			player4 = player3;
			score4 = score3;
			player3 = player2;
			score3 = score2;
			player2 = player1;
			score2 = score1;
			player1 = newPlayer;
			score1 = newScore;
		}else if(newScore > score2){
			player5 = player4;
			score5 = score4;
			player4 = player3;
			score4 = score3;
			player3 = player2;
			score3 = score2;
			player2 = newPlayer;
			score2 = newScore;
		}else if(newScore > score3){
			player5 = player4;
			score5 = score4;
			player4 = player3;
			score4 = score3;
			player3 = newPlayer;
			score3 = newScore;
		}else if(newScore > score4){
			player5 = player4;
			score5 = score4;
			player4 = newPlayer;
			score4 = newScore;
		}else if(newScore > score5){
			player5 = newPlayer;
			score5 = newScore;
		}

		writeFile();

	}

	public static void readFile(){
		FileReader readFile = null;
		BufferedReader reader = null;
		try {
			readFile = new FileReader("highScore.txt");
			reader = new BufferedReader(readFile);
			String line = "NA:0";
			for(int i = 1; i < 6; i++){

				try {
					line = reader.readLine();
				} catch (IOException e) {
					line = "NA:0";
				}

				String splitted[] = line.split(":");
				String plyr = splitted[0];
				int scre = Integer.parseInt(splitted[1]);
				//String plyr = "no one";
				//int scre = 100;

				if(i == 1){

					player1 = plyr;
					score1 = scre;

				}else if (i == 2){

					player2 = plyr;
					score2 = scre;

				}else if (i == 3){

					player3 = plyr;
					score3 = scre;

				}else if (i == 4){

					player4 = plyr;
					score4 = scre;

				}else if (i == 5){

					player5 = plyr;
					score5 = scre;

				}
			}

		} catch (FileNotFoundException e) {
			player1 = "NA";
			score1 = 0;
			player2 = "NA";
			score2 = 0;
			player3 = "NA";
			score3 = 0;
			player4 = "NA";
			score4 = 0;
			player5 = "NA";
			score5 = 0;
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}catch(NullPointerException e) {
				e.printStackTrace();
			}
		}

		checkScores();

	}

	public static void writeFile(){

		File scores = new File("highScore.txt");
		if(!scores.exists()){
			try {
				scores.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileWriter writetofile = null;
		BufferedWriter writer = null;

		try {
			writetofile = new FileWriter(scores);
			writer = new BufferedWriter(writetofile);
			writer.write( player1 + ":" + score1 + "\n");
			writer.write( player2 + ":" + score2 + "\n");
			writer.write( player3 + ":" + score3 + "\n");
			writer.write( player4 + ":" + score4 + "\n");
			writer.write( player5 + ":" + score5 + "\n");

			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
