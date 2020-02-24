import java.util.*;
import java.io.*;

public class MoveHistory {
	private Stack<String[]> history;
	private Pile lPile;
	private Pile mPile;
	private Pile rPile;
	private Pile source;
	private Pile destination;
	
	public MoveHistory(Pile l, Pile m, Pile r) {
		lPile = l;
		mPile = m;
		rPile = r;
		
		history = new Stack<String[]>();
	}
	
	public MoveHistory(Pile l, Pile m, Pile r, String filename) throws FileNotFoundException {
		lPile = l;
		mPile = m;
		rPile = r;
		
		File f = new File(filename);
		Scanner s = new Scanner(f);
		history = new Stack<String[]>();
		
		while(s.hasNextLine()) {
			history.push(s.nextLine().split(","));
		}
		s.close();
	}
	
	public void addMove(String[] a){
		String[] temp = {"x", "x"};
		temp[0] = a[0];
		temp[1] = a[1];
		history.push(temp);
	}
	
	public String[] undo(){
		if (history.size()>0) {
			String[] temp = history.pop();
			undo(temp);
			return temp;
		}
		return null;
	}
	
	public void undo(String[] command) {
		switch (command[1]) {
		case "l":
			source = lPile;
			break;
		case "m":
			source = mPile;
			break;
		case "r":
			source = rPile;
			break;
		}
		
		switch (command[0]) {
		case "l":
			destination = lPile;
			break;
		case "m":
			destination = mPile;
			break;
		case "r":
			destination = rPile;
			break;
		}
		destination.addTop(source.removeTop());
	}
	
	public void save() throws IOException {
		FileWriter gameFile = new FileWriter("currentGame.txt");
		ArrayList<String[]> temp = new ArrayList<String[]>();
		while (history.size()>0) {
			System.out.println(history.peek()[0]+history.peek()[1]);
			temp.add(history.pop());
		}
		for (int i=temp.size()-1;i>=0;i--) {
			gameFile.write(temp.get(i)[0]+","+temp.get(i)[1] + "\n");
			
			history.push(temp.get(i));
		}
		
		gameFile.close();
	}
	
}
