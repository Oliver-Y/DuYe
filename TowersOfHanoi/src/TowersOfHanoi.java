import objectdraw.*;

import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class TowersOfHanoi extends WindowController implements KeyListener {

	private Pile lPile;
	private Pile mPile;
	private Pile rPile;
	
	//Other initializations
	private int numDisks;
	private FramedRect base;
	private ArrayList<Integer[]> moves;
	private Location current;
	private Disk selected;
	
	//Constants for the window
	private static final int HEIGHT= 800;
	private static final int WIDTH = 800;
	
	
	//Initialize Disks 
	public void begin() {
		base = new FramedRect(50, 650, 700, 50, canvas);
		
		//Initialize Piles
		lPile = new Pile(90, numDisks, canvas);
		mPile = new Pile(300, 0, canvas);
		rPile = new Pile(510, 0, canvas);
				
	}

	public void onMousePress(Location l) {
		current = l;
		if (lPile.diskContains(l)) selected = lPile.removeTop();
		else if (mPile.diskContains(l)) selected = mPile.removeTop();
		else if (rPile.diskContains(l)) selected = rPile.removeTop();
		else selected = null;
	}
	
	public void onMouseDrag(Location l) {
		if (selected != null) {
			double dx = l.getX() - current.getX();
			double dy = l.getY() - current.getY();
			selected.move(dx, dy);
			current = l;
		}
		
	}
	
	public void onMouseRelease(Location l) {
		if (selected != null) {
			if (lPile.contains(l) && lPile.canAdd(selected)) lPile.addTop(selected);
			else if (mPile.contains(l)&& mPile.canAdd(selected)) mPile.addTop(selected);
			else if (rPile.contains(l)&& rPile.canAdd(selected)) rPile.addTop(selected);
			else selected.getPile().addTop(selected);;
		}
		selected = null;
		if(win()) {
			setWinState(); 
		}
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
	public boolean win() {
		if(rPile.getSize() == 2)
			return true;
		return false;

	}
	
	public void setDisk(int n) {
		numDisks = n;
	}
	
	public void setWinState() {
		new Text("Congrabalations, you've won",canvas.getHeight()/4,canvas.getWidth()/4,canvas); 
		//Each will do something with disks on each pile?
		lPile.wColor(); 
		mPile.wColor(); 
		rPile.wColor(); 
		
	}
	
    public static void main(String[] args) { 
    	//Uncomment when ready
//    	int num;
//    	Scanner s = new Scanner(System.in);
//    	System.out.print("How many disks (3-7): ");
//    	while (true) {
//    		num = s.nextInt();
//    		if (3 < num && num < 7) {
//    			break;
//    		}
//        	System.out.print("Invalid number: ");
//    	}
//    	s.close();
        TowersOfHanoi toh = new TowersOfHanoi();
        toh.setDisk(2);
        toh.startController(WIDTH, HEIGHT); 
	}

	

}