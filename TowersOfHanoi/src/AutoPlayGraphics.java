import java.util.Queue;

import objectdraw.*;

public class AutoPlayGraphics extends ActiveObject{
	private MoveHistory memory;
	private Queue<String[]> key; 
	private boolean stop = false;
	
	public AutoPlayGraphics(MoveHistory h, Queue<String[]> k) {
		memory = h;
		key = k;
		start();
	}
	
	public void run() {
		while (key.size()>0 && !stop) {
			memory.redo(key.remove());
			pause(1000);
		}
	}
	
	public void pause() {
		stop = true;
	}
	
	
	
}
