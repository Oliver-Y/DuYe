import java.awt.*;
import objectdraw.*; 
public class Disk extends ActiveObject {

	private static final int HEIGHT = 20;
	
	private int size;
	private FilledRect disk;
	private Pile p;
	private boolean fin; 
	private DrawingCanvas c;
	private int speedx = 50; 
	private int speedy = 50; 
	
	public Disk(int x, int y, int size, Pile p, DrawingCanvas canvas) {
		this.size = size;
		this.p = p;
		this.fin = false;
		this.c = canvas; 
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
	
	public void floatAnimation() {
		fin = true;
		start(); 
	}
	public void run() {
		pause(1000);
		System.out.println(c.getWidth() + " " + c.getHeight());
		while(fin){
			if(disk.getX() <= 0 || disk.getX() >= c.getWidth()) speedx = -speedx; 
			if(disk.getY() >= c.getHeight() || disk.getY() <= 0) speedy = -speedy; 
			disk.move(speedx, speedy);
			pause(500); 
		}
	}
}
