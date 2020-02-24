import objectdraw.*;

import java.awt.Color;
import java.util.Stack;

public class Pile {
	private static final int TOP = 350;
	private static final int HEIGHT = 300;
	
	private FramedRect pole;
	private FramedRect dragRegion; 
	
	public Pile(int x, int numDisks, DrawingCanvas c) {
		dragRegion = new FramedRect(x, TOP, 200, HEIGHT, c);
		dragRegion.hide();
		pole = new FramedRect(x+95, TOP, 10, HEIGHT, c);
		
	}
}
