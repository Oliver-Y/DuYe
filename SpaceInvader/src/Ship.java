import objectdraw.*;
import java.awt.Image;
import java.util.ArrayList; 


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
	//Keep track of all lasers for collisions
	private ArrayList<Laser> lasers;
	//Keep track of all gnozz dawgs on the field
	private ArrayList<wildGnozz> gnozz;

	//Constructor should take an Image and starting position 
	public Ship(Image i, int WIDTH, int HEIGHT,Fleet f,DrawingCanvas canvas,SpaceInvaders w) {
		ship = new VisibleImage(i, WIDTH/2-SHIP_WIDTH/2, HEIGHT/2 + SHIP_HEIGHT*5,SHIP_WIDTH, SHIP_HEIGHT, canvas);
		c = canvas;
		fleet = f;
		lasers = new ArrayList<Laser>(); 
		gnozz = new ArrayList<wildGnozz>(); 
		window = w;
		start();
	}
	//Gnozzdawg helpers
	public void addGnozz(wildGnozz g) {
		if(gnozz.size() <= 10) {
			gnozz.add(g); 
		}
	}
	public void removeGnozz(wildGnozz g) {
		gnozz.remove(g);
	}
	//Laser ArrayList for collisions
	public ArrayList<Laser> getLasers(){
		return lasers;
	}
	//Movement flags for windo controller
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
	//Shotting Mekanism
	public void shoot() {
		Laser temp = new Laser(ship.getX()+SHIP_WIDTH/2,ship.getY()+ 5, true, fleet,this, c,gnozz);
		lasers.add(temp); 
	}
	//Clearing the ship
	public void clear() {
		try {
			ship.removeFromCanvas(); 
		}
		catch(IllegalStateException e){
			
		}
	}
	//Method for checking if the ship has collided with a laser
	public boolean checkLaser(Laser s) {
		if(ship.overlaps(s.getPic()) && !s.fromShip()) {
			ship.removeFromCanvas();
			window.gameOver();
			return true; 
		}
		return false; 
	}
	//Helper method for keeping track of fleet
	public void addFleet(Fleet f) {
		fleet = f;
	}
	
	public void run() {
		pause(1000); 
		while(true) {
			//Smooth motion only happens in run method of ship, not in key pressed
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
