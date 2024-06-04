import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * Manages a grid of LiteBriteButtons
 * @author Hector Mendez-Garcia CS121-1 Spring 2023
 */
public class LiteBriteBoardPanel extends JPanel {
    private LitePegButton[][] board;
    private final int BUTTON_DIMENSION = 30;

    /**
     * Initialize board with given dimensions and populate with LitePegButtons.
     * @param width columns
     * @param height rows
     */
    public LiteBriteBoardPanel(int width, int height) {
        this.setLayout(new GridLayout(height, width));
        board = new LitePegButton[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                board[row][col] = new LitePegButton();
                board[row][col].setPreferredSize(new Dimension(BUTTON_DIMENSION, BUTTON_DIMENSION));
                this.add(board[row][col]); 
            }
        }
    }

    /**
     * Call every button's reset() to return to all black.
     */
    public void reset() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col].reset();
            }
        }
    }
}

