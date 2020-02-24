import objectdraw.*;

import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class TowersOfHanoi extends WindowController implements KeyListener {
	//Three stacks of disks because three poles
//	private Stack<Disk> left = new Stack<Disk>();
//	private Stack<Disk> middle = new Stack<Disk>();
//	private Stack<Disk> right = new Stack<Disk>();
	
	private Pile lPile;
	private Pile mPile;
	private Pile rPile;
	
	//Other initializations
	private int numDisks;
	private FramedRect base;
	private FramedRect lPole;
	private FramedRect mPole;
	private FramedRect rPole;
	private ArrayList<Integer[]> moves;

	
	//Constants for the window
	private static final int HEIGHT= 800;
	private static final int WIDTH = 800;
	
	//Initialize Disks 
	public void begin() {
		base = new FramedRect(50, 650, 700, 50, canvas);
//		lPole = new FramedRect(185, 350, 10, 300, canvas);
//		mPole = new FramedRect(395, 350, 10, 300, canvas);
//		rPole = new FramedRect(605, 350, 10, 300, canvas);
//		
		lPile = new Pile(90, numDisks, canvas);
		mPile = new Pile(300, 0, canvas);
		rPile = new Pile(510, 0, canvas);
		
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
	
	public void setDisk(int n) {
		numDisks = n;
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
        toh.setDisk(4);
        toh.startController(WIDTH, HEIGHT); 
	}

	

}