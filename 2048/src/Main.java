import javax.swing.JFrame;

public class Main extends JFrame {

	public static void main(String[] args) {
		
		// Frame Setup
		JFrame frame = new JFrame("Tkha 2048");
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// MVC Design Game
		Game game = new Game();
		
		frame.add(game);
		frame.setVisible(true);
	}

}
