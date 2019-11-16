import java.awt.Image;
import objectdraw.*;


/*
 * If a wild Gnozzdawg is shot it will split into 2. When a Gnozzdawg goes off screen it will dissapear and die.
 * The amount of Gnozzdawgs in a given game is capped at 10 (or else the computer crashes) Gnozzdawgs will destroy/act as 
 * a block for user bullets, but let enemy bullets pass. The movement pattern for gnozzdawgs is random, but will generally stay below
 * the fleet.
 *  
 */


public class wildGnozz extends ActiveObject {
	private VisibleImage gnozz; 
	private DrawingCanvas c;
	private int speedx; 
	private int speedy; 
	private SpaceInvaders controller; 
	private boolean run; 
	private Image storedImage; 
	Ship spaceship; 
	
	public wildGnozz(Image i, SpaceInvaders sc, DrawingCanvas canvas,Ship ship, double x, double y) {
		gnozz = new VisibleImage(i,x,y,50,50,canvas); 
		c = canvas;
		spaceship = ship; 
		controller = sc; 
		storedImage = i; 
		run = true; 
		spaceship.addGnozz(this);
		System.out.println("gets through here first pass"); 
		start();
	}	
	public void clear() {
		try {
			gnozz.removeFromCanvas();
			spaceship.removeGnozz(this);
		}
		catch(IllegalStateException e) {
			
		}
		run = false; 
	}
	//Create 2 more GnozzDawgs and pass it to the ship to keep track
	public boolean checkLaser(Laser s) {
		if(this.gnozz.overlaps(s.getPic()) && s.fromShip()) {
			wildGnozz temp1 = new wildGnozz(storedImage,controller,c,spaceship,gnozz.getX() + 50, gnozz.getY() + 50); 
			wildGnozz temp2 = new wildGnozz(storedImage,controller,c,spaceship,gnozz.getX() - 50, gnozz.getY() - 50);
			spaceship.addGnozz(temp1);
			spaceship.addGnozz(temp2);
			gnozz.removeFromCanvas(); 
			spaceship.removeGnozz(this);
			return true;
		}
	
		return false; 
	}
	
	
	public void run() {
		pause(1000); 
		while((gnozz.getX() >= 0 && gnozz.getX() <= c.getWidth()) && (gnozz.getY() <= c.getHeight() && gnozz.getY() >= 0) && run) {
		speedx = (int)(Math.random() * 150);
		speedy = (int)(Math.random() * 25);
		int dirx = (int)(Math.random() * 2);
		int diry = (int)(Math.random() * 2); 
		if(dirx == 0) speedx = -speedx; 
		if(diry == 0) speedy = -speedy; 
		gnozz.move(-speedx,-speedy);
		pause(500); 
		}
		try {
			gnozz.removeFromCanvas(); 
		}
		catch(IllegalStateException e) {
			
		}
		spaceship.removeGnozz(this);
	}
	
}
