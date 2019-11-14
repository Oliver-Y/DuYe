import objectdraw.*;

import java.awt.*;

import java.awt.event.*;


public class SpaceInvaders extends WindowController implements KeyListener {

	// Constants for the window
	private static final int HEIGHT= 800;
	private static final int WIDTH = 800;	
	
	//Initialize variables
	protected Ship ship;
	protected Fleet fleet;
	protected boolean setup;
	protected FilledRect background;
	protected Text playGame;
	protected Text score;

	// Remember whether a key is currently depressed
	protected boolean keyDown;

	public void begin() {

		/* This code will make it so the window cannot be resized */

		/*
		Container c = this;
		while(! (c instanceof Frame)) {
			c = c.getParent();
		}
		((Frame)c).setResizable(false);
		*/
		//Background
		background = new FilledRect(0,0,WIDTH, HEIGHT, canvas);
		
		//Ship
		Image shipImage = getImage("ship.png");
		ship = new Ship(shipImage,WIDTH,HEIGHT, fleet, canvas);
		
		//create fleet of enemies
		Image[] enemyShips = {getImage("invader1.png"), getImage("invader2.png"), getImage("invader3.png"),
				getImage("invader4.png"), getImage("invader5.png"), getImage("invader6.png")};
		fleet = new Fleet(enemyShips, canvas);
		
		playGame = new Text("Click to Start the Game.", WIDTH/2-20, HEIGHT/2-10, canvas);
		playGame.setFontSize(30);
		
		setup = true;

		requestFocus();
		addKeyListener(this);
		canvas.addKeyListener(this);

	}


	public void onMouseClick(Location l) {

	}
	
	// Handle the arrow keys by telling the ship to go in the direction of the arrow.
	public void keyTyped(KeyEvent e)
	{	
		if (setup) {
			
		}
		else {
			background.setColor(Color.BLACK);
		}
		
			if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
			 ship.shoot(); 
			}		
			 if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
				ship.set_L(true);

			} 
			 else if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {
				ship.set_R(true); 
	        } 
			/* else if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
				 ship.set_S(true); 
			 }*/
	}


	// Remember that the key is no longer down.
	public void keyReleased(KeyEvent e)

	{
		keyDown = false; 
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			ship.set_L(false);
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship.set_R(false);
		}
		/*else if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
			 ship.set_S(false); 
		}*/
	}
	// Handle the pressing of the key the same as typing.
	public void keyPressed(KeyEvent e)
	{
		
		//ship.moveRight(); 
		if (!keyDown)
		{
			keyTyped(e);
		}
		keyDown = true;

	}
	
    public static void main(String[] args) { 
        new SpaceInvaders().startController(WIDTH, HEIGHT); 

	}

	

}