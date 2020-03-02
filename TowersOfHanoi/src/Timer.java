import java.awt.*;
import objectdraw.*; 
public class Timer extends ActiveObject {
	private Text time;
	private int counter; 
	private boolean stop; 
	
	public Timer(double x, double y, DrawingCanvas c) {
		stop = false;
		counter = 0; 
		time = new Text("Seconds Elapsed: " + counter, x,y,c); 
		time.setFontSize(25);
		start(); 
	}	
	
	public void run() {
		while(true) {
			pause(1000); 
			if(!stop) counter++; 
			time.setText("Seconds Elapsed: " + counter);
		}
	}
	public void toggle() {
		stop = !stop; 
	}
	
	public void reset() {
		counter = 0; 
	}

}
