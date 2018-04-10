package PACMAN;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.animation.Timeline;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import javafx.util.Duration;

/**
 * Hold down an arrow key to have your character move around the screen.
 * Hold down the shift key to have the character run.
 */
public class Main extends Application {

	private static final double W = 1024, H = 768;


	//    private static final String GOKU_IMAGE_LOC =
	//    	//	"/home/rohand97/Pictures";
	//    		"https://img00.deviantart.net/3cd7/i/2016/222/a/6/goku_by_bthrgking-dadbnvv.jpg";
	//    
	//    private static final String JIREN_IMAGE_LOC =
	//        	//	"/home/rohand97/Pictures";
	//        		"https://upload.wikimedia.org/wikipedia/en/7/7c/Vegetagt7.jpg";

	//  private static final String RAMEN_BOWL_LOC =
	//	https://cdn.pixabay.com/photo/2014/04/02/10/52/rice-304804_960_720.png;
	
	//private static final String DRAGON_BALL_LOC = 
	//https://upload.wikimedia.org/wikipedia/commons/d/d8/Dragonballstar.png;

	

	private Image GokuImage, JirenImage, KeflaImage, ToppoImage;
	private Node  Goku, Jiren, Kefla, Toppo;
	private Text Score,Time,Lives;
	//    Rectangle rectangle = new Rectangle(100,100,32,32);
//	int[] Maze_Array;
	Group map = new Group();

	private ArrayList<Double> wall_x_center = new ArrayList<Double>();
	private ArrayList<Double> wall_y_center = new ArrayList<Double>();
	private ArrayList<Double> pellet_x_center = new ArrayList<Double>();
	private ArrayList<Double> pellet_y_center = new ArrayList<Double>();
	private ArrayList<Double> dball_x_center = new ArrayList<Double>();
	private ArrayList<Double> dball_y_center = new ArrayList<Double>();
//	private Double[] wall_X_center;
//	private Double[] wall_Y_center;

	boolean North, South, East, West;
	
	
    private static final Integer STARTTIME = 15;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

	@Override
	public void start(Stage stage) throws Exception {
		GokuImage = new Image("file:Goku.jpg",22,22,false,false);
		JirenImage = new Image("file:Baby_Vegeta.jpg",22,22,true,true);
		KeflaImage = new Image("file:Baby_Vegeta.jpg",22,22,true,true);
		ToppoImage = new Image("file:Baby_Vegeta.jpg",22,22,true,true);
//   	WallImage = new Image(BRICK_WALL_LOC,30,30,true,true);

		Goku = new ImageView(GokuImage);
		Jiren = new ImageView(JirenImage);
		Kefla = new ImageView(KeflaImage);
		Toppo = new ImageView(ToppoImage);
		
		Score = new Text(900, 192, "Score:");
		Score.setFont(Font.font("ARIAL", 30));
		Score.setFill(Color.WHITE);
		Score.setUnderline(true);
		
		Time = new Text(900, 384, "Time:");
		Time .setFont(Font.font("ARIAL", 30));
		Time .setFill(Color.WHITE);
		Time .setUnderline(true);
		
//        timerLabel.textProperty().bind(timeSeconds.asString());
//        timerLabel.setTextFill(Color.RED);
//        timerLabel.setStyle("-fx-font-size: 4em;");
		
		
		
		
		Lives = new Text(900, 576, "Lives:");
		Lives.setFont(Font.font("ARIAL", 30));
		Lives.setFill(Color.WHITE);
		Lives.setUnderline(true);
		
		
		


		Scene scene = new Scene(map, W, H, Color.BLACK);      
		Maze();
//		moveGokuTo(W / 2, H / 2);			//Starting position

		map.getChildren().add(Jiren);
		map.getChildren().add(Kefla);
		map.getChildren().add(Toppo);
		map.getChildren().add(Goku);
		map.getChildren().add(Score);
		map.getChildren().add(Time);
		map.getChildren().add(Lives);


		Jiren.relocate(300,300);
		Kefla.relocate(600,600);
		Toppo.relocate(100,100);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.UP) {
					North = true; East = false; South = false; West = false;
				}
				if(event.getCode() == KeyCode.LEFT) {
					West = true; North = false; South = false; East = false;
				}
				if(event.getCode() == KeyCode.DOWN) {
					South = true; East = false; North = false; West = false;
				}
				if(event.getCode() == KeyCode.RIGHT) {
					East = true; North = false; South = false; West = false;
				}
			}
		});


		stage.setScene(scene);								//Show the scene
		stage.show();



		AnimationTimer Movement = new AnimationTimer() {
			@Override
			public void handle(long now) {
				int dx = 0, dy = 0;

				if (North){ 
					dy -= 2;					//Speed of movement
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
				//                if (running) { dx *= 3; dy *= 3; }



				moveGokuBy(dx, dy);
				moveJiren();
				moveKefla();
				moveToppo();
				
			}
		};
		Movement.start();
		
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME+1),
                new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
        
        
	}

	private void moveGokuBy(int dx, int dy) {
		if (dx == 0 && dy == 0) return;

		final double Goku_Width = Goku.getBoundsInLocal().getWidth()  / 2;			//Mid point
		final double Goku_Height = Goku.getBoundsInLocal().getHeight() / 2;

		double x = Goku_Width + Goku.getLayoutX() + dx;
		double y = Goku_Height+ Goku.getLayoutY() + dy;
		//       System.out.println(Goku.getLayoutY());
		moveGokuTo(x, y);						//Moves mid point to this point
	}

	private void moveGokuTo(double x, double y) {
		final double Goku_Width = Goku.getBoundsInLocal().getWidth() / 2;
		final double Goku_Height = Goku.getBoundsInLocal().getHeight() / 2;

		if (x - Goku_Width >= 0 &&			//Stops character from going out of bounds
				x + Goku_Width <= W &&
				y - Goku_Height >= 0 &&
				y + Goku_Height <= H) {

			if(Collision(x,y) == true) {
				Goku.relocate(x - Goku_Width , y - Goku_Height);
			}
			
			Pellets();

		}

	}

	private void moveJiren() {
		int gx = 0;

		final double Jiren_Width = Jiren.getBoundsInLocal().getWidth() / 2;
		//       final double Jiren_height = Jiren.getBoundsInLocal().getHeight() / 2;

		double jx = Jiren.getLayoutX() + Jiren_Width + gx;
		gx = -4;
		
		jx = Jiren.getLayoutX() + Jiren_Width + gx;

		if(jx-Jiren_Width == -80) {
			Jiren.relocate(W+4,300);
		}
		else {
			Jiren.relocate(jx-Jiren_Width, 300);
		}

		//    	System.out.println(jx-Jiren_Width + " " + (0-Jiren_Width));
	}

	private void moveKefla() {			//Attacks User

		int px = 0;
		int py = 0;

		final double Kefla_Width = Kefla.getBoundsInLocal().getWidth() / 2;
		final double Kefla_Height = Kefla.getBoundsInLocal().getHeight() / 2;

		double DistanceX = Goku.getLayoutX() + Goku.getBoundsInLocal().getWidth() / 2 - 
				(Kefla.getLayoutX() + Kefla_Width);

		double DistanceY = Goku.getLayoutY() + Goku.getBoundsInLocal().getHeight() / 2 - 
				(Kefla.getLayoutY() + Kefla_Height);

		if(DistanceX > 0) {
			px = 1;
		}
		else {
			px = -1;
		}

		if(DistanceY > 0) {
			py = 1;
		}
		else {
			py = -1;
		}

		double kx = Kefla.getLayoutX() + Kefla_Width + px;
		double ky = Kefla.getLayoutY() + Kefla_Height + py;

		Kefla.relocate(kx-Kefla_Width, ky-Kefla_Height);
	}


	private void moveToppo() {			//Attacks User

		int px = 0;
		int py = 0;

		final double Toppo_Width = Toppo.getBoundsInLocal().getWidth() / 2;
		final double Toppo_Height = Toppo.getBoundsInLocal().getHeight() / 2;

		double DistanceX = Goku.getLayoutX() + Goku.getBoundsInLocal().getWidth() / 2 - 
				(Toppo.getLayoutX() + Toppo_Width + 15);

		double DistanceY = Goku.getLayoutY() + Goku.getBoundsInLocal().getHeight() / 2 - 
				(Toppo.getLayoutY() + Toppo_Height + 15);

		if(DistanceX > 0) {
			px = 1;
		}
		else {
			px = -1;
		}

		if(DistanceY > 0) {
			py = 1;
		}
		else {
			py = -1;
		}

		double kx = Toppo.getLayoutX() + Toppo_Width + px;
		double ky = Toppo.getLayoutY() + Toppo_Height + py;

		Toppo.relocate(kx-Toppo_Width, ky-Toppo_Height);
	}


	private void Maze() throws FileNotFoundException{
		int[] Array = {
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,
				1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,3,1,0,0,0,0,0,
				1,2,1,0,0,0,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1,0,0,0,1,2,1,0,0,0,0,0,
				1,2,1,0,0,0,1,3,1,1,1,1,2,1,2,1,1,1,1,2,1,0,0,0,1,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,2,2,2,1,1,2,1,2,1,1,2,2,2,1,1,1,1,1,2,1,0,0,0,0,0,
				1,2,2,2,2,2,2,2,2,2,2,1,2,1,2,1,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,1,2,2,2,0,2,2,2,3,2,2,2,1,1,1,1,1,1,2,1,0,0,0,0,0,
				1,2,1,0,0,0,0,1,2,1,1,1,1,1,1,1,1,1,2,1,0,0,0,0,1,2,1,0,0,0,0,0,
				1,2,1,0,0,0,0,1,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,1,2,1,0,0,0,0,0,
				1,2,1,0,0,0,0,1,2,1,1,1,1,1,1,1,1,1,2,1,0,0,0,0,1,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,1,2,1,0,0,0,0,0,0,0,1,2,1,1,1,1,1,1,2,1,0,0,0,0,0,
				1,2,3,2,2,2,2,2,2,1,0,0,0,0,0,0,0,1,2,2,2,3,2,2,2,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,1,2,1,0,0,0,0,0,0,0,1,2,1,1,1,1,1,1,2,1,0,0,0,0,0,
				1,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,1,0,0,0,0,0,
				1,2,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,2,1,0,0,0,0,0,
				1,2,2,2,2,2,2,1,2,2,1,1,1,1,1,1,1,2,2,1,2,2,2,2,2,2,1,0,0,0,0,0,
				1,2,1,1,1,1,2,1,2,2,1,0,0,0,0,0,1,2,2,1,2,1,1,1,1,2,1,0,0,0,0,0,
				1,2,1,0,0,1,2,1,2,2,1,0,0,0,0,0,1,2,2,1,2,1,0,0,1,3,1,0,0,0,0,0,
				1,2,1,0,0,1,2,1,2,3,1,1,1,1,1,1,1,2,2,1,2,1,0,0,1,2,1,0,0,0,0,0,
				1,2,1,0,0,1,2,1,2,2,2,2,2,5,2,2,2,2,2,1,2,1,0,0,1,2,1,0,0,0,0,0,
				1,2,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,2,1,0,0,0,0,0,
				1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0
		};

		int j=0;
//		int x=0;
		
		for(int i=0;i<Array.length;i++) {
			j = i/32;
			if(Array[i]==1) {
				Rectangle vectangle = new Rectangle(32*(i%32),32*j,32,32);
				vectangle.setFill(Color.SKYBLUE);
				wall_x_center.add(vectangle.getX() + (vectangle.getBoundsInLocal().getWidth()/2));
				wall_y_center.add(vectangle.getY() + (vectangle.getBoundsInLocal().getHeight()/2));
//				wall_X_center[x] = vectangle.getX() + (vectangle.getBoundsInLocal().getWidth()/2);
//				wall_Y_center[x] = vectangle.getY() + (vectangle.getBoundsInLocal().getHeight()/2);
//				x++;
				map.getChildren().add(vectangle);
			}
			else if(Array[i]==2) {
				Image RamenImage = new Image("file:Ramen_Bowl.png",18,18,false,false);
				ImageView  Ramen = new ImageView();
				Ramen.setImage(RamenImage);
				Ramen.relocate((32*(i%32))+7,(32*j)+7);		//Plus 7 to compensate for smaller size ((32-18)/2=7)
				pellet_x_center.add(Ramen.getX()+Ramen.getBoundsInLocal().getWidth());
				pellet_y_center.add(Ramen.getY()+Ramen.getBoundsInLocal().getHeight());
				map.getChildren().add(Ramen);
			}
			else if(Array[i]==3) {
				Image DragonBallImage = new Image("file:DragonBall_1star.png",20,20,false,false);
				ImageView DragonBall = new ImageView();
				DragonBall.setImage(DragonBallImage);
				DragonBall.relocate((32*(i%32))+6,(32*j)+6);
				dball_x_center.add(DragonBall.getX()+DragonBall.getBoundsInLocal().getWidth());
				dball_y_center.add(DragonBall.getY()+DragonBall.getBoundsInLocal().getHeight());
				map.getChildren().add(DragonBall);
			}
			else if(Array[i]==5) {
				Goku.relocate((32*(i%32))+5,(32*j)+5);			//Starting position ((32-22)/2=5)
			}
		}
		////		
		//		Scanner Import_Maze = new Scanner(new File("Maze_CSV.csv"));
		//        Import_Maze.useDelimiter(",");
		//        int i = 0;
		//        
		//        while(Import_Maze.hasNext()){
		//        	Maze_Array[i] =  Integer.parseInt(Import_Maze.next());
		//        	i++;
		//        }
		//        
		//        Import_Maze.close();
		//
		//        for(int x=0;x<=Maze_Array.length;x++) {
		//        	System.out.println(Maze_Array[x]);
		//         }
		//        
		//
		////        
		//       		
		//	}

		//    private void GameOver() {
		////            if (Goku.getLayoutX() == Jiren.getLayoutX()) {
		////          	  Platform.exit();
		////            }
		//    		if (Goku.getLayoutBounds() == (Kefla.getLayoutBounds())){
		//    			Platform.exit();
		//    		}
	}



	private boolean Collision (double x , double y) {

		for(int i=0;i<wall_x_center.size();i++) {
			double rectangle_midx = wall_x_center.get(i);
//			System.out.println(wall_x_center.size());
//			System.out.println(wall_x_center.get(i));
			double rectangle_midy = wall_y_center.get(i);
			double Goku_midx = x;
			double Goku_midy = y;
			double rectangle_width = 16;
			double rectangle_height = 16;
			double Goku_width = Goku.getBoundsInLocal().getWidth()/2;
			double Goku_height = Goku.getBoundsInLocal().getHeight()/2;

			if 		((Goku_midx - Goku_width) <= (rectangle_midx + rectangle_width) && 
					(Goku_midy - Goku_height) <= (rectangle_midy + rectangle_height) &&
					(Goku_midx + Goku_width) >= (rectangle_midx - rectangle_width) && 
					(Goku_midy + Goku_height) >= (rectangle_midy - rectangle_height))
			{
				return false;
			}
		}
		return true;
	}
	
	
	private void Pellets() {
		
		for(int i=0;i<pellet_x_center.size();i++) {
			double pellet_midx = pellet_x_center.get(i);
//			System.out.println(wall_x_center.size());
//			System.out.println(wall_x_center.get(i));
			double pellet_midy = pellet_y_center.get(i);
			double Goku_midx = Goku.getLayoutX() + Goku.getBoundsInLocal().getWidth();
			double Goku_midy = Goku.getLayoutY() + Goku.getBoundsInLocal().getHeight();
			double pellet_width = 9;
			double pellet_height = 9;
			double Goku_width = Goku.getBoundsInLocal().getWidth()/2;
			double Goku_height = Goku.getBoundsInLocal().getHeight()/2;
		
			if 		((Goku_midx - Goku_width) <= (pellet_midx + pellet_width) && 
					(Goku_midy - Goku_height) <= (pellet_midy + pellet_height) &&
					(Goku_midx + Goku_width) >= (pellet_midx - pellet_width) && 
					(Goku_midy + Goku_height) >= (pellet_midy - pellet_height))
			{
				
			}
		}
	}
	

	public static void main(String[] args) { launch(args); }
}