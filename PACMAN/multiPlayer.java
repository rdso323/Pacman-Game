package PACMAN;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.util.Duration;

/**
 * Hold down an arrow key to have your character move around the screen.
 * Hold down the shift key to have the character run.
 */
public class multiPlayer extends Application {

	private static final double W = 1024, H = 768;


	//    private static final String GOKU_IMAGE_LOC =
	//    		"https://img00.deviantart.net/3cd7/i/2016/222/a/6/goku_by_bthrgking-dadbnvv.jpg";

	//    private static final String GOKU_ULTRAINSTINCT_IMAGE_LOC =
	//    		"https://www.flickr.com/photos/phathuu/37545359006";
	//
	//    private static final String JIREN_IMAGE_LOC =
	//        		"https://upload.wikimedia.org/wikipedia/en/7/7c/Vegetagt7.jpg";

	//  private static final String RAMEN_BOWL_LOC =
	//	https://cdn.pixabay.com/photo/2014/04/02/10/52/rice-304804_960_720.png;

	//private static final String DRAGON_BALL_LOC =
	//https://upload.wikimedia.org/wikipedia/commons/d/d8/Dragonballstar.png;

	//private static final String SENZU_BEAN_LOC =
	//"http://shiftingpixel.com/slir/w900/wp-content/uploads/2008/02/coffee-bean.jpg;


	static Scene scene;
	private Image GokuImage, JirenImage, KeflaImage, ToppoImage, HitImage, BeanImage;
	private ImageView Bean_1,Bean_2;
	private Node  Goku, Jiren, Kefla, Toppo, Hit;
	private Text Score_Text,Time_Text,Lives_Text,ScoreT;

	private static Text TimeValue;
	private Integer min = 2;
	private Integer sec = 0;
	public Integer minutesR,secondsR;
	private Integer stime = 120;
	private Timeline timeline_1;
	private Integer timeSeconds = stime;

	private int Score_Pellets = 0, Score_Enemies = 0, Score_Total = 0;
	private int PowerUp = 0;
	private String GokuImageString;
	private int Toppo_Count = 0, Toppo_Count_Respawn = 0, Toppo_Secondary_Count = 0;		//Count to change direction
	private int Toppo_Direction = 0, Hit_Direction = 0;
	private int Hit_Count = 0, Collectable_Count = 0, PowerUp_Countdown = 480;

	Group map = new Group();


	private ArrayList<Rectangle> wall = new ArrayList<Rectangle>();
	private ArrayList<ImageView> pellet = new ArrayList<ImageView>();
	private ArrayList<ImageView> dball = new ArrayList<ImageView>();
	private ArrayList<ImageView> Lives = new ArrayList<ImageView>();


	boolean North_1, South_1, East_1, West_1, North_2, South_2, East_2, West_2;


	//    private static final Integer STARTTIME = 15;
	//    private Timeline timeline;
	//    private Label TimerLabel = new Label();
	//    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub	
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

		TimeValue = new Text(910, 420, "2:00");
		TimeValue .setFont(Font.font("ARIAL", 30));
		TimeValue .setFill(Color.WHITE);

		Lives_Text = new Text(900, 576, "Lives:");
		Lives_Text.setFont(Font.font("ARIAL", 30));
		Lives_Text.setFill(Color.WHITE);
		Lives_Text.setUnderline(true);


		scene = new Scene(map, W, H, Color.BLACK);
		Maze();
		Clock_Timer();
		//		moveGokuTo(W / 2, H / 2);			//Starting position

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
		map.getChildren().add(TimeValue);


		Jiren.relocate(200,197);
		Kefla.relocate(640,453);
		Toppo.relocate(421,295);
		Hit.relocate(421,390);
		Bean_1.relocate(925,600);
		//		Bean_2.relocate(945,600);




		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.UP) {
					North_1 = true; East_1 = false; South_1 = false; West_1 = false;
				}
				if(event.getCode() == KeyCode.LEFT) {
					West_1 = true; North_1 = false; South_1 = false; East_1 = false;
				}
				if(event.getCode() == KeyCode.DOWN) {
					South_1 = true; East_1 = false; North_1 = false; West_1 = false;
				}
				if(event.getCode() == KeyCode.RIGHT) {
					East_1 = true; North_1 = false; South_1 = false; West_1 = false;
				}
				if(event.getCode() == KeyCode.W) {
					North_2 = true; East_2 = false; South_2 = false; West_2 = false;
				}
				if(event.getCode() == KeyCode.A) {
					West_2 = true; North_2 = false; South_2 = false; East_2 = false;
				}
				if(event.getCode() == KeyCode.S) {
					South_2 = true; East_2 = false; North_2 = false; West_2 = false;
				}
				if(event.getCode() == KeyCode.D) {
					East_2 = true; North_2 = false; South_2 = false; West_2 = false;
				}
			}
		});

		AnimationTimer Movement = new AnimationTimer() {
			@Override
			public void handle(long now) {
				double dx = 0, dy = 0, jx = 0, jy = 0;
				if(PowerUp == 1) {
					if (North_1){
						dy -= 2.5;					//Speed of movement
					}
					else if (East_1){
						dx += 2.5;
					}

					else if (South_1){
						dy += 2.5;
					}
					else if (West_1){
						dx -= 2.5;
					}
				}
				else {
					if (North_1){
						dy -= 2;					
					}
					else if (East_1){
						dx += 2;
					}

					else if (South_1){
						dy += 2;
					}
					else if (West_1){
						dx -= 2;
					}
				}


				if (North_2){
					jy -= 2;					
				}
				else if (East_2){
					jx += 2;
				}
				else if (South_2){
					jy += 2;
				}
				else if (West_2){
					jx -= 2;
				}


				moveGokuBy(dx, dy);
				moveJirenBy(jx,jy);
	//			moveKefla();
	//			moveToppo();
				moveHit();
				LifeChange();
				GameWin();

			}
		};
		Movement.start();
	}


	private void moveGokuBy(double dx, double dy) {
		if (dx == 0 && dy == 0) return;

		final double Goku_Width = Goku.getBoundsInLocal().getWidth()  / 2;			//Mid point
		final double Goku_Height = Goku.getBoundsInLocal().getHeight() / 2;

		double x = Goku_Width + Goku.getLayoutX() + dx;
		double y = Goku_Height+ Goku.getLayoutY() + dy;
		moveGokuTo(x, y);						//Moves mid point to this point
	}

	private void moveGokuTo(double x, double y) {
		final double Goku_Width = Goku.getBoundsInLocal().getWidth() / 2;
		final double Goku_Height = Goku.getBoundsInLocal().getHeight() / 2;

		//		if (x - Goku_Width >= 0 &&			//Stops character from going out of bounds
		//		x + Goku_Width <= W &&
		//		y - Goku_Height >= 0 &&
		//		y + Goku_Height <= H) {

		if(Goku.getLayoutY()>=H) {
			Goku.relocate(Goku.getLayoutX(),(-2*Goku_Height)+0.1);
		}
		else if(Goku.getLayoutY()+(2*Goku_Height)<=0) {
			Goku.relocate(Goku.getLayoutX(),H-0.1);
		}
		else if(Collision_User(x,y) == false) {
			Goku.relocate(x - Goku_Width , y - Goku_Height);
		}

		Pellets();
		Collectibles();

	}
	private void moveJirenBy(double jx, double jy) {
		if (jx == 0 && jy == 0) return;

		final double Jiren_Width = Jiren.getBoundsInLocal().getWidth()  / 2;			//Mid point
		final double Jiren_Height = Jiren.getBoundsInLocal().getHeight() / 2;

		double x = Jiren_Width + Jiren.getLayoutX() + jx;
		double y = Jiren_Height+ Jiren.getLayoutY() + jy;
		moveJirenTo(x, y);						//Moves mid point to this point
	}

	private void moveJirenTo(double x, double y) {
		final double Jiren_Width = Jiren.getBoundsInLocal().getWidth() / 2;
		final double Jiren_Height = Jiren.getBoundsInLocal().getHeight() / 2;


		if(Jiren.getLayoutY()>=H) {
			Jiren.relocate(Jiren.getLayoutX(),(-2*Jiren_Height)+0.1);
		}
		else if(Jiren.getLayoutY()+(2*Jiren_Height)<=0) {
			Jiren.relocate(Jiren.getLayoutX(),H-0.1);
		}
		else if(Collision_User(x,y) == false) {
			Jiren.relocate(x - Jiren_Width , y - Jiren_Height);
		}

	}

	private void moveKefla() {			//Attacks User

		double px = 0;
		double py = 0;

		final double Kefla_Width = Kefla.getBoundsInLocal().getWidth() / 2;
		final double Kefla_Height = Kefla.getBoundsInLocal().getHeight() / 2;

		double DistanceX = Goku.getLayoutX() + Goku.getBoundsInLocal().getWidth() / 2 -
				(Kefla.getLayoutX() + Kefla_Width);

		double DistanceY = Goku.getLayoutY() + Goku.getBoundsInLocal().getHeight() / 2 -
				(Kefla.getLayoutY() + Kefla_Height);

		if(PowerUp == 0) {
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
		else {
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
			if(Collision_AI(kx,ky)==false) {
				Kefla.relocate(kx-Kefla_Width, ky-Kefla_Height);
			}
			else if(Collision_AI(kx-px,ky)==false){
				if(py!=0) {
					Kefla.relocate(kx-Kefla_Width-px, ky-Kefla_Height);
				}
				else {
					Kefla.relocate(kx-Kefla_Width-px, ky-Kefla_Height-1.4);
				}
			}
			else if(Collision_AI(kx,ky+py)==false){
				if(px!=0) {
					Kefla.relocate(kx-Kefla_Width, ky-Kefla_Height+py);
				}
				else {
					Kefla.relocate(kx-Kefla_Width+1.4, ky-Kefla_Height+py);
				}
			}
			else if(Collision_AI(kx+px,ky)==false){
				if(py!=0) {
					Kefla.relocate(kx-Kefla_Width+px, ky-Kefla_Height);
				}
				else {
					Kefla.relocate(kx-Kefla_Width-px, ky-Kefla_Height-1.4);
				}
			}
			else if(Collision_AI(kx,ky-py)==false){
				if(px!=0) {
					Kefla.relocate(kx-Kefla_Width, ky-Kefla_Height-py);
				}
				else {
					Kefla.relocate(kx-Kefla_Width-1.4, ky-Kefla_Height-py);
				}
			}
		}


		//		if(Collision_AI(kx,ky)==false) {
		//			Kefla.relocate(kx-Kefla_Width, ky-Kefla_Height);
		//		}
		//		else if(Collision_AI(kx-px,ky)==false){
		//			Kefla.relocate(kx-Kefla_Width-px, ky-Kefla_Height);
		//		}
		//		else if(Collision_AI(kx+px,ky)==false){
		//			Kefla.relocate(kx-Kefla_Width+px, ky-Kefla_Height);
		//		}
		//
		//		else if(Collision_AI(kx,ky-py)==false){
		//			Kefla.relocate(kx-Kefla_Width, ky-Kefla_Height-py);
		//		}
		//		else if(Collision_AI(kx,ky+py)==false){
		//			Kefla.relocate(kx-Kefla_Width, ky-Kefla_Height+py);
		//		}
	}


	private void moveToppo() {			//Circles the track 

		final double Toppo_Width = Toppo.getBoundsInLocal().getWidth() / 2;
		final double Toppo_Height = Toppo.getBoundsInLocal().getHeight() / 2;
		double kx = Toppo.getLayoutX() + Toppo_Width;
		double ky = Toppo.getLayoutY() + Toppo_Height;
		double dx = 1.5, dy = 1.5;


		if(Toppo_Secondary_Count == 0) {
			Random rand = new Random();
			int  n = rand.nextInt(4);	//Number between 3 and 1
			Toppo_Direction = n;
		}


		if(Toppo_Direction == 0) {
			if(Collision_AI(kx-dx,ky)==false) {
				Toppo.relocate(kx-Toppo_Width-dx, ky-Toppo_Height);
				Toppo_Secondary_Count = 1;
			}
			else {
				Toppo_Secondary_Count = 0;
			}
		}
		else if(Toppo_Direction == 1) {
			if(Collision_AI(kx,ky+dy)==false) {
				Toppo.relocate(kx-Toppo_Width, ky-Toppo_Height + dy);
				Toppo_Secondary_Count = 1;
			}
			else {
				Toppo_Secondary_Count = 0;
			}
		}
		else if(Toppo_Direction == 2) {
			if(Collision_AI(kx+dx,ky)==false) {
				Toppo.relocate(kx-Toppo_Width+dx, ky-Toppo_Height);
				Toppo_Secondary_Count = 1;
			}
			else {
				Toppo_Secondary_Count = 0;
			}
		}
		else if(Toppo_Direction == 3) {
			if(Collision_AI(kx,ky-dy)==false) {
				Toppo.relocate(kx-Toppo_Width, ky-Toppo_Height-dy);
				Toppo_Secondary_Count = 1;
			}
			else {
				Toppo_Secondary_Count = 0;
			}
		}


		//		System.out.println(Toppo_Count);

		//		if(Collision_AI(kx,ky-5)==true && Collision_AI(kx+5,ky)==true) {
		//			Toppo_Count = 1;
		//		}
		//		else if(Collision_AI(kx-5,ky)==true && (Collision_AI(kx,ky-5)==true)) {
		//			Toppo_Count = 2;
		//		}
		//		else if(Collision_AI(kx,ky+5)==true && (Collision_AI(kx-5,ky)==true)) {
		//			Toppo_Count = 3;
		//		}
		//		else if(Collision_AI(kx+5,ky)==true && (Collision_AI(kx,ky+5)==true)) {
		//			Toppo_Count = 0;
		//		}
		//
		//		if(Toppo_Count == 0){
		//			Toppo.relocate(kx-Toppo_Width, ky-Toppo_Height-2.2);
		//		}
		//		else if(Toppo_Count == 1){
		//			Toppo.relocate(kx-Toppo_Width-2.2, ky-Toppo_Height);
		//		}
		//		else if(Toppo_Count == 2){
		//			Toppo.relocate(kx-Toppo_Width, ky-Toppo_Height+2.2);
		//		}
		//		else if(Toppo_Count == 3){
		//			Toppo.relocate(kx-Toppo_Width+2.2, ky-Toppo_Height);
		//		}

	}


	private void moveHit() {			//Teleports in close proximity to user

		final double Hit_Width = Hit.getBoundsInLocal().getWidth() / 2;
		final double Hit_Height = Hit.getBoundsInLocal().getHeight() / 2;
		double Hit_midx = Hit.getLayoutX() + Hit_Width;
		double Hit_midy = Hit.getLayoutY() + Hit_Height;
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


			if(PowerUp==0) {
				for(int i=0;i<pellet.size();i++) {
					double pellet_midx = pellet.get(i).getLayoutX() + (pellet.get(i).getBoundsInLocal().getWidth()/2);
					double pellet_midy = pellet.get(i).getLayoutY() + (pellet.get(i).getBoundsInLocal().getHeight()/2);
					//			System.out.println(pellet_midx);
					//			System.out.println(pellet_midy);
					double pellet_width = pellet.get(i).getBoundsInLocal().getWidth()/2;
					double pellet_height = pellet.get(i).getBoundsInLocal().getHeight()/2;

					if(Hit_Direction == 0) {
						if(Collision_AI(Goku_midx+120,Goku_midy) == false) {
							if 		((Goku_midx + 120 - Goku_Width) <= (pellet_midx + pellet_width) &&
									(Goku_midy - Goku_Height) <= (pellet_midy + pellet_height) &&
									(Goku_midx + 120 + Goku_Width) >= (pellet_midx - pellet_width) &&
									(Goku_midy + Goku_Height) >= (pellet_midy - pellet_height))
							{
								Hit.relocate(Goku_midx-Goku_Width+120,Goku_midy-Goku_Height);
							}
						}
						else if(Collision_AI(Goku_midx,Goku_midy+120) == false) {

							if 		((Goku_midx - Goku_Width) <= (pellet_midx + pellet_width) &&
									(Goku_midy + 120 - Goku_Height) <= (pellet_midy + pellet_height) &&
									(Goku_midx  + Goku_Width) >= (pellet_midx - pellet_width) &&
									(Goku_midy + 120 + Goku_Height) >= (pellet_midy - pellet_height))
							{
								Hit.relocate(Goku_midx-Goku_Width,Goku_midy-Goku_Height+120);
							}
						}
						else if(Collision_AI(Goku_midx,Goku_midy-120) == false) {

							if 		((Goku_midx  - Goku_Width) <= (pellet_midx + pellet_width) &&
									(Goku_midy - 120- Goku_Height) <= (pellet_midy + pellet_height) &&
									(Goku_midx  + Goku_Width) >= (pellet_midx - pellet_width) &&
									(Goku_midy - 120 + Goku_Height) >= (pellet_midy - pellet_height))
							{
								Hit.relocate(Goku_midx-Goku_Width,Goku_midy-Goku_Height-120);
							}
						}
						else if(Collision_AI(Goku_midx-120,Goku_midy) == false) {

							if 		((Goku_midx - 120 - Goku_Width) <= (pellet_midx + pellet_width) &&
									(Goku_midy - Goku_Height) <= (pellet_midy + pellet_height) &&
									(Goku_midx - 120 + Goku_Width) >= (pellet_midx - pellet_width) &&
									(Goku_midy  + Goku_Height) >= (pellet_midy - pellet_height))
							{
								Hit.relocate(Goku_midx-Goku_Width-120,Goku_midy-Goku_Height);
							}
						}


					}
					else if(Hit_Direction == 1) {							//Increasing randomness
						if(Collision_AI(Goku_midx-120,Goku_midy) == false) {

							if 		((Goku_midx - 120 - Goku_Width) <= (pellet_midx + pellet_width) &&
									(Goku_midy - Goku_Height) <= (pellet_midy + pellet_height) &&
									(Goku_midx - 120 + Goku_Width) >= (pellet_midx - pellet_width) &&
									(Goku_midy  + Goku_Height) >= (pellet_midy - pellet_height))
							{
								Hit.relocate(Goku_midx-Goku_Width-120,Goku_midy-Goku_Height);
							}
						}
						else if(Collision_AI(Goku_midx,Goku_midy-120) == false) {

							if 		((Goku_midx  - Goku_Width) <= (pellet_midx + pellet_width) &&
									(Goku_midy - 120- Goku_Height) <= (pellet_midy + pellet_height) &&
									(Goku_midx  + Goku_Width) >= (pellet_midx - pellet_width) &&
									(Goku_midy - 120 + Goku_Height) >= (pellet_midy - pellet_height))
							{
								Hit.relocate(Goku_midx-Goku_Width,Goku_midy-Goku_Height-120);
							}
						}

						else if(Collision_AI(Goku_midx,Goku_midy+120) == false) {

							if 		((Goku_midx - Goku_Width) <= (pellet_midx + pellet_width) &&
									(Goku_midy + 120 - Goku_Height) <= (pellet_midy + pellet_height) &&
									(Goku_midx  + Goku_Width) >= (pellet_midx - pellet_width) &&
									(Goku_midy + 120 + Goku_Height) >= (pellet_midy - pellet_height))
							{
								Hit.relocate(Goku_midx-Goku_Width,Goku_midy-Goku_Height+120);
							}
						}
						else if(Collision_AI(Goku_midx+120,Goku_midy) == false) {
							if 		((Goku_midx + 120 - Goku_Width) <= (pellet_midx + pellet_width) &&
									(Goku_midy - Goku_Height) <= (pellet_midy + pellet_height) &&
									(Goku_midx + 120 + Goku_Width) >= (pellet_midx - pellet_width) &&
									(Goku_midy + Goku_Height) >= (pellet_midy - pellet_height))
							{
								Hit.relocate(Goku_midx-Goku_Width+120,Goku_midy-Goku_Height);
							}
						}


					}
				}
			}
			else {
				Hit.relocate(421,390);		//Relocate back to centre if powerup is on
			}
			Hit_Count = 0;
		}
	}

	private void Maze() {// throws FileNotFoundException{
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
			if(Array[i]==1) {
				Rectangle vectangle = new Rectangle(32*(i%32),32*j,32,32);
				vectangle.setFill(Color.SKYBLUE);
				wall.add(vectangle);
				map.getChildren().add(vectangle);
			}
			else if(Array[i]==2) {
				Image RamenImage = new Image("file:Resources/Ramen_Bowl.png",18,18,false,false);
				ImageView  Ramen = new ImageView();
				Ramen.setImage(RamenImage);
				Ramen.relocate((32*(i%32))+7,(32*j)+7);		//Plus 7 to compensate for smaller size ((32-18)/2=7)
				pellet.add(Ramen);
				map.getChildren().add(Ramen);
			}
			else if(Array[i]==3) {
				Image DragonBallImage = new Image("file:Resources/DragonBall_1star.png",20,20,false,false);
				ImageView DragonBall = new ImageView();
				DragonBall.setImage(DragonBallImage);
				DragonBall.relocate((32*(i%32))+6,(32*j)+6);
				dball.add(DragonBall);
				map.getChildren().add(DragonBall);
			}
			else if(Array[i]==5) {
				Goku.relocate((32*(i%32))+5,(32*j)+5);			//Starting position ((32-22)/2=5)
			}
		}
	}	


	private boolean Collision_User (double x , double y) {

		for(int i=0;i<wall.size();i++) {
			double rectangle_midx = wall.get(i).getX() + (wall.get(i).getBoundsInLocal().getWidth()/2);
			double rectangle_midy = wall.get(i).getY() + (wall.get(i).getBoundsInLocal().getHeight()/2);
			//			System.out.println(rectangle_midx);
			//			System.out.println(rectangle_midy);
			double User_midx = x;
			double User_midy = y;
			double rectangle_width = 16;
			double rectangle_height = 16;
			double User_width = Goku.getBoundsInLocal().getWidth()/2;
			double User_height = Goku.getBoundsInLocal().getHeight()/2;

			if 		((User_midx - User_width) <= (rectangle_midx + rectangle_width-2) &&
					(User_midy - User_height) <= (rectangle_midy + rectangle_height-2) &&
					(User_midx + User_width) >= (rectangle_midx - rectangle_width+2) &&
					(User_midy + User_height) >= (rectangle_midy - rectangle_height+2))
			{
				return true;
			}
		}
		return false;
	}


	private boolean Collision_AI (double x , double y) {

		for(int i=0;i<wall.size();i++) {
			double rectangle_midx = wall.get(i).getX() + (wall.get(i).getBoundsInLocal().getWidth()/2);
			double rectangle_midy = wall.get(i).getY() + (wall.get(i).getBoundsInLocal().getHeight()/2);
			//			System.out.println(rectangle_midx);
			//			System.out.println(rectangle_midy);
			double AI_midx = x;
			double AI_midy = y;
			double rectangle_width = 16;
			double rectangle_height = 16;
			double AI_width = Kefla.getBoundsInLocal().getWidth()/2;
			double AI_height = Kefla.getBoundsInLocal().getHeight()/2;

			if 		((AI_midx - AI_width) <= (rectangle_midx + rectangle_width) &&
					(AI_midy - AI_height) <= (rectangle_midy + rectangle_height) &&
					(AI_midx + AI_width) >= (rectangle_midx - rectangle_width) &&
					(AI_midy + AI_height) >= (rectangle_midy - rectangle_height))
			{
				return true;
			}
		}
		return false;
	}

	private void Pellets() {

		int count = 0;	//Check the number of pellets that have been eaten

		for(int i=0;i<pellet.size();i++) {
			double pellet_midx = pellet.get(i).getLayoutX() + (pellet.get(i).getBoundsInLocal().getWidth()/2);
			double pellet_midy = pellet.get(i).getLayoutY() + (pellet.get(i).getBoundsInLocal().getHeight()/2);
			//			System.out.println(pellet_midx);
			//			System.out.println(pellet_midy);
			double Goku_midx = Goku.getLayoutX() + (Goku.getBoundsInLocal().getWidth()/2);
			double Goku_midy = Goku.getLayoutY() + (Goku.getBoundsInLocal().getHeight()/2);
			double pellet_width = pellet.get(i).getBoundsInLocal().getWidth()/2;
			double pellet_height = pellet.get(i).getBoundsInLocal().getHeight()/2;
			double Goku_width = Goku.getBoundsInLocal().getWidth()/2;
			double Goku_height = Goku.getBoundsInLocal().getHeight()/2;

			if 		((Goku_midx - Goku_width) <= (pellet_midx + pellet_width) &&
					(Goku_midy - Goku_height) <= (pellet_midy + pellet_height) &&
					(Goku_midx + Goku_width) >= (pellet_midx - pellet_width) &&
					(Goku_midy + Goku_height) >= (pellet_midy - pellet_height))
			{
				//				System.out.println("Working");
				map.getChildren().remove(pellet.get(i));
			}
			if(map.getChildren().indexOf(pellet.get(i))==-1) {
				count++;
			}
		}

		Score(count);
	}


	private void Collectibles() {

		int PowerUp_Total = 0;
		for(int i=0;i<dball.size();i++) {
			double dball_midx = dball.get(i).getLayoutX() + (dball.get(i).getBoundsInLocal().getWidth()/2);
			double dball_midy = dball.get(i).getLayoutY() + (dball.get(i).getBoundsInLocal().getHeight()/2);
			//			System.out.println(dball_midx);
			//			System.out.println(dball_midy);
			double Goku_midx = Goku.getLayoutX() + (Goku.getBoundsInLocal().getWidth()/2);
			double Goku_midy = Goku.getLayoutY() + (Goku.getBoundsInLocal().getHeight()/2);
			double dball_width = 10;
			double dball_height = 10;
			double Goku_width = Goku.getBoundsInLocal().getWidth()/2;
			double Goku_height = Goku.getBoundsInLocal().getHeight()/2;

			if 		((Goku_midx - Goku_width) <= (dball_midx + dball_width) &&
					(Goku_midy - Goku_height) <= (dball_midy + dball_height) &&
					(Goku_midx + Goku_width) >= (dball_midx - dball_width) &&
					(Goku_midy + Goku_height) >= (dball_midy - dball_height))
			{
				//				System.out.println("DBall Working");
				//pellet.remove(i);

				map.getChildren().remove(dball.get(i));
			}

			if(map.getChildren().indexOf(dball.get(i)) == -1) {			//PowerUp based on number of Dragon
				PowerUp_Total++;												//Balls collected
				//				System.out.println(PowerUp);
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
			//			System.out.println("in 1");
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
			//			System.out.println("in 2");

		}
	}

	private void Score(int value) {
		map.getChildren().remove(ScoreT);
		//			System.out.println(Score);
		Score_Pellets = 50*value;
		//			System.out.println(Score_Enemies);
		Score_Total = Score_Pellets + Score_Enemies;
		ScoreT = new Text(910,240,Integer.toString(Score_Total));
		ScoreT.setFont(Font.font("ARIAL", 30));
		ScoreT.setFill(Color.WHITE);
		map.getChildren().add(ScoreT);
	}



	private void LifeChange() {
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
		//		System.out.println(map.getChildren().indexOf(Bean_2));
		String musicFile = "Resources/Collision_Noise.wav";
		//		Music Site: http://soundbible.com/1945-Smashing.html


		if(PowerUp==0) {				//If in regular form

			if(map.getChildren().indexOf(Bean_2)==-1 && map.getChildren().indexOf(Bean_1)==-1) {

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
					Media sound = new Media(new File(musicFile).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(sound);
					mediaPlayer.setCycleCount(1);
					mediaPlayer.play();
					Platform.exit();
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
					North_1 = false;South_1 = false;East_1 = false;West_1 = false;	//Set direction of user & AI
					North_2 = false;South_2 = false;East_2 = false;West_2 = false;	//to 0
					
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
					Toppo_Secondary_Count = 0;
					Hit_Count = 0;
					North_1 = false;South_1 = false;East_1 = false;West_1 = false;	
					North_2 = false;South_2 = false;East_2 = false;West_2 = false;
				}
			}
		}

		else {				//PowerUp active

			if 		((Goku_midx - Goku_width) <= (Kefla_midx + AI_width) &&
					(Goku_midy - Goku_height) <= (Kefla_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Kefla_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Kefla_midy - AI_height)){

				Kefla.relocate(640,453);
				Score_Enemies+=150;							//Plus 100 point for each enemy you defeat
			}


			else if ((Goku_midx - Goku_width) <= (Jiren_midx + AI_width) &&
					(Goku_midy - Goku_height) <= (Jiren_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Jiren_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Jiren_midy - AI_height)){

				Jiren.relocate(200,197);
				North_2 = false;South_2 = false;East_2 = false;West_2 = false;
				Score_Enemies+=150;
			}

			else if ((Goku_midx - Goku_width) <= (Toppo_midx + AI_width) &&
					(Goku_midy - Goku_height) <= (Toppo_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Toppo_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Toppo_midy - AI_height)){

				//				if(Toppo_Count_Respawn == 0) {
				//					Toppo.relocate(805,300);
				//					Toppo_Count_Respawn = 1;
				//					Toppo_Count = 0;
				//				}
				//				else {
				//					Toppo.relocate(35, 300);
				//					Toppo_Count_Respawn = 0;
				//					Toppo_Count = 2;
				//				}
				Toppo.relocate(421,295);
				Toppo_Secondary_Count = 0;
				Score_Enemies+=150;
			}

			else if ((Goku_midx - Goku_width) <= (Hit_midx + AI_width) &&
					(Goku_midy - Goku_height) <= (Hit_midy + AI_height) &&
					(Goku_midx + Goku_width) >= (Hit_midx - AI_width) &&
					(Goku_midy + Goku_height) >= (Hit_midy - AI_height)){

				Hit.relocate(421,390);
				Score_Enemies+=150;
			}

		}
	}

	private void GameWin() {
		int pellet_count = 0;
		for(int i=0;i<pellet.size();i++) {
			if(map.getChildren().indexOf(pellet.get(i))==-1) {
				pellet_count++;
			}
		}
		if(pellet_count == pellet.size()) {
			Platform.exit();
			System.out.println("Congrats You Won!");
		}
	}

	private void Clock_Timer(){
		timeline_1 = new Timeline();
		timeline_1.setCycleCount(Timeline.INDEFINITE);
		timeline_1.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				timeSeconds--;
				min = timeSeconds/60;
				sec = timeSeconds%60;
				TimeValue.setText(min.toString() + ":" + sec.toString());	
				LifeAddition();				//USed to generate life after 60 seconds
			}
		}));

		timeline_1.playFromStart();
	}

	private void LifeAddition() {
		if(sec == 0 && min == 1) {
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

}