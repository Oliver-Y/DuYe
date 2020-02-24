import java.awt.*;
import objectdraw.*; 
public class Disk {

	private static final int HEIGHT = 20;
	
	private int size;
	private FilledRect disk;
	private Pile p;
	
	public Disk(int x, int y, int size, Pile p, DrawingCanvas canvas) {
		this.size = size;
		this.p = p;
		disk = new FilledRect(x, y, (40+size*20), HEIGHT, canvas);
		switch (size) {
			case 1:
				disk.setColor(Color.red);
				break;
			case 2:
				disk.setColor(Color.green);
				break;
			case 3:
				disk.setColor(Color.blue);
				break;
			case 4:
				disk.setColor(Color.yellow);
				break;
			case 5:
				disk.setColor(Color.cyan);
				break;
			case 6:
				disk.setColor(Color.magenta);
				break;
			case 7:
				disk.setColor(Color.gray);
				break;
		}
		disk.sendToFront();
	}
	
	public void move(double dx, double dy) {
		disk.move(dx, dy);
	}
	
	public Pile getPile() {
		return p;
	}
	
	public void setPile(Pile p) {
		this.p = p;
	}
	
	public int getSize() {
		return size;
	}
	
	public FilledRect getDiskRect() {
		return disk;
	}
}
