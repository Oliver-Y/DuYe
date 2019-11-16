import java.awt.Color;
import java.util.ArrayList;

import objectdraw.*;
public class Laser extends ActiveObject {
	//Basic instance variables 
	FilledRect laser; 
	private static final int LASER_LENGTH = 9; 
	private static final int LASER_WIDTH = 3; 
	private double speed; 
	//Keep track of other elements to be able to determine collisions
	DrawingCanvas c; 
	private Fleet fleet; 
	private Ship spaceship; 
	private ArrayList <wildGnozz> gnozz; 
	private boolean fromShip;
	private boolean laserRemoved = false;
		
	public Laser(double start_x, double start_y, boolean fromShip, Fleet f, Ship s, DrawingCanvas canvas) {
		laser = new FilledRect(start_x,start_y, LASER_WIDTH, LASER_LENGTH, canvas); 
		fleet = f;
		spaceship = s; 
		this.fromShip = fromShip;
		if (fromShip) {
			laser.setColor(Color.WHITE);
			speed = -3;
		} 
		else {
			laser.setColor(Color.YELLOW);
			speed = 3;
		}
		c = canvas; 
		start(); 
	}
	//2 constructors for special Gnozzdawg lasers from ship --> pure conveiance sake.
	public Laser(double start_x, double start_y, boolean fromShip, Fleet f, Ship s, DrawingCanvas canvas,ArrayList<wildGnozz> g) {
		laser = new FilledRect(start_x,start_y, LASER_WIDTH, LASER_LENGTH, canvas); 
		fleet = f;
		spaceship = s; 
		gnozz = g;
		this.fromShip = fromShip;
		if (fromShip) {
			laser.setColor(Color.WHITE);
			speed = -3;
		} 
		else {
			laser.setColor(Color.YELLOW);
			speed = 3;
		}
		c = canvas; 
		start(); 
	}
	
	//Accessor + Mutator methods for other classes to use
	public boolean fromShip() {
		return fromShip; 
	}
	public void addGnozz(ArrayList<wildGnozz> g) {
		gnozz = g;
	}
	
	public void kill() {
		laser.removeFromCanvas(); 
	}
	public FilledRect getPic() {
		return laser; 
	}
	
	public void setRemoved() {
		laserRemoved = true;
	}
	
	
	
	public void run() {
		//Make sure its within bounds
		while((laser.getX() >= 0 && laser.getX() <= c.getWidth()) && (laser.getY() <= c.getHeight() && laser.getY() >= 0)) {
			laser.move(0,speed); 
			//check to see if laser overlaps with Spaceship or Alien
			//Which means laser has to have the Visible Image of all aliens along with the ship to check. 
			
			//Check for fleet collisions
			if (fleet.checkLaser(this)) {
				laser.removeFromCanvas();
				fleet.addScore(10); 
				spaceship.getLasers().remove(this); 
				laserRemoved = true;
				break;
			}
			//Check for spaceship collisions
			if(spaceship.checkLaser(this)) {
				laser.removeFromCanvas();
				laserRemoved = true; 
				break; 
			}
			//Check for Gnozz collisions
			if(gnozz != null) {
				for(int j = 0; j < gnozz.size(); ++j) {
					if(gnozz.get(j).checkLaser(this)) {
						laser.removeFromCanvas(); 
						laserRemoved = true; 
						break; 
						}
					}
			}

			//Canceling other lasers
			for(int i = 0; i < spaceship.getLasers().size(); ++i) {
				if(this.laser.overlaps(spaceship.getLasers().get(i).getPic()) && this.fromShip != spaceship.getLasers().get(i).fromShip()) {
					spaceship.getLasers().get(i).getPic().removeFromCanvas(); 
					spaceship.getLasers().get(i).setRemoved(); 
					spaceship.getLasers().remove(spaceship.getLasers().get(i)); 
					laser.removeFromCanvas(); 
					laserRemoved = true; 
					break;
				}

			}
			pause(10); 
		}
		//Remove if it hasn't been already
		if (!laserRemoved) {
			laser.removeFromCanvas(); 
		}
	}


}
