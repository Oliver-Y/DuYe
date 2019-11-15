import objectdraw.*;
import java.awt.Image;

public class Ship extends ActiveObject {
	//initialize variables
	VisibleImage ship; 
	DrawingCanvas c; 
	Fleet fleet;
	private static final int SHIP_WIDTH = 50;
	private static final int SHIP_HEIGHT = 50;
	private static final int STEP = 5; 
	public boolean keyPressedR = false; 
	public boolean keyPressedL = false; 
	public boolean keyPressedS = false; 
	private SpaceInvaders window; 

	//Constructor should take an Image and starting position 
	public Ship(Image i, int WIDTH, int HEIGHT,Fleet f,DrawingCanvas canvas,SpaceInvaders w) {
		ship = new VisibleImage(i, WIDTH/2-SHIP_WIDTH/2, HEIGHT/2 + SHIP_HEIGHT*5,SHIP_WIDTH, SHIP_HEIGHT, canvas);
		c = canvas;
		fleet = f;
		window = w;
		start();
	}
	public void set_R(boolean b) {
		keyPressedR = b;
	}
	public void set_L(boolean b) {
		keyPressedL = b;
	}
	public void moveRight() {
		ship.move(STEP,0);
	}
	public void moveLeft() {
		ship.move(-STEP,0); 
	}
	public void shoot() {
		new Laser(ship.getX()+SHIP_WIDTH/2,ship.getY()+ 5, true, fleet,this, c);
	}
	
	public void clear() {
		try {
			ship.removeFromCanvas(); 
		}
		catch(IllegalStateException e){
			
		}
	}
	
	public boolean checkLaser(Laser s) {
		if(ship.overlaps(s.getPic()) && !s.fromShip()) {
			ship.removeFromCanvas();
			window.gameOver();
			return true; 
		}
		return false; 
	}
	
	public void addFleet(Fleet f) {
		fleet = f;
	}
	
	public void run() {
		while(true) {

			if(keyPressedR && ship.getX() < 800-SHIP_WIDTH) {
				moveRight();
			}
			else if (keyPressedL && ship.getX() > 0) {
				moveLeft(); 
			}

			pause(10); 
		}
		
	}
}
