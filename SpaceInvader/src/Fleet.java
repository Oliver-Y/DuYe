import java.awt.Image;
import objectdraw.*;



public class Fleet extends ActiveObject {
	
	//initialize constants
	private static final int GRID_WIDTH= 58;
	private static final int GRID_HEIGHT= 50;
	
	//initialize variables
	private Alien[][] fleet = new Alien[6][9];
	private boolean movingLeft = false;
	private boolean movingRight = true;
	private int wait = 1000;
	
	//initialize fleet
	public Fleet(Image[] enemyShips, DrawingCanvas canvas) {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j] = new Alien(enemyShips[i], 29+j*GRID_WIDTH, i*GRID_HEIGHT, canvas);
			}
		}	
		start();
	}
	
	public void shoot() {
		
	}
	
	
	public void run() {
		//move the fleet in snake pattern & increase speed every time when fleet moves down
		while(true) {
			if (movingRight) {
				if (fleet[0][8].getX() == 29+12*GRID_WIDTH) {
					moveDown();
					movingRight = false;
					movingLeft = true;
				} 
				else {
					moveRight();
				}
			}
			else {
				if (fleet[0][0].getX() == 29) {
					moveDown();
					wait -= 250;
					movingLeft = false;
					movingRight = true;
				} 
				else {
					moveLeft();
				}
			}
			pause(wait);
		}
	}

	private void moveDown() {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j].moveDown();
			}
		}
	}


	private void moveRight() {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j].moveRight();
			}
		}
	}

	private void moveLeft() {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j].moveLeft();
			}
		}
	}
	
	
	public boolean checkLaser(FilledRect laser) {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				if (fleet[i][j].checkKill(laser)) return true;
			}
		}
		return false;
	}

}