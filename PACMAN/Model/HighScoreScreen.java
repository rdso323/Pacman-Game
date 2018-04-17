package PACMAN.Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import PACMAN.Controller.singlePlayer;
import PACMAN.View.mainScreen;
import PACMAN.View.singlePlayerStart;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.text.*;

public class HighScoreScreen {

	// Create global variables
	static Scene highScoreScene;
	Button buttonHomeScreen;

	static int score1,score2,score3,score4,score5,newScore;
	static String player1,player2,player3,player4,player5,newPlayer;

	public void highScore(Stage primaryStage) throws IOException{

		readFile();//read file function

		//positions for the labels
		int x = 100;
		int y = 200;
		int sx = 445;
		int sy = -230;
		int textsize = 30;

		// Set values to button and add and event handler
		buttonHomeScreen = new Button("Back");
		buttonHomeScreen.setMinSize(100, 50);
		buttonHomeScreen.setOnAction(e -> primaryStage.setScene(mainScreen.getScene()));

		//set up the labels for names and score
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

		// Set up layout of the scene add players and scores
		VBox scores = new VBox(50);
		scores.getChildren().addAll(labelplayer1,labelplayer2,labelplayer3,labelplayer4,labelplayer5);
		scores.getChildren().addAll(labelscore1,labelscore2,labelscore3,labelscore4,labelscore5);

		//set up new layoout with the other layout and add image buttong and set scene
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

	//set Score
	public static void setScore(){
		readFile();
	}

	//check scores with the previous scores and replace if applicable
	public static void checkScores(){

		//get the new players information
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

		writeFile();//write file function

	}

	//read from file and store values
	public static void readFile(){

		FileReader readFile = null; //initailise
		BufferedReader reader = null;

		//read from file if possible
		try {
			readFile = new FileReader("highScore.txt");
			reader = new BufferedReader(readFile);
			String line = "N/A:0";
			for(int i = 1; i < 6; i++){

				//read line if possible if not then set the line value
				try {
					line = reader.readLine();
				} catch (IOException e) {
					line = "N/A:0";
				}

				//split the read string into 2, player and score
				String splitted[] = line.split(":");
				String plyr = splitted[0];
				int scre = Integer.parseInt(splitted[1]);

				//assigne the values read to variables
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

		} catch (FileNotFoundException e) { //if file not found set the variables
			player1 = "N/A";
			score1 = 0;
			player2 = "N/A";
			score2 = 0;
			player3 = "N/A";
			score3 = 0;
			player4 = "N/A";
			score4 = 0;
			player5 = "N/A";
			score5 = 0;
		} finally{//close file if possible
			try {
				reader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}catch(NullPointerException e) {
				//e.printStackTrace();
			}
		}

		checkScores();//call check scores

	}

	//write the scores to file so they are saved
	public static void writeFile(){

		File scores = new File("highScore.txt");

		if(!scores.exists()){
			try {
				scores.createNewFile();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}

		FileWriter writetofile = null;
		BufferedWriter writer = null;

		try { //write to file
			writetofile = new FileWriter(scores);
			writer = new BufferedWriter(writetofile);
			writer.write( player1 + ":" + score1 + "\n");
			writer.write( player2 + ":" + score2 + "\n");
			writer.write( player3 + ":" + score3 + "\n");
			writer.write( player4 + ":" + score4 + "\n");
			writer.write( player5 + ":" + score5 + "\n");

			writer.close();//close file

		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
}
