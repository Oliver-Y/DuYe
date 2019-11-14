import java.awt.Color;
import java.awt.Image;

import objectdraw.*;
public class Laser extends ActiveObject {
	FilledRect laser; 
	private final double laser_len = 10; 
	private final double laser_width = 5; 
	private double speed = -7.5; 
	DrawingCanvas c; 
		
	public Laser(double start_x, double start_y, DrawingCanvas canvas) {
		//laser = new VisibleImage(i, start_x, start_y, laser_width,laser_len,canvas);
		laser = new FilledRect(start_x,start_y,laser_width,laser_len,canvas); 
		laser.setColor(Color.WHITE);
		c = canvas; 
		start(); 
	}
	
	public void switch_col() {
		laser.setColor(Color.YELLOW);
	}
	
	public void switch_dir() {
		speed = -speed; 
	}
	
	public void kill() {
		laser.removeFromCanvas(); 
	}
	
	public void run() {
		while((laser.getX() >= 0 && laser.getX() <= c.getWidth()) && (laser.getY() <= c.getHeight() && laser.getY() >= 0)) {
			laser.move(0,speed); 
			
			pause(50); 
		}
		laser.removeFromCanvas(); 
	}


}
