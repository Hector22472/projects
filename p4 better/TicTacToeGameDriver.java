import java.util.Scanner;

/**
*
*The TicTacToeGameDriver class is a driver program for the TicTacToeGame class,
*allowing users to play Tic Tac Toe against each other. It prompts users to enter
*the row and column they want to place their mark in, and prints the current game
*state after each turn.
*/

public class TicTacToeGameDriver {

/**
 * The main method starts the game and prompts the user to enter their move.
 * It also prints the current game state after each turn, and allows users to play
 * multiple games in a row if they choose to do so.
 *
 * @param args The command line arguments (not used).
 */
public static void main(String[] args) {
    TicTacToeGame game = new TicTacToeGame();
    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to Tic-Tac-Toe!");

    while (true) {
        System.out.println();
        System.out.println("Current game state:");
        printBoard(game.getGameGrid());

        if (game.getGameState() == TicTacToeGame.GameState.IN_PROGRESS) {
            System.out.println("It's " + game.getLastPlayer() + "'s turn.");
            System.out.print("Enter row (0-2): ");
            int row = scanner.nextInt();
            System.out.print("Enter column (0-2): ");
            int col = scanner.nextInt();
            if (game.choose(game.getLastPlayer(), row, col)) {
                System.out.println("Move successful!");
            } else {
                System.out.println("Invalid move. Please try again.");
            }
        } else {
            if (game.getGameState() == TicTacToeGame.GameState.X_WON) {
                System.out.println("X has won the game!");
            } else if (game.getGameState() == TicTacToeGame.GameState.O_WON) {
                System.out.println("O has won the game!");
            } else if (game.getGameState() == TicTacToeGame.GameState.TIE) {
                System.out.println("It's a tie game!");
            }
            System.out.print("Would you like to play again? (y/n) ");
            String choice = scanner.next().toLowerCase();
            if (choice.equals("y")) {
                game.newGame();
            } else {
                break;
            }
        }
    }

    scanner.close();
}

/**
 * The printBoard method takes a two-dimensional array of BoardChoice enums and prints 
 * it out as a Tic Tac Toe board. It prints the value of each element in the array followed
 * by a space character.
 *
 * @param board The two-dimensional array of BoardChoice enums to be printed.
 */
private static void printBoard(TicTacToeGame.BoardChoice[][] board) {
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board.length; j++) {
            System.out.print(board[i][j] + " ");
        }
        System.out.println();
    }
}

}
