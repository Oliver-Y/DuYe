import java.awt.Image;
import objectdraw.*;



public class Fleet extends ActiveObject {
	
	//initialize constants
	private static final int GRID_WIDTH= 58;
	private static final int GRID_HEIGHT= 50;
	private static final int ALIEN_SIZE= 40;
	
	//initialize variables
	private VisibleImage[][] fleet = new VisibleImage[6][9];
	private SpaceInvaders sc;
	private boolean movingRight = true;
	private int wait = 900;
	private int moveCount = 0;
	private int height = 0;
	private int score = 0;
	
	//initialize fleet
	public Fleet(Image[] enemyShips, SpaceInvaders sc, DrawingCanvas canvas) {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j] = new VisibleImage(enemyShips[i], 29+j*GRID_WIDTH, i*GRID_HEIGHT, 
						ALIEN_SIZE, ALIEN_SIZE, canvas);
			}
		}	
		this.sc = sc;
		start();
	}
	
	public void shoot() {
		
	}

	private void moveLeft() {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j].move(-GRID_WIDTH, 0);
			}
		}
	}

	private void moveRight() {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j].move(GRID_WIDTH, 0);
			}
		}
	}

	private void moveDown() {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j].move(0, GRID_HEIGHT);
			}
		}
		
	}
	
	public boolean checkLaser(FilledRect laser) {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				if (fleet[i][j].overlaps(laser)) {
					
					try {
						fleet[i][j].removeFromCanvas();
						return true;
					}
					catch (IllegalStateException e) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void clear() {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j].removeFromCanvas();
			}
		}
	}
	
	private boolean fleetDead(int h) {
		if (height == 8) {
			clear();
			sc.gameOver(score);
			return true;
		}
		return false;
	}
	
	public void run() {
		//move the fleet in snake pattern & increase speed slowly
		while(true) {
			if (movingRight) {
				if (moveCount == 4) {
					moveDown();
					height++;
					moveCount = 0;
					movingRight = false;
					wait -= 100;
					if (fleetDead(height)) break;
				}
				else {
					moveRight();
					moveCount ++;
				}
			}
			else {
				if (moveCount == 4) {
					moveDown();
					height ++;
					moveCount = 0;
					movingRight = true;
					wait -= 100;
					if (fleetDead(height)) break;
				}
				else {
					moveLeft();
					moveCount ++;
				}
			}
			pause(wait);
		}
	}
}