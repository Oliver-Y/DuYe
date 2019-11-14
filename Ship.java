import objectdraw.*;
import java.awt.Image;

public class Ship extends ActiveObject {
	//Constructor should take an Image and starting position 
	VisibleImage ship; 
	DrawingCanvas c; 
	private static final int SHIP_WIDTH = 50;
	private static final int SHIP_HEIGHT = 50;
	private final int step = 5; 
	public boolean keyPressedR = false; 
	public boolean keyPressedL = false; 
	public boolean keyPressedS = false; 
	

	
	public Ship(Image i, int WIDTH, int HEIGHT,DrawingCanvas canvas) {
		ship = new VisibleImage(i, WIDTH/2-SHIP_WIDTH/2, HEIGHT/2 + SHIP_HEIGHT*5,SHIP_WIDTH, SHIP_HEIGHT, canvas);
		c = canvas;
		start();
	}
	public void set_R(boolean b) {
		keyPressedR = b;
	}
	public void set_L(boolean b) {
		keyPressedL = b;
	}

	public void set_S(boolean b) {
		keyPressedS = b;
	}
	
	public void moveRight() {
		ship.move(step,0);
	}
	public void moveLeft() {
		ship.move(-step,0); 
	}
	public void shoot() {
		new Laser(ship.getX()+SHIP_WIDTH/2,ship.getY()+ 5,c);
	}
	public void run() {
		while(true) {

			if(keyPressedR) {
				moveRight();
			}
			else if (keyPressedL) {
				moveLeft(); 
			}
			/*else if (keyPressedS) {
				shoot(); 
			}*/
			pause(10); 
		}
		
	}
}
