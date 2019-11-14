import java.awt.Color;
import java.awt.Image;

import objectdraw.*;
public class Laser extends ActiveObject {
	FilledRect laser; 
	private final double laser_len = 10; 
	private final double laser_width = 5; 
	private double speed; 
	DrawingCanvas c; 
	private Fleet fleet; 
	private Ship spaceship; 
	private boolean fromShip;
	private boolean laserRemoved = false;
		
	public Laser(double start_x, double start_y, boolean fromShip, Fleet f, DrawingCanvas canvas) {
		//laser = new VisibleImage(i, start_x, start_y, laser_width,laser_len,canvas);
		laser = new FilledRect(start_x,start_y,laser_width,laser_len,canvas); 
		//spaceship = s; 
		fleet = f;
		this.fromShip = fromShip;
		if (fromShip) {
			laser.setColor(Color.WHITE);
			speed = -4;
		} 
		else {
			laser.setColor(Color.WHITE);
			speed = 4;
		}
		c = canvas; 
		start(); 
	}

	public void kill() {
		laser.removeFromCanvas(); 
	}
	
	public void run() {
		while((laser.getX() >= 0 && laser.getX() <= c.getWidth()) && (laser.getY() <= c.getHeight() && laser.getY() >= 0)) {
			laser.move(0,speed); 
			
			//check to see if laser overlaps with Spaceship or Alien
			//Which means laser has to have the Visible Image of all aliens along with the ship to check. 
			//fleet.checkLaser(laser); // Overlaps   
						
			//Laser will kill both spaceship and alien.

			if (fleet.checkLaser(laser)) {
				laser.removeFromCanvas();
				laserRemoved = true;
				break;
			}
			
			pause(20); 
		}
		if (!laserRemoved) laser.removeFromCanvas(); 
	}


}
