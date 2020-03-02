import java.io.File;
import java.util.Scanner; 

public class CLI {
	
	public static void main(String[] args) {
		 TowersOfHanoi toh = new TowersOfHanoi();

		 Scanner s = new Scanner(System.in);
			try {
				int num = Integer.parseInt(s.next()); 
				while(s.hasNext()) {
					String temp = s.next();
					temp.trim(); 
					//Set temp moves + recognize Piles. 
					toh.move(temp.charAt(0),temp.charAt(1)); 
				}
			}
			catch(Exception e) {
				System.out.println("file not found"); 
			}	    	
	        toh.setDisk(3);
	}

}
