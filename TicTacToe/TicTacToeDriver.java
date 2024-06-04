import java.util.Scanner;

public class TicTacToeDriver {

    private static String name;
    private static Scanner scnr = new Scanner(System.in);

    public static void printMenu(){

        String dashes = String.format("%1$-40s", "-").replace(' ', '-');
        System.out.print("\nEnter your name to play TicTacToe: ");
        name = scnr.nextLine();
        System.out.println(name + ", welcome to TicTacToe,!");
        System.out.println(dashes);
        System.out.println("TicTacToe Menu");
        System.out.println(dashes);
        System.out.println("(X)Press to play as");
        System.out.println("(O)Press to play as");
        System.out.println("(Q)uit");
        System.out.println(dashes);

    }

    public static void main(String [] args){
        TicTacToeGame game = new TicTacToeGame();
        printMenu();
        Scanner scnr = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter your choice: ");
        

            
        }
    }

}
