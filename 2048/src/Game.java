import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * @author Thanh Kha
 * 
 * MVC Design:
 * - This Class (Game) manages the GUI (user controls and game outputs). 
 * - The data manipulation class (Board) manages the gameboard accordingly with
 * 	 the user's action in game. 
 */

public class Game extends JPanel implements KeyListener, ActionListener {
	
	/**
	 * MVC Design
	 * 
	 * This Class (Game) Controls the GUI. 
	 * The data manipulations
	 */
	// Globals
	private final int dimension = 4;
	private Board board;
	private JLabel[] labels = new JLabel[16];
	private JButton reset, quit;
	private JLabel highScore, validMoves, end, move;
	private JPanel controlPanel, boardPanel, labelPanel, buttonPanel;
	private boolean play, quitGame, restartGame;
	
	// Constructor
	public Game() {
		quitGame = false;
		restartGame = false;
		play = true;
		
		// Main Panel Layout 
		this.setLayout(new BorderLayout());
		
		// Listener
		addKeyListener(this);
		setFocusable(true);

		// Board Setup
		setBoard();
		setControl();
	}
	
	// Control Panel
	public void setControl() {
		
		// Control Panel Container
		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		controlPanel.setBackground(Color.WHITE);
		
		// Label Panel Container
		labelPanel = new JPanel(new GridLayout(2,1));
		labelPanel.setBackground(Color.WHITE);
		
		// Button Panel Container
		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setBackground(Color.WHITE);
		
		/** Buttons **/
		// Reset Button
		reset = new JButton("New Game"); 
		reset.addActionListener(this);
		reset.addKeyListener(this);
		buttonPanel.add(reset);
		
		// Quit Button
		quit = new JButton("Quit");
		quit.addActionListener(this);
		quit.addKeyListener(this);
		buttonPanel.add(quit);
		
		/** Labels **/
		// Score
		highScore = new JLabel("Score: " + board.getScore(), SwingConstants.CENTER);
		labelPanel.add(highScore);
		
		// Valid Inputs
		validMoves = new JLabel("Valid Moves: " + board.getMoves(), SwingConstants.CENTER);
		labelPanel.add(validMoves);
		
		// Valid Move
		move = new JLabel(" ", SwingConstants.CENTER);
		controlPanel.add(move, BorderLayout.CENTER);
		
		// Add buttonPanel to control Panel
		controlPanel.add(labelPanel, BorderLayout.LINE_START);
		controlPanel.add(buttonPanel, BorderLayout.LINE_END);
		
		// Add to Main Panel
		this.add(controlPanel, BorderLayout.NORTH);
		
	}
	
	// Board
	public void setBoard() {
		board = new Board();
		
		// Panel Setup
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(4,4, 2, 2));
		boardPanel.setSize(500, 500);
		
		// Setup Labels and add to JPanel
		for(int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel("" + (i+1), SwingConstants.CENTER);
			labels[i].setOpaque(true);
			labels[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			labels[i].setBackground(Color.WHITE);
			labels[i].setFont(labels[i].getFont().deriveFont(25.0f));
			boardPanel.add(labels[i]);
		}
		boardPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.add(boardPanel, BorderLayout.CENTER);
		updateBoard();
	}
	
	// Update Board 
	public void updateBoard() {
		int count = -1;
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				count++;
				int value = board.getValue(i, j);
				if (value == 0) { labels[count].setBackground(new Color(200, 250, 250)); }
				if (value == 2) { labels[count].setBackground(new Color(150, 230, 230)); }
				if (value == 4) { labels[count].setBackground(new Color(50, 230, 230)); }
				if (value == 8) { labels[count].setBackground(new Color(0, 190, 230)); }
				if (value == 16) { labels[count].setBackground(new Color(0, 150, 230)); }
				if (value == 32) { labels[count].setBackground(new Color(150, 250, 150)); }
				if (value == 64) { labels[count].setBackground(new Color(50, 250, 50)); }
				if (value == 128) { labels[count].setBackground(new Color(0, 200, 0)); }
				if (value == 256) { labels[count].setBackground(new Color(250, 150, 150)); }
				if (value == 512) { labels[count].setBackground(new Color(250, 50, 50)); }
				if (value == 1024) { labels[count].setBackground(new Color(220, 100, 100)); }
				if (value == 2048) { labels[count].setBackground(new Color(250, 250, 250)); }
				labels[count].setText("" + board.getValue(i, j));
			}
		}
		

	}
	
	// Update Score
	public void updateScore() {
		highScore.setText("Score: " + board.getScore());
	}
	
	public void updateMoves() {
		validMoves.setText("Valid Moves: " + board.getMoves());
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	// Player Key Press
	@Override
	public void keyPressed(KeyEvent e) {
		
		// Restart/Quit Option
		if(e.getKeyCode() == KeyEvent.VK_R || e.getKeyCode() == KeyEvent.VK_Q) {
	 		int key = e.getKeyCode();
			switch(key) {
				case KeyEvent.VK_R:
					if(!restartGame) {
						restartGame = true;
						quitGame = false;
						System.out.println("Press (R) to confirm");
					} else
						restart();
					break;
				case KeyEvent.VK_Q:
					if(!quitGame) {
						quitGame = true;
						restartGame = false;
						System.out.println("Press (Q) to confirm"); 
					} else 
						quit();
			}
		} else if (play){
	 		int key = e.getKeyCode();
			switch(key) {
				case KeyEvent.VK_UP:
					board.moveUp();
					break;
				case KeyEvent.VK_DOWN:
					board.moveDown();
					break;
				case KeyEvent.VK_LEFT:
					board.moveLeft();
					break;
				case KeyEvent.VK_RIGHT:
					board.moveRight();
					break;
				default:
					break;
			}
			restartGame = false;
			quitGame = false;
			reset.setText("New Game");
			quit.setText("Quit");
			board.printBoard();
			updateScore();
			updateMoves();
			updateBoard();
			
			if(board.checkGameOver() == true) {
				play = false;
				move.setText("Game Over!");
				
			} else if (board.checkValidPlay() == false) {
				move.setText("Invalid Move!");
				
			} else {
				move.setText("Valid Move!");
				
			} 
		}
	}
	
	// Not used
	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override // Game Reset
	public void actionPerformed(ActionEvent e) {
		
		// Reset Clicked
		if(e.getSource() == reset && reset.getText() == "New Game") {
			reset.setText("Are you sure?");
			quit.setText("Quit");
			
		} else if(e.getSource() == reset && reset.getText() == "Are you sure?") {
			restart();
			
		// Quit Clicked
		} else if(e.getSource() == quit && quit.getText() == "Quit") {
			quit.setText("Are you sure?");
			reset.setText("New Game");
			
		// Remove Board panel
		} else if(e.getSource() == quit && quit.getText().equals("Are you sure?")) {
			quit();


		}
	}
	
	public void restart() {
		play = true;
		quit.setText("Quit");
		reset.setText("New Game");
		this.remove(boardPanel);
		this.remove(controlPanel);
		setBoard();
		setControl();
		
	}
	
	public void quit() {
		play = false;
		reset.setText("New Game");
		
		// Remove all panels from main
		this.removeAll();			
		
		// Add goodbye
		labelPanel.setLayout(new GridLayout(4,1));
		end = new JLabel("Thanks for playing!", SwingConstants.CENTER);
		labelPanel.add(end);
		
		// Add new labelPanel to main
		this.add(labelPanel, BorderLayout.CENTER);
	
		// Display - this somehow procs the repaints, discovered by myself. 
		validMoves.setText("Valid Moves: " + board.getMoves() + " ");
		
		System.out.println("Thanks for Playing!");
	}


}
