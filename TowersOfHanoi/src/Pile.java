import objectdraw.*;
import java.awt.Color;
import java.util.*;

//Piles manages all the disks on a single pole (graphics, logic, etc). 
//It interacts with main ToH class.
public class Pile {
	private static final int TOP = 350;
	private static final int HEIGHT = 300;
	
	private FramedRect pole;
	private FramedRect dragRegion; 
	private Stack<Disk> disks;
	private int topY;
	private int center;
	private DrawingCanvas canvas; 
	
	public Pile(int x, int numDisks, DrawingCanvas c) {
		
		canvas = c; 

		//Cursor detector for dragging purposes
		dragRegion = new FramedRect(x+25, TOP, 150, HEIGHT, c);
		dragRegion.hide();
		
		//Initialize pole and disks
		pole = new FramedRect(x+95, TOP, 10, HEIGHT, c);
		pole.sendToBack();
		disks = new Stack<Disk>();
		
		topY = 630;
		for (int i = numDisks; i>0; i--) {
			disks.push(new Disk(x+80-i*10, topY, i, this, c));
			topY -= 20;
		}
		center = x+100;
	}
	
	public boolean contains(Location point) {
		return dragRegion.contains(point);
	}
	
	public boolean diskContains(Location point) {
		if (disks.size() == 0) {
			return false;
		} else {
			return disks.peek().getDiskRect().contains(point);
		}
	}
	
//	public Disk getTop() {
//		return disks.peek();
//	}
//	
	public Disk removeTop() {
		topY +=20;
		return disks.pop();
	}
	
	public void addTop(Disk d) {
		d.setPile(this);
		disks.push(d);
		d.getDiskRect().moveTo(center-20-d.getSize()*10, topY);
		topY-=20;
	}
	
	public boolean canAdd(Disk d) {
		if (disks.size() == 0) {
			return true;
		} else return (d.getSize()<=disks.peek().getSize());
	}
	
	public void wColor() {
		ArrayList<Disk> tempL = new ArrayList<Disk>();
		while(disks.size() > 0) {
			Disk temp = disks.pop(); 
			temp.getDiskRect().setColor(new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255))); 
			temp.floatAnimation(); 
			tempL.add(temp);
		}
		for (int i=tempL.size()-1;i>=0;i--) {
			disks.push(tempL.get(i));
		}
	}
	
	public void selfDestruct() {
		pole.removeFromCanvas();
		dragRegion.removeFromCanvas();
		while (disks.size()>0) {
			disks.pop().getDiskRect().removeFromCanvas();
		}
	}
	
	public int getSize(){
		return disks.size(); 
	}
}
