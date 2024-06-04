import java.awt.Point;

public class TicTacToeD {
    
    public static void main(String[] args) {
        TicTacToe game = new TicTacToeGame();
        TicTacToe.BoardChoice[][] board = game.getGameGrid();
        
        // Player X makes first move
        game.choose(TicTacToe.BoardChoice.X, 1, 1);
        board = game.getGameGrid();
        printBoard(board);
        
        // Player O makes second move
        game.choose(TicTacToe.BoardChoice.O, 0, 0);
        board = game.getGameGrid();
        printBoard(board);
        
        // Player X makes third move
        game.choose(TicTacToe.BoardChoice.X, 0, 1);
        board = game.getGameGrid();
        printBoard(board);
        
        // Player O makes fourth move
        game.choose(TicTacToe.BoardChoice.O, 2, 2);
        board = game.getGameGrid();
        printBoard(board);
        
        // Player X makes fifth move
        game.choose(TicTacToe.BoardChoice.X, 2, 0);
        board = game.getGameGrid();
        printBoard(board);
        
        // Player O makes sixth move, should be invalid
        boolean validMove = game.choose(TicTacToe.BoardChoice.O, 2, 0);
        System.out.println("Valid move? " + validMove);
        board = game.getGameGrid();
        printBoard(board);
        
        // Player O makes sixth move, should be valid
        validMove = game.choose(TicTacToe.BoardChoice.O, 1, 0);
        System.out.println("Valid move? " + validMove);
        board = game.getGameGrid();
        printBoard(board);
        
        // Game should be over
        System.out.println("Game over? " + game.gameOver());
        System.out.println("Winner: " + game.getGameState());
        
        // Print out moves
        Point[] moves = game.getMoves();
        for (int i = 0; i < moves.length; i++) {
            System.out.println("Move " + (i+1) + ": " + moves[i].getX() + ", " + moves[i].getY());
        }
    }
    
    private static void printBoard(TicTacToe.BoardChoice[][] board) {
        System.out.println("-------");
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("-------");
        }
    }

}
