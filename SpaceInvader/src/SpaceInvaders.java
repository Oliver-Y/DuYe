import objectdraw.*;

import java.awt.*;

import java.awt.event.*;
/* Though Questions:
 * 
 * 1. The game came out in 1978 which was 1 year after Star Wars came out. 
 * People must have been excited to play a space-themed game. In contrast to conventional shooting games of the time, 
 * SpaceInvaders introduced new gameplay by challenging the user to shoot projectiles at moving objects (aliens) 
 * compared to the traditional static targets. The game was easily integrated into arcades because 
 * the original version of the game kept track of high scores, enabling players to compete against each other even if 
 * they weren’t simultaneously playing. Thus, the competitive spirit fueled almost a revolution in arcades and propelled cabinet 
 * consoles to the forefront of entertainment. 
	Beyond the statistics, intuitively, the game is just pretty fun. 
	It’s challenging, in the sense that it can’t be beaten on the first try, and there are no 
	loopholes and hacks that will instantly lead to a win. The randomized shooting of the aliens add to the 
	unpredictability of gameplay

 * 
 * 2. I would make a separate class called base, that keeps its own base_count (to know when to remove itself from the canvas) as well
 *  as its own GUI (visibleImage of whatever the shield looks like). The base class will be shootable and the laser will constantly 
 *  be checking if a base has been hit. If a base has been hit, the laser will use the mutator method provided by the base class to 
 *  decrement the base count. The laser will pass in the x,y coordinates of where it hit the base, so the base class can make the 
 *  corresponding decay of the GUI. 
 * 
 * 
 * 3.The base class will provide a destroy method that instantly removes the entire object from the canvas. 
 * A UFOfactory class will be made to randomly instantiate UFO objects that fly across the screen. 
 * A UFO class will define UFO objects, which are shootable and will continue moving left to right across the canvas. 
 * The UFO class will keep track of the VisibleImage and an array of base objects. If the VisibleImage hits the righter bound of 
 * the canvas, it’ll call the destroy method of every base object (since the UFO object will be passed the Array of bases 
 * probably by the spaceship class or something)  
 * 
 * 
 * 
 */

public class SpaceInvaders extends WindowController implements KeyListener {

	// Constants for the window
	private static final int HEIGHT= 800;
	private static final int WIDTH = 800;
	
	//Initialize variables
	protected Ship ship;
	protected Fleet fleet;
	protected wildGnozz gnozz; 
	protected boolean setup;
	protected FilledRect background;
	protected Text playGame;
	protected Text playAgain;
	protected int score;

	// Remember whether a key is currently depressed
	protected boolean keyDown;

	public void begin() {

		//Background 
		background = new FilledRect(0,0,WIDTH, HEIGHT, canvas);
		background.setColor(Color.WHITE);
		
		playGame = new Text("Click to Start the Game.", WIDTH/4+30, HEIGHT/2-40, canvas);
		playGame.setFontSize(30);
		//Set up Text
		playAgain = new Text("Click to play again.", WIDTH/4+20, HEIGHT/2 + 60, canvas);
		playAgain.setFontSize(15);
		playAgain.setColor(Color.red);
		playAgain.hide();
		
		setup = true;

		requestFocus();
		addKeyListener(this);
		canvas.addKeyListener(this);

	}

	public void onMousePress(Location l) {
		if (setup) {
			setup = false;
			background.setColor(Color.BLACK);
			
			playGame.hide();
			playAgain.hide();
			
			//create fleet of enemies
			Image[] enemyShips = {getImage("invader1.png"), getImage("invader2.png"), getImage("invader3.png"),
					getImage("invader4.png"), getImage("invader5.png"), getImage("invader6.png")};
			fleet = new Fleet(enemyShips, this, canvas,ship);
			//Ship 
			Image shipImage = getImage("ship.png");
			ship = new Ship(shipImage,WIDTH,HEIGHT, fleet, canvas,this);
			//Gnozz
			Image gnozzI = getImage("gnozz.png"); 
			gnozz = new wildGnozz(gnozzI, this, canvas, ship,200,400); 
			//Add in objects so everyone knows everyone else
			fleet.addShip(ship);
			ship.addGnozz(gnozz); 
			fleet.addGnozz(gnozz);
		}
	}
	
	// Handle the arrow keys by telling the ship to go in the direction of the arrow.
	public void keyTyped(KeyEvent e)
	{	
		if (setup) {
		}
		else {
			if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
				 ship.shoot(); 
				}		
				 if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
					ship.set_L(true);

				} 
				 else if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {
					ship.set_R(true); 
		        } 
		}
		

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
	
	//Game Over
	public void gameOver() {
		setup = true;
		ship.clear(); 
		fleet.clear(); 
		gnozz.clear(); 
		playGame.setText("Game over! Your score: " + score + "/540 points.");
		playGame.moveTo(WIDTH/4 - 50, HEIGHT/2-40);
		playGame.setColor(Color.red);
		playGame.show();
		playAgain.setColor(Color.red);
		playAgain.show();
		score = 0; 
	}
	
	//Win condition met
	public void win() {
		setup = true;
		ship.clear(); 
		fleet.clear(); 
		gnozz.clear(); 
		playGame.setText("Congrats bro Your score: " + score + "/540 points.");
		playGame.moveTo(WIDTH/4 - 50, HEIGHT/2-40);
		playGame.setColor(Color.green);
		playGame.show();
		playAgain.setColor(Color.green);
		playAgain.show();
		score = 0; 
	}
	
    public static void main(String[] args) { 
        new SpaceInvaders().startController(WIDTH, HEIGHT); 
	}

	

}