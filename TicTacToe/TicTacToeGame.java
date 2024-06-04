import java.awt.Point;
import java.util.Scanner;

public class TicTacToeGame implements TicTacToe {

    Scanner scnr = new Scanner(System.in);
    private char [][] gameBoard = {{' ', '|', ' ', '|', ' '},
    {'-', '+', '-', '+', '-'},
    {' ', '|', ' ', '|', ' '},
    {'-', '+', '-', '+', '-'},
    {' ', '|', ' ', '|', ' '}};

    BoardChoice currentPlayer;
    private Point[] moves;
    private int numMoves;
    private GameState gameState;

    public TicTacToeGame() {
        newGame();
    }

    public void printGame() {
        for(char[] row : gameBoard) {
            for(char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
    

public void newGame() {
    // Reset the game board
    for(int i = 0; i < gameBoard.length; i++) {
        for(int j = 0; j < gameBoard[i].length; j++) {
            gameBoard[i][j] = ' ';
        }
    }
    
    // Print out the game board
    for(char[] row : gameBoard) {
        for(char c : row) {
            System.out.print(c);
        }
        System.out.println();
        printGame();
    }
}

    

    @Override
    public boolean choose(BoardChoice player, int row, int col) {
        char symbol = player.name().charAt(0);
        if (gameState != GameState.IN_PROGRESS || row < 0 || row > 2 || col < 0 || col > 2 || gameBoard[row*2][col*2] != ' ') {
            return false;
        }
        switch(row){
            case 0:
                switch(col){
                    case 0:
                        gameBoard[0][0] = symbol;
                        break;
                    case 1:
                        gameBoard[0][2] = symbol;
                        break;
                    case 2:
                        gameBoard[0][4] = symbol;
                        break;
                }
                break;
            case 1:
                switch(col){
                    case 0:
                        gameBoard[2][0] = symbol;
                        break;
                    case 1:
                        gameBoard[2][2] = symbol;
                        break;
                    case 2:
                        gameBoard[2][4] = symbol;
                        break;
                }
                break;
            case 2:
                switch(col){
                    case 0:
                        gameBoard[4][0] = symbol;
                        break;
                    case 1:
                        gameBoard[4][2] = symbol;
                        break;
                    case 2:
                        gameBoard[4][4] = symbol;
                        break;
                }
                break;
        }

        getGameGrid();
        return true;
    }
    


    @Override
    public boolean gameOver() {
        GameState state = getGameState();
        return state == GameState.X_WON || state == GameState.O_WON || state == GameState.TIE;
    }
 

    public Point[] getMoves() {
        Point[] copy = new Point[numMoves];
        for (int i = 0; i < numMoves; i++) {
            copy[i] = new Point(moves[i]);
        }
        return copy;
    }
    

    @Override
    public BoardChoice[][] getGameGrid() {
        BoardChoice[][] grid = new BoardChoice[3][3];
        for(int i = 0; i < gameBoard.length; i += 2) {
            for(int j = 0; j < gameBoard[i].length; j += 2) {
                char c = gameBoard[i][j];
                if(c == 'X') {
                    grid[i/2][j/2] = BoardChoice.X;
                } else if(c == 'O') {
                    grid[i/2][j/2] = BoardChoice.O;
                } else {
                    grid[i/2][j/2] = BoardChoice.OPEN;
                }
            }
        }
        
        // Print out the game grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    
        boolean validInput = false;
        while(!validInput) {
            System.out.println("Enter X or O:");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("X")) {
                validInput = true;
                return grid;
            } else if(input.equalsIgnoreCase("O")) {
                validInput = true;
                return grid;
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
        return null; // unreachable code, as one of the above return statements will be executed
    }
    
    
    

    public GameState getGameState() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0] == gameBoard[i][2] && gameBoard[i][2] == gameBoard[i][4]) {
                if (gameBoard[i][0] == 'X') {
                    return GameState.X_WON;
                } else if (gameBoard[i][0] == 'O') {
                    return GameState.O_WON;
                }
            }
        }   

    // Check columns
        for (int i = 0; i < 3; i++) {
            if (gameBoard[0][i] == gameBoard[2][i] && gameBoard[2][i] == gameBoard[4][i]) {
                if (gameBoard[0][i] == 'X') {
                    return GameState.X_WON;
                } else if (gameBoard[0][i] == 'O') {
                    return GameState.O_WON;
                }
            }
        }

    // Check diagonals
        if ((gameBoard[0][0] == gameBoard[2][2] && gameBoard[2][2] == gameBoard[4][4])
                || (gameBoard[0][4] == gameBoard[2][2] && gameBoard[2][2] == gameBoard[4][0])) {
            if (gameBoard[2][2] == 'X') {
                return GameState.X_WON;
            } else if (gameBoard[2][2] == 'O') {
                return GameState.O_WON;
            }
        }

    // Check for tie
        boolean isTie = true;
        for (int i = 0; i < 5; i += 2) {
            for (int j = 0; j < 5; j += 2) {
                if (gameBoard[i][j] == ' ') {
                    isTie = false;
                }
            }
        }
        if (isTie) {
            return GameState.TIE;
        }

    // Game is still in progress
        return GameState.IN_PROGRESS;
    }
}
