package PACMAN;

import java.awt.event.ActionEvent;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Hold down an arrow key to have your character move around the screen.
 * Hold down the shift key to have the character run.
 */
public class Main extends Application {

    private static final double W = 1024, H = 768;
    

    private static final String GOKU_IMAGE_LOC =
    	//	"/home/rohand97/Pictures";
    		"https://img00.deviantart.net/3cd7/i/2016/222/a/6/goku_by_bthrgking-dadbnvv.jpg";
    
    private static final String JIREN_IMAGE_LOC =
        	//	"/home/rohand97/Pictures";
        		"https://upload.wikimedia.org/wikipedia/en/7/7c/Vegetagt7.jpg";

    private Image GokuImage, JirenImage, KeflaImage;
    private Node  Goku, Jiren, Kefla;
    

    boolean North, South, East, West;

    @Override
    public void start(Stage stage) throws Exception {
    	GokuImage = new Image(GOKU_IMAGE_LOC,75,75,true,true);
    	JirenImage = new Image(JIREN_IMAGE_LOC,75,75,true,true);
    	KeflaImage = new Image(JIREN_IMAGE_LOC,75,75,true,true);
    	Goku = new ImageView(GokuImage);
    	Jiren = new ImageView(JirenImage);
    	Kefla = new ImageView(KeflaImage);

        Group map = new Group(Goku);
 //       Group map2 = new Group(Jiren);

        moveGokuTo(W / 2, H / 2);			//Starting position
        map.getChildren().add(Jiren);
        map.getChildren().add(Kefla);
        Jiren.relocate(300,300);
        Kefla.relocate(600, 600);

        Scene scene = new Scene(map, W, H, Color.ORANGERED);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W: North = true; break;
                    case D: East  = true; break;
                    case S: South = true; break;
                    case A: West  = true; break;

 //                   case SHIFT: running = true; break;
				default:
					break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W: North = false; break;
                    case D:	East  = false; break;
                    case S: South = false; break;
                    case A: West  = false; break;

 //                   case SHIFT: running = false; break;
				default:
					break;
                }
            }
            
           
//            public void handle(ActionEvent arg0) {
//              if (Goku.getLayoutX() < Jiren.getLayoutX()) {
//            	  Platform.exit();
//              }
//            }
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
                else if (South){
                	dy += 2;
                }
                else if (East){
                	dx += 2;
                }
                else if (West){
                	dx -= 2;
                }
//                if (running) { dx *= 3; dy *= 3; }

                moveGokuBy(dx, dy);
                moveJiren();
            }
        };
        Movement.start();
    }

    private void moveGokuBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = Goku.getBoundsInLocal().getWidth()  / 2;			//Mid point
        final double cy = Goku.getBoundsInLocal().getHeight() / 2;

        double x = cx + Goku.getLayoutX() + dx;
        double y = cy+ Goku.getLayoutY() + dy;
        System.out.println(Goku.getLayoutY());
        moveGokuTo(x, y);						//Moves mid point to this point
    }

    private void moveGokuTo(double x, double y) {
        final double cx = Goku.getBoundsInLocal().getWidth() / 2;
        final double cy = Goku.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&			//Stops character from going out of bounds
            x + cx <= W &&
            y - cy >= 0 &&
            y + cy <= H) {
            Goku.relocate(x - cx , y - cy);
        }
        
    }
    
    private void moveJiren() {
    	int gx = 0;
//    	int gy = 0;
//    	int count = 0;
    	int count = 0;
    	
    	final double Jiren_width = Jiren.getBoundsInLocal().getWidth() / 2;
 //       final double Jiren_height = Jiren.getBoundsInLocal().getHeight() / 2;
    	
    	double jx = Jiren.getLayoutX() + Jiren_width + gx;
//    	double jy = Jiren.getLayoutY() + gy;
    	
    	if(jx - Jiren_width == 100) {
    		gx += 2;
    		count = 1;
    	}
    	else if((jx + Jiren_width) == (W-100)) {
    		gx -= 2;
    		count = 0;
    	}
    	
    	if(count == 0) {
    		gx = -2;
    	}
    	else {
    		gx = 2;
    	}
    	
    	
    	jx = Jiren.getLayoutX() + Jiren_width + gx;
    	
    	Jiren.relocate(jx-Jiren_width, 300);
    }
    
    private void GameOver() {
  //  	if
    }

    public static void main(String[] args) { launch(args); }
}