import java.util.Random;

public class Board {
	
	private int [][] board;
	
	public Board() {
		
		board = new int[4][4];
		printBoard();
		
	}
	
	public void update() {
		Random rand = new Random();
		
		// generate two random values 2 (80%) or 4 (20%) 
		int value1 = rand.nextInt(100);
		int value2 = rand.nextInt(100);
		
		if(value1 <= 80) {
			// place 2
			
		} else {
			// place 4
		}
	}
	
	public void moveUp() {
		for(int i = 0; i < 4; i++) {
			for (int j = 0;j < 4; j++) {
				
				// Do something
				
			}
		}
	}
	
	public void moveDown() {
		
	}
	
	public void moveLeft() {
		
	}
	
	public void moveRight() {
		
	}
	
	public void printBoard() {
		for(int i = 0; i < 4; i++) {
			System.out.print("| ");
			for(int j = 0; j < 4; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("|");
		}
	}

}
