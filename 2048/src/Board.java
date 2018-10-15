
import java.util.Random;


public class Board {
	
	private Random rand = new Random();
	private int [][] board;
	private final int dimension = 4;
	private int highScore, validMoves;
	private boolean endGame, validPlay;
	
	
	// Constructor
	public Board() {
		board = new int[dimension][dimension];
		highScore = 0; validMoves = -1;
		endGame = false;
		update();
		printBoard();
	}
	
	public int getValue(int i, int j) {
		return board[i][j];
	}
	
	public int getMoves() {
		return validMoves;
	}

	// Generates two new values onto board. 
	public boolean update() {
		validPlay = true;
		validMoves ++;
		// Generate two random values 2 (80%) or 4 (20%) 
		int num1 = rand.nextInt(100);
		int num2 = rand.nextInt(100);
		
		// Assignment for first generated number
		if(num1 <= 80) {
			num1 = 2;	
		} else {
			num1 = 4;
		}
		
		// Assignment for second generated number
		if(num2 <= 80) {
			num2 = 2;
			
		} else {
			num2 = 4;
		}
		
		// Place generated numbers in random spots	
		updateScore();
		return updateBoard(num1) && updateBoard(num2);
	}
	
	// Updated the high score
	public void updateScore() {
		for(int i = 0; i < dimension; i++)
			for(int j = 0; j < dimension; j++)
				if(highScore < board[i][j])
					highScore = board[i][j];
		
	}
	
	// Getter for highScore
	public int getScore() {
		return highScore;
	}
	
	// Places the two values onto board. 
	public boolean updateBoard(int val) {
		int x = rand.nextInt(4);
		int y = rand.nextInt(4);
		
		boolean found = false;
		
		for(int i = y; true;) {
			for(int j = 0; j < dimension; j++) {
				
				// Find if available position in x position
				if(board[i][j] == 0) { found = true; }
			}
			
			if(found) { // pick random position for x in positon y
				for(int j = x; true;) {
					if(board[i][j] == 0) { board[i][j] = val; break; }
					j++;
					if(j == dimension) { j = 0; }
					if(j == x) { break; }
				}
				break;
			}
			
			// Increment i
			i++;
			if(i == dimension) { i = 0; }
			if(i == y) { break; }
		}
		updateScore();
		return found;
	}
	
	public void printBoard() {
		int [] temp = new int[dimension]; // For console print
		
		for(int i = 0; i < dimension; i ++) {
			for(int j = 0; j < dimension; j++) {
				System.out.println(); // Clear screen
				int size = Integer.toString(board[i][j]).length();
				if(size == 0) size = 1;
				int set = temp[j];				
				if(set < size)
					temp[j] = Integer.toString(board[i][j]).length();
			}
		}
		
		/** Print out board **/
		// Top Line
		System.out.print("\n+");
		for(int i = 0; i < dimension; i++) 
			for(int j = 0; j < temp[i] + 1; j++)
				System.out.print("-");
		
		System.out.print("-");
		System.out.print("+\n");
				
		// Board
		for(int i = 0; i < dimension; i++) {
			System.out.print("| ");
			for(int j = 0; j < dimension; j++) {		
				System.out.print(board[i][j]);
				
				// Print spaces
				int spaces = temp[j] - Integer.toString(board[i][j]).length() + 1;
				
				for(int space = 0; space < spaces; space++) 
					System.out.print(" ");
			}
			System.out.println("|");
		}
		
		// Bottom Line
		System.out.print("+");
		for(int i = 0; i < dimension; i++) 
			for(int j = 0; j < temp[i] + 1; j++)
				System.out.print("-");
	
		System.out.print("-");
		System.out.print("+\n");
		
		// Print Score
		System.out.println("Score: " + getScore() + " | Valid Moves: " + getMoves());
		
		// Print Valid Moves
		if(validMoves > 0 && !endGame) {
			if(validPlay)
				System.out.println("Valid Move");
			else 
				System.out.println("Invalid Move");
		}
		
		if(endGame) {
			System.out.println("Game Over");
		}
			// Controls:
			System.out.println("Press: (R)eset, (Q)uit, (Arrow keys) Moves");
			
	}
	

	/** MOVE UP **/
	public void moveUp() {
		// Compare Then Shift
		boolean [][] added = new boolean[dimension][dimension];
		boolean invalid = true;

		// TODO(Add): Compare and add
		for(int row = 0; row < dimension-1; row++) { // from top to bottom
			for(int i = row+1; i < dimension; i++) { // i in {y1, y2, y3}
				for(int index = 0; index < dimension; index++) { // from index 0 to 3
					// Compare and add
					if(board[row][index] != 0 && board[row][index] == board[i][index] && added[row][index] == false) {  // add if match or avoid obstruction
						board[row][index] *= 2; 
						board[i][index] = 0;
						added[row][index] = true;
						invalid = false;
					}
					
					// Obstruction (if there's a 2, 4, 2. Cannot add to the first 2 with 3rd 2
					if( (board[row][index] != 0 && board[i][index] != 0) && (board[row][index] != board[i] [index]) ) {
						added[row][index] = true;
					}
				}
			}
		}
		
		// TODO(Shift): Shift Up
		for(int row = 0; row < dimension-1; row++) {
			for(int i = row+1; i < dimension; i++) { // i in {y1, y2, y3}
				for(int index = 0; index < dimension; index++) {
					if(board[row][index] == 0 && board[i][index] != 0) {
						board[row][index] = board[i][index];
						board[i][index] = 0;
						invalid = false;
					}
				}
			}
		}
		
		// Generate new numbers only if move is valid. 
		if(!invalid) {
			update();
		} else { 
			invalid();
		}
	}
	
	/** MOVE DOWN **/
	public void moveDown() {
		// Compare Then Shift
		boolean [][] added = new boolean[dimension][dimension];
		boolean invalid = true;

		// TODO(Add): Compare and add
		for(int row = dimension-1; row > 0; row--) { // from top to bottom
			for(int i = row-1; i >= 0; i--) { // i in {y2 y1, y0}
				for(int index = 0; index < dimension; index++) { // from index 0 to 3
					// Compare and add
					if(board[row][index] != 0 && board[row][index] == board[i][index] && added[row][index] == false) { 
						board[row][index] *= 2; 
						board[i][index] = 0;
						added[row][index] = true;
						invalid = false;
					}
					
					// Obstruction (if there's a 2, 4, 2. Cannot add to the first 2 with 3rd 2
					if( (board[row][index] != 0 && board[i][index] != 0) && (board[row][index] != board[i][index]) ) {
						added[row][index] = true;
					}
				}
			}
		}
		
		// TODO(Shift): Shift Down
		for(int row = dimension-1; row > 0; row--) {
			for(int i = row-1; i >= 0; i--) { // i in {y2, y1, y0}
				for(int index = 0; index < dimension; index++) {
					if(board[row][index] == 0 && board[i][index] != 0) {
						board[row][index] = board[i][index];
						board[i][index] = 0;
						invalid = false;
					}
				}
			}
		}

		// Generate new numbers only if move is valid. 
		if(!invalid) {
			update();
		} else { 
			invalid();
		}

	}
	
	/** MOVE LEFT **/
	public void moveLeft() {
		// Compare Then Shift
		boolean [][] added = new boolean[dimension][dimension];
		boolean invalid = true;
		
		// TODO(Add): Compare and add
		for(int col = 0; col < dimension-1; col++) { // from left to right
			for(int i = col+1; i < dimension; i++) { // i in {col1, col2, col3}
				for(int row = 0; row < dimension; row++) { // from index 0 to 3
					// Compare and add
					if(board[row][col] != 0 && board[row][col] == board[row][i] && added[row][col] == false) { 
						board[row][col] *= 2; 
						board[row][i] = 0;
						added[row][col] = true;
						invalid = false;
					}
					
					// Obstruction (if there's a 2, 4, 2. Cannot add to the first 2 with 3rd 2
					if( (board[row][col] != 0 && board[row][i] != 0) && (board[row][col] != board[row][i]) ) {
						added[row][col] = true;
					}
				}
			}
		}

		// TODO(Shift): Shift Left
		for(int col = 0; col < dimension-1; col++) {
			for(int i = col+1; i < dimension; i++) { // i in {col1, col2, col3}
				for(int row = 0; row < dimension; row++) {
					if(board[row][col] == 0 && board[row][i] != 0) {
						board[row][col] = board[row][i];
						board[row][i] = 0;
						invalid = false;
					}
				}
			}
		}
		// Generate new numbers only if move is valid. 
		if(!invalid) {
			update();
		} else { 
			invalid();
		}
	}
	
	/** MOVE RIGHT **/
	public void moveRight() {
		// Compare Then Shift
		boolean [][] added = new boolean[dimension][dimension];
		boolean invalid = true;
		
		// TODO(Add): Compare and add
		for(int col = dimension-1; col > 0; col--) { // from left to right
			for(int i = col-1; i >= 0; i--) { // i in {col1, col2, col3}
				for(int row = 0; row < dimension; row++) { // from index 0 to 3
					// Compare and add
					if(board[row][col] != 0 && board[row][col] == board[row][i] && added[row][col] == false) { 
						board[row][col] *= 2; 
						board[row][i] = 0;
						added[row][col] = true;
						invalid = false;
					}
					
					// Obstruction (if there's a 2, 4, 2. Cannot add to the first 2 with 3rd 2
					if( (board[row][col] != 0 && board[row][i] != 0) && (board[row][col] != board[row][i]) ) {
						added[row][col] = true;
					}
				}
			}
		}

		// TODO(Shift): Shift Right
		for(int col = dimension-1; col > 0; col--) {
			for(int i = col-1; i >= 0; i--) { // i in {col1, col2, col3}
				for(int row = 0; row < dimension; row++) {
					if(board[row][col] == 0 && board[row][i] != 0) {
						board[row][col] = board[row][i];
						board[row][i] = 0;
						invalid = false;
					}
				}
			}
		}
		
		// Generate new numbers only if move is valid. 
		if(!invalid) {
			update();
		} else { 
			invalid();
		}
	}
	
	// Check to see if any more moves are available
	public void invalid() {
		System.out.println("Invalid Move!");
		validPlay = false;
		
		// Check to see any empty spaces
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				int position = board[i][j];
				if(position == 0) {
					return;
				}
			}
		}
		
		// Check to see if any other moves are available
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				int position = board[i][j];
				
			
				/** Check if any values adjacent matches **/
				
				// Middle to outer
				if ( (i == 1 || i == 2) && (j == 1 || j == 2) ) {
					if(position == board[i][j-1] ||
					   position == board[i][j+1] ||
					   position == board[i-1][j] || 
				       position == board[i+1][j]) { return; }
				}
				
				// Top-Left Corner
				else if(i == 0 && j == 0) {
					if(position == board[i][j+1] || position == board[i+1][j]) {
						return;
					}
				}
				
				// Top-Right Corner
				else if(i == 0 && j == dimension-1) {
					if(position == board[i][j-1] || position == board[i+1][j]) {
						return;
					}
				}
				
				// Bottom-Left Corner
				else if(i == dimension-1 && j == 0) {
					if(position == board[i][j+1] || position == board[i-1][j]) {
						return;
					} 
				}
				
				// Bottom-Right Corner
				else if(i == dimension-1 && j == dimension-1) {
					if(position == board[i][j-1] || position == board[i-1][j]) {
						return;
					}
				}
				
				// Top-Inner
				else if(i == 0 && j == 1) {
					if(position == board[i][j+1]) {
						return;
					}
				} 
				
				// Left-Inner
				else if(i == 1 && j == 1) {
					if(position == board[i+1][j]) {
						return;
					}
				} 
				
				// Right-Inner
				else if(i == 1 && j == dimension-1) {
					if(position == board[i+1][j]) {
						return;
					}
				} 
				
				// Bottom-Inner
				else if(i == dimension-1 && j == 1) {
					if(position == board[i][j+1]) {
						return;
					}
				} 	
			}
		}
		
		// Otherwise no other moves available
		System.out.println("End of game");
		endGame = true;
	}
	
	public boolean checkGameOver() {
		return endGame;
	}
	
	public boolean checkValidPlay() {
		return validPlay;
	}
	
}
