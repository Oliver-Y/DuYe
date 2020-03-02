import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AutoPlay {
	private Queue<String[]> key;
	private Pile l;
	private Pile m;
	private Pile r;
	private MoveHistory memory;
	private AutoPlayGraphics graphics;
	private boolean stillOptimal = true;
	private int flag = 0;
	
	public AutoPlay(Pile lp, Pile mp, Pile rp, MoveHistory mem) {
		key = new LinkedList<String[]>();
		l = lp;
		m = mp;
		r = rp;
		memory = mem;

		figureItOut(lp.size(), "l", "r");
		while (rp.size()>0) {
			mp.addTop(rp.removeTop());
		}
		while (mp.size()>0) {
			lp.addTop(mp.removeTop());
		}
	}
	
	//find middle pile given source and destination
	private String returnMid(String source, String dest) {
		if (source.equals("l")&&dest.equals("m")) return "r";
		else if (source.equals("l")&&dest.equals("r")) return "m";
		else if (source.equals("m")&&dest.equals("r")) return "l";
		else if (source.equals("m")&&dest.equals("l")) return "r";
		else if (source.equals("r")&&dest.equals("l")) return "m";
		else return "l";
	}
	
	//recursive program that finds optimal solution
	private void figureItOut(int num, String source, String dest) {
		if (num == 1) {
			move(source, dest);
		}
		else {
			String mid = returnMid(source, dest);
			figureItOut(num-1, source, mid);
			move(source, dest);
			figureItOut(num-1, mid, dest);
		}
	}
	
	private void move(String s, String d) {
		toPile(d).addTop(toPile(s).removeTop());
		String[] temp = {s, d};
		key.add(temp);
	}
	
	//convert String to Pile
	private Pile toPile(String s) {
		switch (s) {
		case "l":
			return l;
		case "m":
			return m;
		case "r":
			return r;
		}
		return null;
	}
	
	//set flag for auto play
	//for when a wrong move is made
	public void setFlag() {
		flag = memory.getHistory().size();
	}
	
	//check if a move is optimal
	public boolean checkOptimal(String[] move) {
		if (stillOptimal && key.size()>0) {
			boolean result = (move[0].equals(key.peek()[0]) && move[1].equals(key.peek()[1]));
			if (!result) {
				stillOptimal = result;
			} else {
				key.remove();
			}
			return result;
		}
		else return true;
	}
	
	public void remove() {
		key.remove();
	}
	
	// initialize auto play by creating an active object
	// auto play can be called after loading from file, whether the moves saved in file are optimal or not (Counts as an extension?)
	public void startPlaying() {
		if (!stillOptimal) {
			int temp = key.size();
			Stack<String[]> tempStack = new Stack<String[]>();
			while (memory.getHistory().size()>flag) {
				String[] command = memory.getHistory().pop();
				String[] reverse = {command[1], command[0]};
				key.add(reverse);
				tempStack.push(command);
			}
			
			while (tempStack.size()>0) {
				memory.getHistory().push(tempStack.pop());
			}
			
			for (int i = 0; i < temp; i++) {
				key.add(key.remove());
			}
		}
		graphics = new AutoPlayGraphics(memory, key, 1000, false, this);
	}
	
	// pause auto play 
	// player can pause and un-pause and make moves in between without bugging the game out. (Counts as an extension?)
	public void pause() {
		if (graphics != null) {
			graphics.pause();
		}
	}
	
	public Queue<String[]> getKey() {
		return key;
	}

}
