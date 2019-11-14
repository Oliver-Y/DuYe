import objectdraw.*;
import java.awt.Image;

public class Alien {
	private static final int ALIEN_SIZE = 40;
	private static final int GRID_WIDTH = 58;
	private static final int GRID_HEIGHT= 50;
	
	VisibleImage alien;
	
	public Alien(Image i, int x, int y, DrawingCanvas canvas) {
		alien = new VisibleImage(i, x, y, ALIEN_SIZE, ALIEN_SIZE, canvas);
	}
	
	public void moveLeft() {
		alien.move(-GRID_WIDTH, 0);
	}


	public void moveRight() {
		alien.move(GRID_WIDTH, 0);
	}

	public void moveDown() {
		alien.move(0, GRID_HEIGHT);
	}
	
	public double getX() {
		return alien.getX();
	}

	public boolean checkKill(FilledRect laser) {
		if (alien.overlaps(laser)) {
			alien.removeFromCanvas();
			return true;
		}
		else return false;
	}
	
}
