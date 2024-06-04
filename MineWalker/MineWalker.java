import javax.swing.JFrame;

/**
 * Driver class for MineWalker activities.
 * This class creates a JFrame and adds a MineWalkerPanel to it to display the game.
 * @author Hector Mendez-Garcia
 * CS-121 Spring 2023
 * @version 1.0
 */
public class MineWalker {
	public static void main(String[] args) {
		JFrame frame = new JFrame("MineWalker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MineWalkerPanel panel = new MineWalkerPanel(10, 10);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}