import java.awt.Image;

import objectdraw.*;



public class Fleet extends ActiveObject {

	//initialize constants
	private static final int INVADER_WIDTH = 40;
	private static final int INVADER_HEIGHT = 40;
	private static final int BUFFER_WIDTH= 58;
	private static final int BUFFER_HEIGHT= 50;
	
	//initialize variables
	VisibleImage[][] fleet = new VisibleImage[6][9];
	boolean movingLeft = false;
	boolean movingRight = true;
	//initialize
	public Fleet(Image[] enemyShips, DrawingCanvas canvas) {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j] = new VisibleImage(enemyShips[i], 28+j*BUFFER_WIDTH, i*BUFFER_HEIGHT, INVADER_WIDTH, INVADER_HEIGHT, canvas);
			}
		}	
		start();
	}
	public void shoot() {
		
	}
	
	
	public void run() {
		while(true) {
			if (movingRight) {
				if (fleet[0][8].getX() == 28+12*BUFFER_WIDTH) {
					moveDown();
					movingRight = false;
					movingLeft = true;
				} 
				else {
					moveRight();
				}
			}
			else {
				if (fleet[0][0].getX() == 28) {
					moveDown();
					movingLeft = false;
					movingRight = true;
				} 
				else {
					moveLeft();
				}
			}
			
			
			
			pause(1000);
		}
			// the moving mechanism is not working correctly yet

	}

	

	private void moveDown() {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j].moveTo(fleet[i][j].getX(), fleet[i][j].getY()+BUFFER_HEIGHT);
			}
		}
	}


	private void moveRight() {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				//fleet[i][j].move(10, 0);
				fleet[i][j].moveTo(fleet[i][j].getX() + BUFFER_WIDTH, fleet[i][j].getY());
			}
		}
	}

	private void moveLeft() {
		for (int i = 0; i < fleet.length; i++) {
			for (int j = 0; j < fleet[0].length; j++) {
				fleet[i][j].moveTo(fleet[i][j].getX() - BUFFER_WIDTH, fleet[i][j].getY());
			}
		}
	}

}