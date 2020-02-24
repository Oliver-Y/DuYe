import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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
	private String[] tempMove= {"0","0"};
	private Location current;
	private Disk selected;
	private FramedRect save;
	private Text saveText;
	private FramedRect undo;
	private Text undoText;
	private FramedRect reset;
	private Text resetText;
	private FramedRect autoPlay;
	private Text APText;
	private MoveHistory memory;

	//Constants for the window
	private static final int HEIGHT= 800;
	private static final int WIDTH = 800;

	//Initialize Disks
	public void begin() {
		base = new FramedRect(50, 650, 700, 50, canvas);

		save = new FramedRect(520, 30, 70, 25, canvas);
		saveText = new Text("Save", 539, 33, canvas);
		saveText.setFontSize(15);

		undo = new FramedRect(600, 30, 70, 25, canvas);
		undoText = new Text("Undo", 617, 33, canvas);
		undoText.setFontSize(15);

		reset = new FramedRect(680, 30, 70, 25, canvas);
		resetText = new Text("Reset", 695, 33, canvas);
		resetText.setFontSize(15);

		autoPlay = new FramedRect(520, 65, 230, 25, canvas);
		APText = new Text("Auto Play", 600, 68, canvas);
		APText.setFontSize(15);

		//Initialize Piles
		lPile = new Pile(90, numDisks, canvas);
		mPile = new Pile(300, 0, canvas);
		rPile = new Pile(510, 0, canvas);
		
		memory = new MoveHistory(lPile, mPile, rPile);
	}

	public void onMouseClick(Location l) {
		if (save.contains(l)) {
			try {
				memory.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (undo.contains(l)) {
			memory.undo();
		}
		

	}

	public void onMousePress(Location l) {
		current = l;
		if (lPile.diskContains(l)) {
			selected = lPile.removeTop();
			tempMove[0] = "l";
			tempMove[1] = "l";
		}
		else if (mPile.diskContains(l)) {
			selected = mPile.removeTop();
			tempMove[0] = "m";
			tempMove[1] = "m";
		}
		else if (rPile.diskContains(l)) {
			selected = rPile.removeTop();
			tempMove[0] = "r";
			tempMove[1] = "r";
		}
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
			if (lPile.contains(l) && lPile.canAdd(selected)) {
				lPile.addTop(selected);
				tempMove[1] = "l";
				if (tempMove[0] != tempMove[1]) {
					memory.addMove(tempMove);
				}
			}
			else if (mPile.contains(l)&& mPile.canAdd(selected)) {
				mPile.addTop(selected);
				tempMove[1] = "m";
				if (tempMove[0] != tempMove[1]) {
					memory.addMove(tempMove);
				}			}
			else if (rPile.contains(l)&& rPile.canAdd(selected)) {
				rPile.addTop(selected);
				tempMove[1] = "r";
				if (tempMove[0] != tempMove[1]) {
					memory.addMove(tempMove);
				}
			}
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
