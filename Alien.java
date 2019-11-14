import objectdraw.*;
import java.awt.Image;

public class Alien extends ActiveObject {
	private static final int INVADER_WIDTH = 40;
	private static final int INVADER_HEIGHT = 40;
	VisibleImage alien; 
	
	public Alien(Image i, int x, int y, DrawingCanvas canvas) {
		alien = new VisibleImage(i,x,y,canvas); 
	}
	public void die() {
		alien.removeFromCanvas();
	}

}
