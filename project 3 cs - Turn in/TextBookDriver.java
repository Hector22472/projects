/*
 * 
 * @author Hector Mendez-Garcia
 * Cs121 Spring 2023
 * @version 1.0
 */
import java.util.Scanner;

public class TextBookDriver {

    // TextBook and Scanner objects used throughout the program
    private static TextBook textbook = new TextBook();
    private static Scanner scanner = new Scanner(System.in);
    // String representing the current user's name
    private static String author;

    /**
    * Prints the menu options for the TextBook application.
    *
    */
    public static void printMenu() {
        String dashes = String.format("%1$-40s", "-").replace(' ', '-');
        System.out.print("\nEnter your name to enter TextBook: ");
        author = scanner.nextLine();
        System.out.println(author + ", welcome to TextBook, the totally-text social media site!");
        System.out.println(dashes);
        System.out.println("TextBook Site Menu");
        System.out.println(dashes);
        System.out.println("(P)rint TextBook posts");
        System.out.println("(A)dd new post");
        System.out.println("(D)elete a post");
        System.out.println("(C)omment on a post");
        System.out.println("(R)ead a post and its comments");
        System.out.println("(Q)uit");
        System.out.println(dashes);
        System.out.print("Please enter a command (press 'm' for Menu): ");
    }

    /**
    * The main method for the TextBook application. Allows users to interact with
    * the social media site through a command-line interface.
    * 
    * @param args arguments for the application.
    *
    */
    public static void main(String[] args) {
        char menu;
        boolean loop = true;

        printMenu(); // print menu initially

        while (loop) {
            switch (menu = scanner.nextLine().toUpperCase().charAt(0)) {
                case 'P':
                    System.out.println(textbook.toString());
                    System.out.println("Please enter a command (press 'm' for Menu): ");
                    break;
                case 'A':
                    System.out.print("Enter the text for your new post: ");
                    String postText = scanner.nextLine();
                    textbook.addPost(author, postText);
                    System.out.println("\nPlease enter a command (press 'm' for Menu): ");
                    break;
                case 'D':
                    System.out.print("Enter the index of the post to delete: ");
                    int deleteIndex = scanner.nextInt();
                    scanner.nextLine(); 
                    if (deleteIndex >= 0 && deleteIndex < textbook.getPostCount()) {
                        textbook.removePost(deleteIndex);
                    } else {
                        System.out.println("Invalid index.");
                    }
                    System.out.println("\nPlease enter a command (press 'm' for Menu): ");
                    break;
                case 'C':
                    System.out.print("Enter the index of the post to comment on: ");
                    int commentIndex = scanner.nextInt();
                    scanner.nextLine(); 
                    if (commentIndex >= 0 && commentIndex < textbook.getPostCount()) {
                        System.out.print("Enter your comment: ");
                        String commentText = scanner.nextLine();
                        textbook.addComment(commentIndex, author, commentText);
                    } else {
                        System.out.println("Invalid index.");
                    }
                    System.out.println("\nPlease enter a command (press 'm' for Menu): ");
                    break;
                case 'R':
                    System.out.print("Enter the index of the post to read: ");
                    int readIndex = scanner.nextInt();
                    scanner.nextLine();
                    if (readIndex >= 0 && readIndex < textbook.getPostCount()) {
                        System.out.println(textbook.getPostString(readIndex).toString());
                    } else {
                        System.out.println("Invalid index.");
                    }
                    System.out.println("Please enter a command (press 'm' for Menu): ");
                    break;
                case 'Q':
                    loop = false;
                    break;
                case 'M':
                    // print menu again
                    printMenu();
                    break;
                default:
                    System.out.println("Invalid Selection.");
                    break;
            }
        }
        scanner.close();
        System.out.println("Goodbye, " + author + "!");
    }
}

