import java.util.*;
import java.io.*;
import objectdraw.*;


public class MoveHistory {
	private Stack<String[]> history;
	private Pile lPile;
	private Pile mPile;
	private Pile rPile;
	private Pile source;
	private Pile destination;
	private Text numMoves;
	private int numDisks;
	private AutoPlay iterator;
	
	public MoveHistory(Pile l, Pile m, Pile r, Text n, int nd) {
		lPile = l;
		mPile = m;
		rPile = r;
		numMoves = n;
		numDisks = nd;
		
		history = new Stack<String[]>();
	}
	
	public void setIterator(AutoPlay i) {
		iterator = i;
	}

	//loadGame using AutoPlayGraphics
	public void loadGame(String fileName) throws FileNotFoundException{
		File f = new File(fileName);
		Scanner s = new Scanner(f);
		Queue<String[]> key = new LinkedList<String[]>();
		s.nextLine();
		while(s.hasNextLine()) {
			String[] move = s.nextLine().split(",");
			key.add(move);
		}
		s.close();
		new AutoPlayGraphics(this, key, 100, true, iterator);

		numMoves.setText("Number of Moves: " + history.size());
	}
	
	//add move to history
	public void addMove(String[] a){
		String[] temp = {"x", "x"};
		temp[0] = a[0];
		temp[1] = a[1];
		history.push(temp);
		numMoves.setText("Number of Moves: " + history.size());
	}
	
	//undo a move
	public String[] undo(){
		if (history.size()>0) {
			String[] temp = history.pop();
			String[] undoTemp = {temp[1], temp[0]};
			redoInternal(undoTemp);
			numMoves.setText("Number of Moves: " + history.size());
			return temp;
		}
		return null;
	}
	
	//perform a move
	public void redo(String[] command) {
		history.push(command);
		numMoves.setText("Number of Moves: " + history.size());
		redoInternal(command);
	}
	
	//perform a move for loading
	public void redoLoad(String[] command) {
		if (!iterator.checkOptimal(command)) {
			iterator.setFlag();
		}
		redo(command);
	}
	
	public void redoInternal(String[] command) {
		switch (command[0]) {
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
		
		switch (command[1]) {
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

	
	public Stack<String[]> getHistory() {
		return history;
	}
	
	//save move history to file
	public void save() throws IOException {
		FileWriter gameFile = new FileWriter("currentGame.txt");
		ArrayList<String[]> temp = new ArrayList<String[]>();
		while (history.size()>0) {
			temp.add(history.pop());
		}
		gameFile.write(numDisks+"\n");
		for (int i=temp.size()-1;i>=0;i--) {
			gameFile.write(temp.get(i)[0]+","+temp.get(i)[1] + "\n");
			
			history.push(temp.get(i));
		}
		
		gameFile.close();
	}
	
}
