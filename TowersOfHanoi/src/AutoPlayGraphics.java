import java.util.Queue;

import objectdraw.*;

public class AutoPlayGraphics extends ActiveObject{
	private MoveHistory memory;
	private Queue<String[]> key; 
	private boolean stop = false;
	private int pace;
	private boolean buffer;
	
	public AutoPlayGraphics(MoveHistory h, Queue<String[]> k, int p, boolean b) {
		memory = h;
		key = k;
		pace = p;
		buffer = b;
		if (buffer) {
			pause(500);
		}
		start();
	}
	
	public void run() {
		while (key.size()>0 && !stop) {
			pause(pace);
			memory.redo(key.remove());
		}
	}
	
	public void pause() {
		stop = true;
	}
	
	
	
}
