import objectdraw.*;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner; 
/*
 * Thought Questions:
 * 1. 
 * 
 * Hypothesis: If there were x disks, it would take 2^x - 1 moves. Let P = 2^x - 1 moves 
 * 
 * The next step is to prove that for x+1 disks there will be 2^x+ 1 -1 disks.
 * Let say for x amount of disks, it takes P moves. Now, if there were x+1 amount of disks, that would mean I would first have to
 * move x amount of disks off from the top, which would take P moves. Then I'd use extra move to move the x+1 disk to the final pole. 
 * finally, I"d move the x amount of disks again onto the final peg, which would take P moves again. 
 * Adding everything together, it means for x+1 disks, it would take 2P + 1 moves. 
 * 
 * Substituting in the value of P into the equation. 2*(2^x - 1) + 1 = 2^x+1 -1
 * 
 * 2. 
 * The command line will reuse some of the autoplay functionality to automatically move Disks onto different polls. Both command line
 * and graphical interface will also use the same backend code, meaning Stacks keep track of Disks on each poll and the checking/logic
 * associated with storing PollStacks in the background.
 * 
 * 
 * 3. the ABACABA is a recursive fractal pattern represents the solution to the Tower of Hanoi problem. 
 * For every new disk that is introduced, the previous pattern/solution has to be repeated twice, before and after, the extra, biggest
 * disk is added to the final pole. 
 * 

 *  A way to view this alphabet pattern is to say that with every new alphabet, the previous pattern will be repeated twice on both ends. 
 	For example: when the letter D is added to the pattern above, the pattern should go ABACABADABACABA
 	For each, ABACABA pattern repeated on both sides of D, this can be viewed as a new letter C is added, 
 	and ABA is repeated on both sides of the pattern. The base case would be two letters, where if B is a new letter, A is repeated on
 	both ends. 
 	
 	For Towers of Hanoi, When a fourth disk is added, we first have to move the first 3 disks into the middle pole. Then the fourth disk
 * has to moved onto the final pole. When we try to move 3 disks, we can view this as moving 2 disks onto whichever 2 poles are free, 
 * and then moving the 3rd disk onto the middle pole (since the 4th disk has to go on the final pole). Then, we can view the problem
 * as a problem of 2 disks (moving the 2nd disk onto the right pole, freeing up the 3rd disk for the middle). 
 * 
 * In both Towers of Hanoi and the Alphabet example, the problem can be broken down into smaller cases until a base case, which also
 * conforms to the recursive nature of the solution we wrote.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

public class TowersOfHanoi extends WindowController implements KeyListener {

	private Pile lPile;
	private Pile mPile;
	private Pile rPile;

	//Other initializations
	private int numDisks;
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
	private Text moves;
	private Text win;
	private boolean load = false;
	private String loadFile;
	private Text notif;
	private AutoPlay iterator;
	private boolean autoPlaying = false;
	private boolean hasAutoPlayed = false;

	//Constants for the window
	private static final int HEIGHT= 800;
	private static final int WIDTH = 800;
	
	public TowersOfHanoi() {}
	public TowersOfHanoi(String f) {
		load = true;
		loadFile = f;
	}
	
	//Initialize Disks
	public void begin() {	
		//Setup GUI of game
		//Initialize Piles
		lPile = new Pile(90, numDisks, canvas);
		mPile = new Pile(300, 0, canvas);
		rPile = new Pile(510, 0, canvas);
		
		//Base
		new FramedRect(50, 650, 700, 50, canvas);
		
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
		
		moves = new Text("Number of Moves: 0",50,50, canvas);
		moves.setFontSize(25);
		
		notif = new Text("", 592, 100, canvas);
		notif.setFontSize(15);
		notif.setColor(Color.red);
		
		new Text("Type 3-7 to change the number of disks", 270, 710, canvas);
		
        addKeyListener(this);
        canvas.addKeyListener(this);
        
        
        // Setup memory that keeps track of move history
        memory = new MoveHistory(lPile, mPile, rPile, moves, numDisks);
        
        // Setup iterator with optimal solution
        iterator = new AutoPlay(lPile, mPile, rPile, memory);
        
        //feed iterator to memory
        memory.setIterator(iterator);
		
        //Check loading
		if (load) {
			try {
				memory.loadGame(loadFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
        
	}

	public void onMouseClick(Location l) {
		//Can't save or undo during auto play
		//Can't undo after having auto played because we suck at programming
		if (save.contains(l) && !autoPlaying) {
			try {
				memory.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
			notif.setText("Game Saved");
		}
		if (undo.contains(l) && !autoPlaying) {
			if (hasAutoPlayed) {
				notif.setText("Please reset first!");
			} else {
				memory.undo();
			}
		}
		if (reset.contains(l)) {
			reset(numDisks);
			notif.setText("Game Reset");
		}
		if (autoPlay.contains(l)) {
			if (!autoPlaying) {
				autoPlaying = true;
				hasAutoPlayed = true;
				notif.setText("Auto Playing");
				iterator.startPlaying();
			} else {
				autoPlaying = false;
				notif.setText("Auto Play Paused");
				iterator.pause();
			}
		}
	}
	
	//Drag & drop mechanism
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
				addNcheck();
			}
			else if (mPile.contains(l)&& mPile.canAdd(selected)) {
				mPile.addTop(selected);
				tempMove[1] = "m";
				addNcheck();
			}
			else if (rPile.contains(l)&& rPile.canAdd(selected)) {
				rPile.addTop(selected);
				tempMove[1] = "r";
				addNcheck();
			}
			else selected.getPile().addTop(selected);
		}
		selected = null;
		if(win()) {
			setWinState();
		}
	}
	
	//move disk & check for optimal move
	private void addNcheck() {
		if (tempMove[0] != tempMove[1]) {
			memory.addMove(tempMove);
			if (iterator.checkOptimal(tempMove)) {
				notif.setText("");
			} else {
				notif.setText("Move != Best");
			}
		}
	}

	//reset game
	private void reset(int  num) {
		numDisks = num;
		lPile.selfDestruct();
		mPile.selfDestruct();
		rPile.selfDestruct();
		lPile = new Pile(90, numDisks, canvas);
		mPile = new Pile(300, 0, canvas);
		rPile = new Pile(510, 0, canvas);
		if (win != null) {
			win.hide();
		}
		
		moves.setText("Number of Moves: 0");
		notif.setText("");
		
		memory = new MoveHistory(lPile, mPile, rPile, moves, numDisks);
		iterator.pause();
		iterator = new AutoPlay(lPile, mPile, rPile, memory);
		autoPlaying = false;
		hasAutoPlayed = false;

	}
	
	// Handle the arrow keys by telling the ship to go in the direction of the arrow.
	public void keyTyped(KeyEvent e)
	{
		if (Character.isDigit(e.getKeyChar())) {
			int number = e.getKeyChar() - '0';
			if (number > 2 && number < 8) {
				reset(number);
			}
		}
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
	private boolean win() {
		if(rPile.size() == numDisks)
			return true;
		return false;

	}

	public void setDisk(int n) {
		numDisks = n;
	}

	private void setWinState() {
		win = new Text("Congrabalations, you've won",270, 660,canvas);
		win.setFontSize(20);
		lPile.wColor();
		mPile.wColor();
		rPile.wColor();
	}

    public static void main(String[] args) throws FileNotFoundException {
        TowersOfHanoi toh;
        int nd = 3;
    	if (args.length>0) {
    		File f = new File(args[0]);
    		Scanner s = new Scanner(f);
    		nd=s.nextInt();
    		s.close();
    		toh = new TowersOfHanoi(args[0]);
    	} else {
    		toh = new TowersOfHanoi();
    	}
    	
        toh.setDisk(nd);
        toh.startController(WIDTH, HEIGHT);
	}
}
