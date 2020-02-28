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
	
	private String returnMid(String source, String dest) {
		if (source.equals("l")&&dest.equals("m")) return "r";
		else if (source.equals("l")&&dest.equals("r")) return "m";
		else if (source.equals("m")&&dest.equals("r")) return "l";
		else if (source.equals("m")&&dest.equals("l")) return "r";
		else if (source.equals("r")&&dest.equals("l")) return "m";
		else return "l";
	}
	
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
	
	public void setFlag() {
		flag = memory.getHistory().size();
	}
	
	public boolean checkOptimal(String[] move) {
		if (stillOptimal && key.size()>0) {
			boolean temp = (move[0].equals(key.peek()[0]) && move[1].equals(key.peek()[1]));
			if (!temp) {
				stillOptimal = temp;
			} else {
				key.remove();
			}
			return temp;
		}
		else return true;
	}
	
	public void remove() {
		key.remove();
	}
	
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
		graphics = new AutoPlayGraphics(memory, key);
	}
	public void pause() {
		if (graphics != null) {
			graphics.pause();
		}
	}

}
