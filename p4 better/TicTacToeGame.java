import java.awt.*;
import java.util.Arrays;
/**
 * The TicTacToeGame class implements the TicTacToe interface and represents a game of Tic Tac Toe.
 * The game board is represented by a 2D array of BoardChoice enums, where each cell represents a
 * square on the Tic Tac Toe board and can be either X, O, or OPEN (empty). Players can make moves
 * by selecting a row and column on the board. The game ends when one player wins or when the board
 * is full (a tie).
 *
 * @author Hector
 * @version CS121 Spring 2023
 * 04/15/2023
 */

 
public class TicTacToeGame implements TicTacToe {
    /**
    * A 2D array representing the Tic Tac Toe game board.
    */
    private final BoardChoice[][] board;
    private Point[] point;
    private GameState gameState;
    private BoardChoice lastPlayer;
    private int nextindex;

    /**
    * Constructor for a new game of Tic Tac Toe. Initializes the game board to all OPEN cells, 
    * sets the point array to an empty array, and sets the game state to IN_PROGRESS.
    */
    public TicTacToeGame() {
        board = new BoardChoice[3][3];
        point = new Point[0];
        gameState = TicTacToe.GameState.IN_PROGRESS;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = TicTacToe.BoardChoice.OPEN;
            }
        }
    }

    /**
    * Starts a new game by resetting the game board to all OPEN cells, setting the point array 
    * to an empty array, setting the game state to IN_PROGRESS, and setting the last player to OPEN.
    */
    public void newGame() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = TicTacToe.BoardChoice.OPEN;
            }
        }
        point = new Point[0];
        gameState = TicTacToe.GameState.IN_PROGRESS;
        lastPlayer = BoardChoice.OPEN;
        nextindex = 0;
    }

    /**
    * Allows a player to make a move by selecting a row and column on the game board. If the game 
    * is over or the row and column are out of bounds, returns false. If the player is trying to 
    * make two consecutive moves, returns false.
    * 
    * @param player the BoardChoice enum representing the player making the move (X or O)
    * @param row the row of the selected cell
    * @param col the column of the selected cell
    * @return true if the move was successful, false otherwise
    */
    public boolean choose(BoardChoice player, int row, int col) {
        if (gameOver() || row > 2 || col > 2) {
            return false;
        }
        if (lastPlayer == BoardChoice.X && player == BoardChoice.X) {
            return false;
        }
        if (lastPlayer == BoardChoice.O && player == BoardChoice.O) {
            return false;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (i == row && j == col) {
                    if (board[i][j] == TicTacToe.BoardChoice.OPEN) {
                        board[i][j] = player;
                        lastPlayer = player;
                        Point[] pointCopy = Arrays.copyOf(point, nextindex + 1);
                        point = Arrays.copyOf(pointCopy, nextindex + 1);
                        point[nextindex++] = new Point(row, col);
                        gameOver();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
    * 
    * This method checks if the game is over by checking if any player has won or if the game is a tie.
    * @return true if the game is over, false otherwise
    */
    public boolean gameOver() {
        if (win(BoardChoice.X)) {
            gameState = GameState.X_WON;
            return true;
        } else if (win(BoardChoice.O)) {
            gameState = GameState.O_WON;
            return true;
        } else if (nextindex == 9) {
            gameState = GameState.TIE;
            return true;
        }
        return false;
    }

    /**
    * 
    * This method checks if a player has won the game.
    * @param player the player to check for a win
    * @return true if the player has won, false otherwise
    */
    public boolean win(BoardChoice player) {
        return (((board[0][0] == player) && (board[0][1] == player) && (board[0][2] == player)) ||
                ((board[1][0] == player) && (board[1][1] == player) && (board[1][2] == player)) ||
                ((board[2][0] == player) && (board[2][1] == player) && (board[2][2] == player)) ||
                ((board[0][0] == player) && (board[1][0] == player) && (board[2][0] == player)) ||
                ((board[0][1] == player) && (board[1][1] == player) && (board[2][1] == player)) ||
                ((board[0][2] == player) && (board[1][2] == player) && (board[2][2] == player)) ||
                ((board[0][0] == player) && (board[1][1] == player) && (board[2][2] == player)) ||
                ((board[2][0] == player) && (board[1][1] == player) && (board[0][2] == player)));
    }

    /**
    * 
    * This method gets the current state of the game.
    * @return the current state of the game
    */
    public GameState getGameState() {
        return gameState;
    }

    /**
    * 
    * This method gets a copy of the game grid.
    * @return a copy of the game grid
    */
    public BoardChoice[][] getGameGrid() {
        BoardChoice[][] copy = new BoardChoice[3][3];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board.length);
        }
        return copy;
    }

    /**
    * 
    * This method gets an array of all the moves made in the game so far.
    * @return an array of all the moves made in the game so far
    */
    public Point[] getMoves() {
        return Arrays.copyOf(point, point.length);
    }

    /**
    * 
    * This method gets the last player who made a move.
    * @return the last player who made a move
    */
    public BoardChoice getLastPlayer(){
        return lastPlayer;
    }

}
