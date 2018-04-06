package PACMAN;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    
    	private static final String BRICK_WALL_LOC =
		"https://images.pexels.com/photos/289633/pexels-photo-289633.jpeg?cs=srgb&dl=background-brick-brick-wall-289633.jpg&fm=jpg";

    private Image GokuImage, JirenImage, KeflaImage, ToppoImage, WallImage;
    private Node  Goku, Jiren, Kefla, Toppo, Wall;
    

    boolean North, South, East, West;

    @Override
    public void start(Stage stage) throws Exception {
    	GokuImage = new Image("file:Goku.jpg",75,75,true,true);
    	JirenImage = new Image("file:Baby_Vegeta.jpg",75,75,true,true);
    	KeflaImage = new Image("file:Baby_Vegeta.jpg",75,75,true,true);
    	ToppoImage = new Image("file:Baby_Vegeta.jpg",75,75,true,true);
    	WallImage = new Image(BRICK_WALL_LOC,30,30,true,true);
    	
    	Goku = new ImageView(GokuImage);
    	Jiren = new ImageView(JirenImage);
    	Kefla = new ImageView(KeflaImage);
    	Toppo = new ImageView(ToppoImage);
    	

        Group map = new Group(Goku);
 //       Group map2 = new Group(Jiren);

        moveGokuTo(W / 2, H / 2);			//Starting position
        map.getChildren().add(Jiren);
        map.getChildren().add(Kefla);
        map.getChildren().add(Toppo);
        
        Jiren.relocate(300,300);
        Kefla.relocate(600, 600);
        Toppo.relocate(100,100);

        Scene scene = new Scene(map, W, H, Color.TURQUOISE);
    
//
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
//                switch (event.getCode()) {
//                    case W: North = true; break;
//                    case D: East  = true; break;
//                    case S: South = true; break;
//                    case A: West  = true; break;
//
// //                   case SHIFT: running = true; break;
//				default:
//					break;
//                }
            	if(event.getCode() == KeyCode.W) {
            		North = true; East = false; South = false; West = false;
            	}
            	if(event.getCode() == KeyCode.A) {
            		West = true; North = false; South = false; East = false;
            	}
            	if(event.getCode() == KeyCode.S) {
            		South = true; East = false; North = false; West = false;
            	}
            	if(event.getCode() == KeyCode.D) {
            		East = true; North = false; South = false; West = false;
            	}
            }
        });

//        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                switch (event.getCode()) {
//                    case W: North = false; break;
//                    case D: East  = false; break;
//                    case S: South = false; break;
//                    case A: West  = false; break;
//
// //                   case SHIFT: running = false; break;
//				default:
//					break;
//                }
//            }
//        });

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
//                GameOver();
            }
        };
        Movement.start();
    }

    private void moveGokuBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double Goku_Width = Goku.getBoundsInLocal().getWidth()  / 2;			//Mid point
        final double Goku_Height = Goku.getBoundsInLocal().getHeight() / 2;

        double x = Goku_Width + Goku.getLayoutX() + dx;
        double y = Goku_Height+ Goku.getLayoutY() + dy;
        System.out.println(Goku.getLayoutY());
        moveGokuTo(x, y);						//Moves mid point to this point
    }

    private void moveGokuTo(double x, double y) {
        final double Goku_Width = Goku.getBoundsInLocal().getWidth() / 2;
        final double Goku_Height = Goku.getBoundsInLocal().getHeight() / 2;

        if (x - Goku_Width >= 0 &&			//Stops character from going out of bounds
            x + Goku_Width <= W &&
            y - Goku_Height >= 0 &&
            y + Goku_Height <= H) {
            Goku.relocate(x - Goku_Width , y - Goku_Height);
        }
        
    }
    
    private void moveJiren() {
    	int gx = 0;
//    	int gy = 0;
//    	int count = 0;
    	int count = 0;
    	
    	final double Jiren_Width = Jiren.getBoundsInLocal().getWidth() / 2;
 //       final double Jiren_height = Jiren.getBoundsInLocal().getHeight() / 2;
    	
    	double jx = Jiren.getLayoutX() + Jiren_Width + gx;
//    	double jy = Jiren.getLayoutY() + gy;
    	
//    	if(jx - Jiren_Width == 100) {
//    		gx += 2;
//    		count = 1;
//    	}
//    	else if((jx + Jiren_Width) == (W-100)) {
//    		gx -= 2;
//    		count = 0;
//    	}
//    	
//    	if(count == 0) {
//    		gx = -2;
//    	}
//    	else {
//    		gx = 2;
//    	}
    		
    		gx = -4;
    	
    	
    	
    	jx = Jiren.getLayoutX() + Jiren_Width + gx;
    	
    	if(jx-Jiren_Width == -80) {
    		Jiren.relocate(W+4,300);
    	}
    	else {
    		Jiren.relocate(jx-Jiren_Width, 300);
    	}
    	
    	System.out.println(jx-Jiren_Width + " " + (0-Jiren_Width));
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
    
    
//    private void Maze() {
//    	Rectangle R1 = new Rectangle(20,20);
//    	
//    	
//    }
    
//    private void GameOver() {
////            if (Goku.getLayoutX() == Jiren.getLayoutX()) {
////          	  Platform.exit();
////            }
//    		if (Goku.getLayoutBounds() == (Kefla.getLayoutBounds())){
//    			Platform.exit();
//    		}
//    }
//    
    
    
    

    public static void main(String[] args) { launch(args); }
}