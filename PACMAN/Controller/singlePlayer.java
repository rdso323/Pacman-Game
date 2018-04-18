package PACMAN.Controller;

import java.io.File;
//import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;


import PACMAN.Model.HighScoreScreen;
import PACMAN.Model.PauseMenu;
import PACMAN.View.gamOvr;
import PACMAN.View.uWon;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import javafx.util.Duration;

public class singlePlayer extends Application {

	private static final double W = 1024, H = 768;


	static Scene scene;
	private Image GokuImage, JirenImage, KeflaImage, ToppoImage, HitImage, BeanImage;
	private ImageView Bean_1,Bean_2;

	private Node  Goku, Jiren, Kefla, Toppo, Hit;
	private Text Score_Text,Time_Text,Lives_Text;


	private int Score_Pellets = 0, Score_Enemies = 0, Score_Total = 0;		//Used to keep track of score
	private int PowerUp = 0;			
	private String GokuImageString;
	private int Toppo_Secondary_Count = 0;					//Count to change direction
	private int Toppo_Direction = 0, Hit_Direction = 0;
	private int Hit_Count = 0, Collectable_Count = 0, PowerUp_Countdown = 480;


	private static Text TimeValue;

	private static Text ScoreT;
	private static Integer min = 2;
	private static Integer sec = 0;
	public Integer minutesR,secondsR;
	private static final Integer stime = 121;

	private final Integer lifetimer = 120;
	private Integer Seconds = lifetimer;
	private Timeline lifetimeline;


	private static Timeline timeline;
	private static Integer timeSeconds = stime;

	Group map = new Group();


	private ArrayList<Rectangle> wall = new ArrayList<Rectangle>();
	private ArrayList<ImageView> pellet = new ArrayList<ImageView>();
	private ArrayList<ImageView> dball = new ArrayList<ImageView>();


	boolean North, South, East, West;

	Button buttonPause;
	static AnimationTimer Movement;

	@Override
	public void start(Stage stage) throws Exception {
		GokuImage = new Image("file:Resources/Goku.png",22,22,false,false);
		JirenImage = new Image("file:Resources/Jiren.png",22,22,false,false);
		KeflaImage = new Image("file:Resources/Kefla.png",22,22,false,false);
		ToppoImage = new Image("file:Resources/Toppo.png",22,22,false,false);
		HitImage = new Image("file:Resources/Hit.png",22,22,false,false);
		BeanImage = new Image("file:Resources/Bean.jpg",30,30,true,true);
		GokuImageString = "Goku";  						//Used to change image when collectible is collected

		Goku = new ImageView(GokuImage);
		Jiren = new ImageView(JirenImage);
		Kefla = new ImageView(KeflaImage);
		Toppo = new ImageView(ToppoImage);
		Hit = new ImageView(HitImage);
		Bean_1 = new ImageView(BeanImage);
		Bean_2 = new ImageView(BeanImage);

		Score_Text = new Text(900, 192, "Score:");
		Score_Text.setFont(Font.font("ARIAL", 30));
		Score_Text.setFill(Color.WHITE);
		Score_Text.setUnderline(true);

		ScoreT = new Text(930,240,Integer.toString(Score_Total));		//Actual score
		ScoreT.setFont(Font.font("ARIAL", 30));
		ScoreT.setFill(Color.WHITE);

		Time_Text = new Text(900, 384, "Time:");
		Time_Text .setFont(Font.font("ARIAL", 30));
		Time_Text .setFill(Color.WHITE);
		Time_Text .setUnderline(true);

		TimeValue = new Text(910,430,"2:00");
		TimeValue.setFont(Font.font("ARIAL", 30));
		TimeValue.setFill(Color.WHITE);
		lvlTime(stage);
		lifeTime();

		Lives_Text = new Text(900, 576, "Lives:");
		Lives_Text.setFont(Font.font("ARIAL", 30));
		Lives_Text.setFill(Color.WHITE);
		Lives_Text.setUnderline(true);

		buttonPause = new Button("Pause");
		buttonPause.setMinSize(100, 50);
		buttonPause.setTranslateX(900);
		buttonPause.setTranslateY(25);
		buttonPause.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				gamePause();
				PauseMenu.pauseSin(stage);
			}

		});
		buttonPause.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == (KeyCode.P)){					//If 'P' pressed, pause the game
					gamePause();
					PauseMenu.pauseSin(stage);
				}else if(event.getCode() == (KeyCode.ESCAPE)){			//If 'Escape' pressed, bring up exit screen
					gamePause();
					PauseMenu.pauseSin(stage);
				}else if (event.getCode() == (KeyCode.PAGE_DOWN)){		//Set timer to 1s
					timeSeconds = 1;
				}
			}
		});


		scene = new Scene(map, W, H, Color.BLACK);
		Maze();											//Set the map

		map.getChildren().add(Jiren);
		map.getChildren().add(Kefla);
		map.getChildren().add(Toppo);
		map.getChildren().add(Hit);
		map.getChildren().add(Goku);
		map.getChildren().add(Score_Text);
		map.getChildren().add(Time_Text);
		map.getChildren().add(Lives_Text);
		map.getChildren().add(Bean_1);
		map.getChildren().add(ScoreT);
		map.getChildren().add(buttonPause);
		map.getChildren().add(TimeValue);


		Jiren.relocate(200,197);						//Set default positions
		Kefla.relocate(640,453);
		Toppo.relocate(421,295);
		Hit.relocate(421,390);
		Bean_1.relocate(925,600);

		//Key press action
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.UP) {									//If 'UP" pressed, we want to go North
					North = true; East = false; South = false; West = false;
				}
				if(event.getCode() == KeyCode.LEFT) {								//If 'LEFT" pressed, we want to go North
					West = true; North = false; South = false; East = false;
				}
				if(event.getCode() == KeyCode.DOWN) {								//If 'DOWN" pressed, we want to go North
					South = true; East = false; North = false; West = false;
				}
				if(event.getCode() == KeyCode.RIGHT) {								//If 'RIGHT" pressed, we want to go North
					East = true; North = false; South = false; West = false;
				}
			}
		});


		//Speed for movement
		Movement = new AnimationTimer() {
			@Override
			public void handle(long now) {
				double dx = 0, dy = 0;
				if(PowerUp == 1) {					//If PowerUp is active
					if (North){
						dy -= 2.5;					//Speed of movement
					}
					else if (East){
						dx += 2.5;
					}

					else if (South){
						dy += 2.5;
					}
					else if (West){
						dx -= 2.5;
					}
				}
				else {								//If PowerUp is inactive
					if (North){
						dy -= 2;
					}
					else if (East){
						dx += 2;
					}

					else if (South){
						dy += 2;
					}
					else if (West){
						dx -= 2;
					}
				}

				moveGokuBy(dx, dy); //initialize functions
				moveJiren();
				moveKefla();
				moveToppo();
				moveHit();
				LifeChange(stage);
				GameWin(stage);
			}
		};
		Movement.start();
	}

	//Move user
	private void moveGokuBy(double dx, double dy) {									//Take in desired movement directions
		if (dx == 0 && dy == 0) return;

		final double Goku_Width = Goku.getBoundsInLocal().getWidth()  / 2;			//Mid point
		final double Goku_Height = Goku.getBoundsInLocal().getHeight() / 2;

		double x = Goku_Width + Goku.getLayoutX() + dx;								//Desired center position
		double y = Goku_Height+ Goku.getLayoutY() + dy;
		moveGokuTo(x, y);															//Moves mid point to this point
	}

	private void moveGokuTo(double x, double y) {
		final double Goku_Width = Goku.getBoundsInLocal().getWidth() / 2;
		final double Goku_Height = Goku.getBoundsInLocal().getHeight() / 2;


		if(Goku.getLayoutY()>=H) {									//Used for moving between top and bottom
			Goku.relocate(Goku.getLayoutX(),(-2*Goku_Height)+0.1);	//openings
		}
		else if(Goku.getLayoutY()+(2*Goku_Height)<=0) {
			Goku.relocate(Goku.getLayoutX(),H-0.1);
		}
		else if(Collision_Goku(x,y) == false) {
			Goku.relocate(x - Goku_Width , y - Goku_Height);
		}

		Pellets();
		Collectibles();
	}

	//Move AI
	private void moveJiren() {

		double px = 0;
		double py = 0;

		final double Jiren_Width = Jiren.getBoundsInLocal().getWidth() / 2;					//Jiren mid-point
		final double Jiren_Height = Jiren.getBoundsInLocal().getHeight() / 2;

		double DistanceX = Goku.getLayoutX() + Goku.getBoundsInLocal().getWidth() / 2 -		//User X Distance away from AI
				(Jiren.getLayoutX() + Jiren_Width);

		double DistanceY = Goku.getLayoutY() + Goku.getBoundsInLocal().getHeight() / 2 -	//User Y Distance away from AI
				(Jiren.getLayoutY() + Jiren_Height);


		if(PowerUp == 0) {								//If PowerUp is Inactive
			if(DistanceX > 0) {
				px = 1.6;
			}
			else if(DistanceX < 0){
				px = -1.6;
			}

			if(DistanceY > 0) {
				py = 1.6;
			}
			else if(DistanceY < 0){
				py = -1.6;
			}
		}
		else {											//If PowerUp is Active
			if(DistanceX > 0) {
				px = -1.6;
			}
			else if(DistanceX < 0){
				px = 1.6;
			}

			if(DistanceY > 0) {
				py = -1.6;
			}
			else if(DistanceY < 0) {
				py = 1.6;
			}
		}

		double Jiren_midx = Jiren.getLayoutX() + Jiren_Width;
		double Jiren_midy = Jiren.getLayoutY() + Jiren_Height;
		double kx = Jiren_midx + px;
		double ky = Jiren_midy + py;

		if (kx - Jiren_Width >= 0 &&								//Stops character from going out of bounds
				kx + Jiren_Width <= W &&
				ky - Jiren_Height >= 0 &&
				ky + Jiren_Height <= H) {

			if(Collision_AI(kx,ky)==false) {
				Jiren.relocate(kx-Jiren_Width, ky-Jiren_Height);	//If direct movement possible, move straight there
			}
			else if(Collision_AI(kx,ky-py)==false){						//Else negate +y direction
				Jiren.relocate(kx-Jiren_Width, ky-Jiren_Height-py);
			}
			else if(Collision_AI(kx+px,ky)==false){
				Jiren.relocate(kx-Jiren_Width+px, ky-Jiren_Height);		//Else negate -x direction
			}
			else if(Collision_AI(kx,ky+py)==false){						
				Jiren.relocate(kx-Jiren_Width, ky-Jiren_Height+py);		//Else negate -y direction
			}
			else if(Collision_AI(kx-px,ky)==false){
				Jiren.relocate(kx-Jiren_Width-px, ky-Jiren_Height);		//Else negate +x direction
			}
		}


	}

	//Move AI
	private void moveKefla() {														//Attacks User

		double px = 0;																	
		double py = 0;

		final double Kefla_Width = Kefla.getBoundsInLocal().getWidth() / 2;			//Kefla's mid point
		final double Kefla_Height = Kefla.getBoundsInLocal().getHeight() / 2;

		double DistanceX = Goku.getLayoutX() + Goku.getBoundsInLocal().getWidth() / 2 -			//User X Distance away from AI
				(Kefla.getLayoutX() + Kefla_Width);

		double DistanceY = Goku.getLayoutY() + Goku.getBoundsInLocal().getHeight() / 2 -		//User Y Distance away from AI
				(Kefla.getLayoutY() + Kefla_Height);

		if(PowerUp == 0) {									//If PowerUp is Inactive
			if(DistanceX > 0) {
				px = 1.4;
			}
			else {
				px = -1.4;
			}

			if(DistanceY > 0) {
				py = 1.4;
			}
			else {
				py = -1.4;
			}
		}
		else {												//If PowerUp is Active
			if(DistanceX > 0) {
				px = -1.4;
			}
			else {
				px = 1.4;
			}

			if(DistanceY > 0) {
				py = -1.4;
			}
			else {
				py = 1.4;
			}
		}

		double kx = Kefla.getLayoutX() + Kefla_Width + px;
		double ky = Kefla.getLayoutY() + Kefla_Height + py;

		if (kx - Kefla_Width >= 0 &&			//Stops character from going out of bounds
				kx + Kefla_Width <= W &&
				ky - Kefla_Height >= 0 &&
				ky + Kefla_Height <= H) {
			if(Collision_AI(kx,ky)==false) {									//If direct movement possible, move straight there
				Kefla.relocate(kx-Kefla_Width, ky-Kefla_Height);
			}
			else if(Collision_AI(kx-px,ky)==false){								//Else negate +x
				if(py!=0) {														//If Y Distance is not equal to zero	
					Kefla.relocate(kx-Kefla_Width-px, ky-Kefla_Height);
				}
				else {
					Kefla.relocate(kx-Kefla_Width-px, ky-Kefla_Height-1.4);		//Else just move up
				}
			}
			else if(Collision_AI(kx,ky+py)==false){								//Else negate -y
				if(px!=0) {														//If X Distance is not equal to zero
					Kefla.relocate(kx-Kefla_Width, ky-Kefla_Height+py);			
				}
				else {
					Kefla.relocate(kx-Kefla_Width+1.4, ky-Kefla_Height+py);		//Else just move right
				}
			}
			else if(Collision_AI(kx+px,ky)==false){								//Else negate -x
				if(py!=0) {														//If Y Distance is not equal to zero
					Kefla.relocate(kx-Kefla_Width+px, ky-Kefla_Height);			
				}
				else {
					Kefla.relocate(kx-Kefla_Width-px, ky-Kefla_Height-1.4);		//Else just move Left
				}
			}
			else if(Collision_AI(kx,ky-py)==false){								//Else negate +y
				if(px!=0) {														//If X Distance is not equal to zero
					Kefla.relocate(kx-Kefla_Width, ky-Kefla_Height-py);			
				}
				else {
					Kefla.relocate(kx-Kefla_Width-1.4, ky-Kefla_Height-py);		//Else just move Down
				}
			}
		}

	}

	//Move AI
	private void moveToppo() {			//Circles the track

		final double Toppo_Width = Toppo.getBoundsInLocal().getWidth() / 2;
		final double Toppo_Height = Toppo.getBoundsInLocal().getHeight() / 2;
		double kx = Toppo.getLayoutX() + Toppo_Width;
		double ky = Toppo.getLayoutY() + Toppo_Height;
		double dx = 1.5, dy = 1.5;


		if(Toppo_Secondary_Count == 0) {
			Random rand = new Random();
			int  n = rand.nextInt(4);	//Number between 3 and 0
			Toppo_Direction = n;
		}


		if(Toppo_Direction == 0) {												//If random number is 0
			if(Collision_AI(kx-dx,ky)==false) {									//Move left if possible
				Toppo.relocate(kx-Toppo_Width-dx, ky-Toppo_Height);				
				Toppo_Secondary_Count = 1;									
			}
			else {
				Toppo_Secondary_Count = 0;
			}
		}
		else if(Toppo_Direction == 1) {											//If random number is 1
			if(Collision_AI(kx,ky+dy)==false) {									//Move down if possible
				Toppo.relocate(kx-Toppo_Width, ky-Toppo_Height + dy);
				Toppo_Secondary_Count = 1;
			}
			else {
				Toppo_Secondary_Count = 0;
			}
		}
		else if(Toppo_Direction == 2) {											//If random number is 2
			if(Collision_AI(kx+dx,ky)==false) {									//Move right if possible
				Toppo.relocate(kx-Toppo_Width+dx, ky-Toppo_Height);
				Toppo_Secondary_Count = 1;
			}
			else {
				Toppo_Secondary_Count = 0;
			}
		}
		else if(Toppo_Direction == 3) {											//If random number is 3
			if(Collision_AI(kx,ky-dy)==false) {									//Move up if possible
				Toppo.relocate(kx-Toppo_Width, ky-Toppo_Height-dy);
				Toppo_Secondary_Count = 1;
			}
			else {
				Toppo_Secondary_Count = 0;
			}
		}
	}

	//Move AI
	private void moveHit() {			//Teleports in close proximity to user

		final double Goku_Width = Goku.getBoundsInLocal().getWidth()  / 2;
		final double Goku_Height = Goku.getBoundsInLocal().getHeight() / 2;
		double Goku_midx = Goku_Width + Goku.getLayoutX();
		double Goku_midy = Goku_Height+ Goku.getLayoutY();

		//		System.out.println(Hit_Count);
		if (Hit_Count != 500) {
			Hit_Count ++;
		}
		else {
			Random rand = new Random();
			int  n = rand.nextInt(2);	//Number between 1 and 0
			Hit_Direction = n;


			if(PowerUp==0) {						//Occurs every 8-10 seconds if powerup is 0
				for(int i=0;i<pellet.size();i++) {
					double pellet_midx = pellet.get(i).getLayoutX() + (pellet.get(i).getBoundsInLocal().getWidth()/2);
					double pellet_midy = pellet.get(i).getLayoutY() + (pellet.get(i).getBoundsInLocal().getHeight()/2);
					//			System.out.println(pellet_midx);
					//			System.out.println(pellet_midy);
					double pellet_width = pellet.get(i).getBoundsInLocal().getWidth()/2;
					double pellet_height = pellet.get(i).getBoundsInLocal().getHeight()/2;

					if(Hit_Direction == 0) {									//If random number is 0
						if(Collision_Goku(Goku_midx+120,Goku_midy) == false) {			//Check 120 spaces right of the user
							if 		((Goku_midx + 120 - Goku_Width) <= (pellet_midx + pellet_width + 7) &&		//Check if pellet is in this spot
									(Goku_midy - Goku_Height) <= (pellet_midy + pellet_height + 7) &&
									(Goku_midx + 120 + Goku_Width) >= (pellet_midx - pellet_width - 7) &&
									(Goku_midy + Goku_Height) >= (pellet_midy - pellet_height - 7))
							{
								Hit.relocate(Goku_midx-Goku_Width+120,Goku_midy-Goku_Height);
							}
						}
						else if(Collision_Goku(Goku_midx,Goku_midy+120) == false) {			//Check 120 spaces below of the user

							if 		((Goku_midx - Goku_Width) <= (pellet_midx + pellet_width + 7) &&		//Check if pellet is in this spot
									(Goku_midy + 120 - Goku_Height) <= (pellet_midy + pellet_height + 7) &&
									(Goku_midx  + Goku_Width) >= (pellet_midx - pellet_width - 7) &&
									(Goku_midy + 120 + Goku_Height) >= (pellet_midy - pellet_height - 7))
							{
								Hit.relocate(Goku_midx-Goku_Width,Goku_midy-Goku_Height+120);
							}
						}
						else if(Collision_Goku(Goku_midx,Goku_midy-120) == false) {		//Check 120 spaces above the user

							if 		((Goku_midx  - Goku_Width) <= (pellet_midx + pellet_width + 7) &&	//Check if pellet is in this spot
									(Goku_midy - 120- Goku_Height) <= (pellet_midy + pellet_height + 7) &&
									(Goku_midx  + Goku_Width) >= (pellet_midx - pellet_width - 7) &&
									(Goku_midy - 120 + Goku_Height) >= (pellet_midy - pellet_height - 7))
							{
								Hit.relocate(Goku_midx-Goku_Width,Goku_midy-Goku_Height-120);
							}
						}
						else if(Collision_Goku(Goku_midx-120,Goku_midy) == false) {					//Check 120 spaces left of the user

							if 		((Goku_midx - 120 - Goku_Width) <= (pellet_midx + pellet_width + 7) &&	//Check if pellet is in this spot
									(Goku_midy - Goku_Height) <= (pellet_midy + pellet_height + 7) &&
									(Goku_midx - 120 + Goku_Width) >= (pellet_midx - pellet_width - 7) &&
									(Goku_midy  + Goku_Height) >= (pellet_midy - pellet_height - 7))
							{
								Hit.relocate(Goku_midx-Goku_Width-120,Goku_midy-Goku_Height);
							}
						}


					}
					else if(Hit_Direction == 1) {								//If random number is 1
						if(Collision_Goku(Goku_midx-120,Goku_midy) == false) {		//Check 120 spaces left of the user

							if 		((Goku_midx - 120 - Goku_Width) <= (pellet_midx + pellet_width + 7) &&	//Check if pellet is in this spot
									(Goku_midy - Goku_Height) <= (pellet_midy + pellet_height + 7) &&
									(Goku_midx - 120 + Goku_Width) >= (pellet_midx - pellet_width - 7) &&
									(Goku_midy  + Goku_Height) >= (pellet_midy - pellet_height - 7))
							{
								Hit.relocate(Goku_midx-Goku_Width-120,Goku_midy-Goku_Height);
							}
						}
						else if(Collision_Goku(Goku_midx,Goku_midy-120) == false) {				//Check 120 spaces above the user

							if 		((Goku_midx  - Goku_Width) <= (pellet_midx + pellet_width + 7) &&	//Check if pellet is in this spot
									(Goku_midy - 120- Goku_Height) <= (pellet_midy + pellet_height + 7) &&
									(Goku_midx  + Goku_Width) >= (pellet_midx - pellet_width - 7) &&
									(Goku_midy - 120 + Goku_Height) >= (pellet_midy - pellet_height - 7))
							{
								Hit.relocate(Goku_midx-Goku_Width,Goku_midy-Goku_Height-120);
							}
						}

						else if(Collision_Goku(Goku_midx,Goku_midy+120) == false) {			//Check 120 spaces below of the user

							if 		((Goku_midx - Goku_Width) <= (pellet_midx + pellet_width + 7) &&	//Check if pellet is in this spot
									(Goku_midy + 120 - Goku_Height) <= (pellet_midy + pellet_height + 7) &&
									(Goku_midx  + Goku_Width) >= (pellet_midx - pellet_width - 7) &&
									(Goku_midy + 120 + Goku_Height) >= (pellet_midy - pellet_height - 7))
							{
								Hit.relocate(Goku_midx-Goku_Width,Goku_midy-Goku_Height+120);
							}
						}
						else if(Collision_Goku(Goku_midx+120,Goku_midy) == false) {				//Check 120 spaces right of the user
							if 		((Goku_midx + 120 - Goku_Width) <= (pellet_midx + pellet_width + 7) &&	//Check if pellet is in this spot
									(Goku_midy - Goku_Height) <= (pellet_midy + pellet_height + 7) &&
									(Goku_midx + 120 + Goku_Width) >= (pellet_midx - pellet_width - 7) &&
									(Goku_midy + Goku_Height) >= (pellet_midy - pellet_height - 7))
							{
								Hit.relocate(Goku_midx-Goku_Width+120,Goku_midy-Goku_Height);
							}
						}


					}
				}
			}
			else {
				Hit.relocate(421,390);		//Relocate back to center if powerup is on
			}
			Hit_Count = 0;
		}
	}
	
	//Map generation
	private void Maze() {		
		int[] Array = {		
				1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,
				1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,3,1,0,0,0,0,0,
				1,2,1,0,0,0,1,2,2,2,2,2,2,1,3,2,2,2,2,2,1,0,0,0,1,2,1,0,0,0,0,0,
				1,2,1,0,0,0,1,3,1,1,1,1,2,1,2,1,1,1,1,2,1,0,0,0,1,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,2,2,2,1,1,2,1,2,1,1,2,2,2,1,1,1,1,1,2,1,0,0,0,0,0,
				1,2,2,2,2,2,2,2,2,2,2,1,2,1,2,1,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,1,2,2,2,0,2,2,2,2,2,2,2,1,1,1,1,1,1,2,1,0,0,0,0,0,
				1,2,1,0,0,0,0,1,2,1,1,1,1,1,1,1,1,1,2,1,0,0,0,0,1,2,1,0,0,0,0,0,
				1,2,1,0,0,0,0,1,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,1,2,1,0,0,0,0,0,
				1,2,1,0,0,0,0,1,2,1,1,1,1,1,1,1,1,1,2,1,0,0,0,0,1,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,1,2,1,0,0,0,0,0,0,0,1,2,1,1,1,1,1,1,2,1,0,0,0,0,0,
				1,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0,0,1,2,2,2,3,2,2,2,2,1,0,0,0,0,0,
				1,3,1,1,1,1,1,1,2,1,0,0,0,0,0,0,0,1,2,1,1,1,1,1,1,2,1,0,0,0,0,0,
				1,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,2,1,0,0,0,0,0,
				1,2,2,2,2,2,2,1,2,2,1,1,1,1,1,1,1,2,2,1,2,2,2,2,2,2,1,0,0,0,0,0,
				1,2,1,1,1,1,2,1,2,2,1,0,0,0,0,0,1,2,2,1,2,1,1,1,1,2,1,0,0,0,0,0,
				1,2,1,0,0,1,2,1,2,2,1,0,0,0,0,0,1,2,2,1,2,1,0,0,1,3,1,0,0,0,0,0,
				1,2,1,0,0,1,2,1,2,3,1,1,1,1,1,1,1,2,2,1,2,1,0,0,1,2,1,0,0,0,0,0,
				1,2,1,0,0,1,2,1,2,2,2,2,2,5,2,2,2,2,2,1,2,1,0,0,1,2,1,0,0,0,0,0,
				1,2,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,2,1,0,0,0,0,0,
				1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,
				1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0
		};

		int j=0;

		for(int i=0;i<Array.length;i++) {
			j = i/32;
			if(Array[i]==1) {													//Add a rectangle for every 1
				Rectangle vectangle = new Rectangle(32*(i%32),32*j,32,32);		
				vectangle.setFill(Color.SKYBLUE);
				wall.add(vectangle);
				map.getChildren().add(vectangle);
			}
			else if(Array[i]==2) {											//Add a ramen bowl every 2
				Image RamenImage = new Image("file:Resources/Ramen_Bowl.png",18,18,false,false);
				ImageView  Ramen = new ImageView();
				Ramen.setImage(RamenImage);
				Ramen.relocate((32*(i%32))+7,(32*j)+7);		//Plus 7 to compensate for smaller size ((32-18)/2=7)
				pellet.add(Ramen);
				map.getChildren().add(Ramen);
			}
			else if(Array[i]==3) {							//Add a dragon ball for every 3
				Image DragonBallImage = new Image("file:Resources/DragonBall_1star.png",20,20,false,false);
				ImageView DragonBall = new ImageView();
				DragonBall.setImage(DragonBallImage);
				DragonBall.relocate((32*(i%32))+6,(32*j)+6);
				dball.add(DragonBall);
				map.getChildren().add(DragonBall);
			}
			else if(Array[i]==5) {								//Goku starting position
				Goku.relocate((32*(i%32))+5,(32*j)+5);			//Accounting for size ((32-22)/2=5)
			}
		}
	}


	//Wall collision for user
	private boolean Collision_Goku (double x , double y) {

		for(int i=0;i<wall.size();i++) {
			double rectangle_midx = wall.get(i).getX() + (wall.get(i).getBoundsInLocal().getWidth()/2);
			double rectangle_midy = wall.get(i).getY() + (wall.get(i).getBoundsInLocal().getHeight()/2);
			double Goku_midx = x;
			double Goku_midy = y;
			double rectangle_width = 16;
			double rectangle_height = 16;
			double Goku_width = Goku.getBoundsInLocal().getWidth()/2;
			double Goku_height = Goku.getBoundsInLocal().getHeight()/2;

			if 		((Goku_midx - Goku_width) <= (rectangle_midx + rectangle_width-2) &&		//Check if players desired position  
					(Goku_midy - Goku_height) <= (rectangle_midy + rectangle_height-2) &&		//intersects with a wall
					(Goku_midx + Goku_width) >= (rectangle_midx - rectangle_width+2) &&
					(Goku_midy + Goku_height) >= (rectangle_midy - rectangle_height+2))
			{
				return true;
			}
		}
		return false;
	}


	//Wall collision for AI
	private boolean Collision_AI (double x , double y) {

		for(int i=0;i<wall.size();i++) {
			double rectangle_midx = wall.get(i).getX() + (wall.get(i).getBoundsInLocal().getWidth()/2);
			double rectangle_midy = wall.get(i).getY() + (wall.get(i).getBoundsInLocal().getHeight()/2);
			double AI_midx = x;
			double AI_midy = y;
			double rectangle_width = 16;
			double rectangle_height = 16;
			double AI_width = Kefla.getBoundsInLocal().getWidth()/2;
			double AI_height = Kefla.getBoundsInLocal().getHeight()/2;

			if 		((AI_midx - AI_width) <= (rectangle_midx + rectangle_width) &&			//Check if AI's desired position
					(AI_midy - AI_height) <= (rectangle_midy + rectangle_height) &&			//intersects with a wall
					(AI_midx + AI_width) >= (rectangle_midx - rectangle_width) &&
					(AI_midy + AI_height) >= (rectangle_midy - rectangle_height))
			{
				return true;
			}
		}
		return false;
	}

	//Pellet removal
	private void Pellets() {

		int count = 0;	//Check the number of pellets that have been eaten

		for(int i=0;i<pellet.size();i++) {
			double pellet_midx = pellet.get(i).getLayoutX() + (pellet.get(i).getBoundsInLocal().getWidth()/2);
			double pellet_midy = pellet.get(i).getLayoutY() + (pellet.get(i).getBoundsInLocal().getHeight()/2);
			double Goku_midx = Goku.getLayoutX() + (Goku.getBoundsInLocal().getWidth()/2);
			double Goku_midy = Goku.getLayoutY() + (Goku.getBoundsInLocal().getHeight()/2);
			double pellet_width = pellet.get(i).getBoundsInLocal().getWidth()/2;
			double pellet_height = pellet.get(i).getBoundsInLocal().getHeight()/2;
			double Goku_width = Goku.getBoundsInLocal().getWidth()/2;
			double Goku_height = Goku.getBoundsInLocal().getHeight()/2;

			if 		((Goku_midx - Goku_width) <= (pellet_midx + pellet_width) &&			//If Goku intersects with Pellet
					(Goku_midy - Goku_height) <= (pellet_midy + pellet_height) &&			//remove pellet from map
					(Goku_midx + Goku_width) >= (pellet_midx - pellet_width) &&
					(Goku_midy + Goku_height) >= (pellet_midy - pellet_height))
			{
				map.getChildren().remove(pellet.get(i));
			}
			if(map.getChildren().indexOf(pellet.get(i))==-1) {
				count++;
			}
		}

		Score(count);														//Add the pellet to the score
	}


	//Collectible effects and removal
	private void Collectibles() {

		int PowerUp_Total = 0;
		for(int i=0;i<dball.size();i++) {
			double dball_midx = dball.get(i).getLayoutX() + (dball.get(i).getBoundsInLocal().getWidth()/2);
			double dball_midy = dball.get(i).getLayoutY() + (dball.get(i).getBoundsInLocal().getHeight()/2);
			double Goku_midx = Goku.getLayoutX() + (Goku.getBoundsInLocal().getWidth()/2);
			double Goku_midy = Goku.getLayoutY() + (Goku.getBoundsInLocal().getHeight()/2);
			double dball_width = 10;
			double dball_height = 10;
			double Goku_width = Goku.getBoundsInLocal().getWidth()/2;
			double Goku_height = Goku.getBoundsInLocal().getHeight()/2;

			if 		((Goku_midx - Goku_width) <= (dball_midx + dball_width) &&		//If Goku intersects with Dragon Ball
					(Goku_midy - Goku_height) <= (dball_midy + dball_height) &&		//remove Dragon ball from map
					(Goku_midx + Goku_width) >= (dball_midx - dball_width) &&
					(Goku_midy + Goku_height) >= (dball_midy - dball_height))
			{
				map.getChildren().remove(dball.get(i));
			}

			if(map.getChildren().indexOf(dball.get(i)) == -1) {			//Based on number of Dragon
				PowerUp_Total++;										//Balls collected
			}

		}

		if(Collectable_Count == PowerUp_Total && PowerUp_Countdown==480) {	//If PowerUp isn't activated
			PowerUp = 0;
			PowerUp_Countdown = 0;
		}
		else if(Collectable_Count != PowerUp_Total) { //Start or restart a
			PowerUp = 1;							  //count down if PowerUp activated
			PowerUp_Countdown = 479;
		}
		else if(Collectable_Count == PowerUp_Total && PowerUp_Countdown!=480) {
			PowerUp = 1;
			PowerUp_Countdown--;
		}
		if(PowerUp_Countdown<=0) {			//If PowerUp time has elapsed, then reset
			PowerUp_Countdown = 480;
		}

		Collectable_Count = PowerUp_Total;

		if(PowerUp==1 && GokuImageString == "Goku") {			//Change the image base on
			double Goku_x = Goku.getLayoutX();					//number of Dballs collected
			double Goku_y = Goku.getLayoutY();
			map.getChildren().remove(Goku);
			GokuImage = new Image("file:Resources/Goku_UltraInstinct.png",22,22,false,false);
			Goku = new ImageView(GokuImage);
			map.getChildren().add(Goku);
			Goku.relocate(Goku_x,Goku_y);
			GokuImageString = "Goku_UltraInstinct";
		}
		else if(PowerUp==0 && GokuImageString == "Goku_UltraInstinct") {
			double Goku_x = Goku.getLayoutX();
			double Goku_y = Goku.getLayoutY();
			map.getChildren().remove(Goku);
			GokuImage = new Image("file:Resources/Goku.png",22,22,false,false);
			Goku = new ImageView(GokuImage);
			map.getChildren().add(Goku);
			Goku.relocate(Goku_x,Goku_y);
			GokuImageString = "Goku";
		}
	}

	//Increase the score
	private void Score(int value) {
		map.getChildren().remove(ScoreT);					//Score total based on enemies defeated and pellets eaten
		Score_Pellets = 50*value;
		Score_Total = Score_Pellets + Score_Enemies;
		ScoreT = new Text(910,240,Integer.toString(Score_Total));
		ScoreT.setFont(Font.font("ARIAL", 30));
		ScoreT.setFill(Color.WHITE);
		map.getChildren().add(ScoreT);
	}

	//Collision effects
	private void LifeChange(Stage stage) {
		double Kefla_midx = Kefla.getLayoutX() + Kefla.getBoundsInLocal().getWidth();
		double Kefla_midy = Kefla.getLayoutY() + Kefla.getBoundsInLocal().getHeight();
		double Jiren_midx = Jiren.getLayoutX() + Jiren.getBoundsInLocal().getWidth();
		double Jiren_midy = Jiren.getLayoutY() + Jiren.getBoundsInLocal().getHeight();
		double Toppo_midx = Toppo.getLayoutX() + Toppo.getBoundsInLocal().getWidth();
		double Toppo_midy = Toppo.getLayoutY() + Toppo.getBoundsInLocal().getHeight();
		double Hit_midx = Hit.getLayoutX() + Hit.getBoundsInLocal().getWidth();
		double Hit_midy = Hit.getLayoutY() + Hit.getBoundsInLocal().getHeight();
		double Goku_midx = Goku.getLayoutX() + Goku.getBoundsInLocal().getWidth();
		double Goku_midy = Goku.getLayoutY() + Goku.getBoundsInLocal().getHeight();
		double AI_width = Kefla.getBoundsInLocal().getWidth()/2;
		double AI_height = Kefla.getBoundsInLocal().getHeight()/2;
		double Goku_width = Goku.getBoundsInLocal().getWidth()/2;
		double Goku_height = Goku.getBoundsInLocal().getHeight()/2;
		String musicFile = "Resources/Collision_Noise.wav";


		if(PowerUp==0) {				//If in regular form

			if(map.getChildren().indexOf(Bean_2)==-1 && map.getChildren().indexOf(Bean_1)==-1) {	//No senzu beans remaining

				if 		((Goku_midx - Goku_width) <= (Kefla_midx + AI_width) &&			//In case of collision
						(Goku_midy - Goku_height) <= (Kefla_midy + AI_height) &&
						(Goku_midx + Goku_width) >= (Kefla_midx - AI_width) &&
						(Goku_midy + Goku_height) >= (Kefla_midy - AI_height)
						||
						(Goku_midx - Goku_width) <= (Jiren_midx + AI_width) &&
						(Goku_midy - Goku_height) <= (Jiren_midy + AI_height) &&
						(Goku_midx + Goku_width) >= (Jiren_midx - AI_width) &&
						(Goku_midy + Goku_height) >= (Jiren_midy - AI_height)
						||
						(Goku_midx - Goku_width) <= (Toppo_midx + AI_width) &&
						(Goku_midy - Goku_height) <= (Toppo_midy + AI_height) &&
						(Goku_midx + Goku_width) >= (Toppo_midx - AI_width) &&
						(Goku_midy + Goku_height) >= (Toppo_midy - AI_height)
						||
						(Goku_midx - Goku_width) <= (Hit_midx + AI_width) &&
						(Goku_midy - Goku_height) <= (Hit_midy + AI_height) &&
						(Goku_midx + Goku_width) >= (Hit_midx - AI_width) &&
						(Goku_midy + Goku_height) >= (Hit_midy - AI_height))

				{
					Media sound = new Media(new File(musicFile).toURI().toString());	//Used to play
					MediaPlayer mediaPlayer = new MediaPlayer(sound);					//Collision sound
					mediaPlayer.setCycleCount(1);
					mediaPlayer.play();
					gamePause();
					HighScoreScreen.setScore();
					gamOvr gO = new gamOvr();
					gO.gmeOvr(stage);
				}
			}

			if 		((Goku_midx - Goku_width) <= (Kefla_midx + AI_width) &&
					(Goku_midy - Goku_height) <= (Kefla_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Kefla_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Kefla_midy - AI_height)
					||
					(Goku_midx - Goku_width) <= (Jiren_midx + AI_width) &&
					(Goku_midy - Goku_height) <= (Jiren_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Jiren_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Jiren_midy - AI_height)
					||
					(Goku_midx - Goku_width) <= (Toppo_midx + AI_width) &&
					(Goku_midy - Goku_height) <= (Toppo_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Toppo_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Toppo_midy - AI_height)
					||
					(Goku_midx - Goku_width) <= (Hit_midx + AI_width) &&
					(Goku_midy - Goku_height) <= (Hit_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Hit_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Hit_midy - AI_height))
			{

				if(map.getChildren().indexOf(Bean_2)==-1) {
					Media sound = new Media(new File(musicFile).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(sound);
					mediaPlayer.setCycleCount(1);
					mediaPlayer.play();
					map.getChildren().remove(Bean_1);
					Goku.relocate(421,645);				//If user dies, move all characters back to
					Kefla.relocate(640,453);			//original position
					Jiren.relocate(200,197);
					Hit.relocate(421,390);
					Toppo.relocate(421,295);
					Toppo_Secondary_Count = 0;
					Hit_Count = 0;
					North = false;South = false;East = false;West = false;		//Set direction of user to 0



				}
				else {
					Media sound = new Media(new File(musicFile).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(sound);
					mediaPlayer.setCycleCount(1);
					mediaPlayer.play();
					map.getChildren().remove(Bean_2);
					Goku.relocate(421,645);
					Kefla.relocate(640,453);
					Jiren.relocate(200,197);
					Hit.relocate(421,390);
					Toppo.relocate(421,295);
					Bean_1.relocate(925,600);
					Toppo_Secondary_Count = 0;
					Hit_Count = 0;
					North = false;South = false;East = false;West = false;		//Set direction of user to 0
				}
			}
		}

		else {				//PowerUp active

			if 		((Goku_midx - Goku_width) <= (Kefla_midx + AI_width) &&		//Goku collides with Kefla
					(Goku_midy - Goku_height) <= (Kefla_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Kefla_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Kefla_midy - AI_height)){

				Kefla.relocate(640,453);
				Score_Enemies+=250;							//Plus 150 point for each enemy you defeat
			}


			else if ((Goku_midx - Goku_width) <= (Jiren_midx + AI_width) &&			//Goku collides with Jiren
					(Goku_midy - Goku_height) <= (Jiren_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Jiren_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Jiren_midy - AI_height)){

				Jiren.relocate(200,197);
				Score_Enemies+=250;
			}

			else if ((Goku_midx - Goku_width) <= (Toppo_midx + AI_width) &&		//goku collides with Jiren
					(Goku_midy - Goku_height) <= (Toppo_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Toppo_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Toppo_midy - AI_height)){

				Toppo.relocate(421,295);
				Toppo_Secondary_Count = 0;
				Score_Enemies+=150;
			}

			else if ((Goku_midx - Goku_width) <= (Hit_midx + AI_width) &&			//Goku collides with Hit
					(Goku_midy - Goku_height) <= (Hit_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Hit_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Hit_midy - AI_height)){

				Hit.relocate(421,390);
				Score_Enemies+=500;
			}

		}
	}

	//Condition for winning the game
	private void GameWin(Stage primaryStage) {
		int pellet_count = 0;									//If all Ramen is eaten, end the game
		for(int i=0;i<pellet.size();i++) {
			if(map.getChildren().indexOf(pellet.get(i))==-1) {
				pellet_count++;
			}
		}
		if(pellet_count == pellet.size()) {
			gamePause();
			HighScoreScreen.setScore();
			uWon won = new uWon();
			won.win(primaryStage);
		}
	}
	
	//
	public static void lvlTime(Stage primaryStage){					//TimeLIne for timer
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				timeSeconds--;
				min = timeSeconds/60;
				sec = timeSeconds%60;
				if(sec < 10){
					TimeValue.setText(min.toString() + ":0" + sec.toString());
				}else{
					TimeValue.setText(min.toString() + ":" + sec.toString());
				}
				if(timeSeconds <= 0){
					gamePause();
					HighScoreScreen.setScore();
					gamOvr gO = new gamOvr();
					gO.gmeOvr(primaryStage);
					timeline.stop();
				}
			}
		}));
		timeline.playFromStart();
	}


	public static void resettimer(){
		timeSeconds = stime;

	}

	public static void gamePause(){
		Movement.stop();
		timeline.pause();
	}

	public static void gameResume(){
		//		Animation.Status.RUNNING;
		Movement.start();
		timeline.play();
	}


	public void lifeTime(){				//Timer for adding a life
		lifetimeline = new Timeline();
		lifetimeline.setCycleCount(Timeline.INDEFINITE);
		lifetimeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				Seconds--;
				LifeAddition(Seconds);
				if(Seconds <= 0){
					lifetimeline.stop();
				}
			}
		}));
		lifetimeline.playFromStart();
	}

	private void LifeAddition(int seconds) {					//Add a life with 40 seconds remaining
		if(seconds == 40) {
			if(map.getChildren().indexOf(Bean_1)==-1) {
				map.getChildren().add(Bean_1);
			}
			else {
				Bean_1.relocate(905,600);
				map.getChildren().add(Bean_2);
				Bean_2.relocate(945,600);
			}
		}
	}

	public static Scene getScene() {
		return scene;

	}

	public static String getScore(){
		String finalScore = ScoreT.getText();
		return finalScore;
	}
}