import objectdraw.*;

import java.awt.*;

import java.awt.event.*;

public class TowersOfHanoi extends WindowController implements KeyListener {

	// Constants for the window
	private static final int HEIGHT= 800;
	private static final int WIDTH = 800;
	
	//Initialize Disks 
	public void begin() {


	}

	public void onMousePress(Location l) {
		
	}
	
	// Handle the arrow keys by telling the ship to go in the direction of the arrow.
	public void keyTyped(KeyEvent e)
	{	
	}


	// Remember that the key is no longer down.
	public void keyReleased(KeyEvent e)

	{
	}
	// Handle the pressing of the key the same as typing.
	public void keyPressed(KeyEvent e)
	{
		
	}
	
	
	//Win condition met
	public void win() {

	}
	
    public static void main(String[] args) { 
        new TowersOfHanoi().startController(WIDTH, HEIGHT); 
	}

	

}