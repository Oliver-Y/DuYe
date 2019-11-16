import java.awt.Color;

import objectdraw.*;
public class Laser extends ActiveObject {
	FilledRect laser; 
	private static final int LASER_LENGTH = 9; 
	private static final int LASER_WIDTH = 3; 
	private double speed; 
	DrawingCanvas c; 
	private Fleet fleet; 
	private Ship spaceship; 
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
	
	public boolean fromShip() {
		return fromShip; 
	}
	

	public void kill() {
		laser.removeFromCanvas(); 
	}
	public FilledRect getPic() {
		return laser; 
	}
	
	
	public void run() {
		while((laser.getX() >= 0 && laser.getX() <= c.getWidth()) && (laser.getY() <= c.getHeight() && laser.getY() >= 0)) {
			laser.move(0,speed); 
			//check to see if laser overlaps with Spaceship or Alien
			//Which means laser has to have the Visible Image of all aliens along with the ship to check. 
			//fleet.checkLaser(laser); // Overlaps   
			//Laser will kill both spaceship and alien.
			if (fleet.checkLaser(this)) {
				laser.removeFromCanvas();
				fleet.addScore(10); 
				laserRemoved = true;
				break;
			}
			if(spaceship.checkLaser(this)) {
				laser.removeFromCanvas();
				laserRemoved = true; 
				break; 
			}
			pause(10); 
		}
		if (!laserRemoved) laser.removeFromCanvas(); 
	}


}
