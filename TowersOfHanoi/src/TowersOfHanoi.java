import objectdraw.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
		//Initialize Piles
		lPile = new Pile(90, numDisks, canvas);
		mPile = new Pile(300, 0, canvas);
		rPile = new Pile(510, 0, canvas);
		
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
		
		moves = new Text("Number of Moves: 0",50,50, canvas);
		moves.setFontSize(25);
		
		notif = new Text("", 592, 100, canvas);
		notif.setFontSize(15);
		notif.setColor(Color.red);
		
		new Text("Type 3-7 to change the number of disks", 270, 710, canvas);
		
		memory = new MoveHistory(lPile, mPile, rPile, moves);
		if (load) {
			try {
				memory.loadGame(loadFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		iterator = new AutoPlay(lPile, mPile, rPile, memory);
		
        addKeyListener(this);
        canvas.addKeyListener(this);
        
	}

	public void onMouseClick(Location l) {
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
				iterator.setFlag();
			}
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
		
		memory = new MoveHistory(lPile, mPile, rPile, moves);
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
		//Each will do something with disks on each pile?
		lPile.wColor();
		mPile.wColor();
		rPile.wColor();
	}

    public static void main(String[] args) {
        	TowersOfHanoi toh;
    	if (args.length>0) {
    		toh = new TowersOfHanoi(args[0]);
    	} else {
    		toh = new TowersOfHanoi();
    	}

        toh.setDisk(3);
        toh.startController(WIDTH, HEIGHT);
	}
}
