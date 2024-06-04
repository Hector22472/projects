/*The Post class represents a single post with comments.
 * @author Hector Mendez-Garcia
 * Cs121 Spring 2023
 * version 1.0
 */

import java.io.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Post implements PostInterface {

    private int postID; //ID of post
    private Instant timestamp; //The time and date the post was made
    private String author; //The name of the author who created that post
    private String text; //The text you leave on the post
    private ArrayList<String> comments; //The text you use to comment on the post

    /**
    * Constructs a Post object with the given id, author, and text.
    * Initializes the post's timestamp to the current time and an empty list of comments.
    * Writes the post and its comments to a file.
    *
    * @param id The unique identifier for the post
    * @param author The author of the post
    * @param text The text content of the post
    *
    */
    public Post(int id, String author, String text) {
        postID = id;
        timestamp = Instant.now();
        this.author = author;
        this.text = text;
        comments = new ArrayList<>();
        
        try (PrintWriter writer = new PrintWriter(getFilename())) {
            writer.println(toStringPostOnly());
            for (String comment : comments) {
                writer.println(comment);
            }
        } catch (Exception e) {
            System.out.println("Error could not find file ");
        }
    }

    /**
    * Constructs a Post object with the given id.
    * Initializes the list of comments by reading them from a file.
    *
    * @param id The unique identifier for the post
    *
    */
    public Post(int id) {
        postID = id;
        
        comments = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(getFilename()))) {
            String postLine = scanner.nextLine();
            String[] postParts = postLine.split(" ", 4);
            postID = Integer.parseInt(postParts[0]);
            timestamp = Instant.parse(postParts[1]);
            author = postParts[2];
            text = postParts[3];
            while (scanner.hasNextLine()) {
                comments.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error file not found");
        }
    }

    /**
    * Adds a comment to the post.
    * Writes the updated post and comments to a file.
    *
    * @param author The author of the comment
    * @param text The text content of the comment
    *
    */
    public void addComment(String author, String text) {
        Instant timestamp = Instant.now();
        String comment = timestamp.toString() + " " + author + " " + text;
        comments.add(comment);
        
        try (PrintWriter writer = new PrintWriter(getFilename())) {
            writer.println(toStringPostOnly());
            for ( String comm : comments) {
                writer.println(comm);
            }
        } catch (Exception e) {
            System.out.println("Error file not found ");
        }

    }

    /**
    * @return A string representation of the post and its comments
    *
    */
    public String toString() {
        String postString = "Post:\n" + toStringPostOnly() + "\n";
        String commentsString = "Comments:\n";
        for (String comment : comments) {
            commentsString += comment + "\n";
        }
        return postString + commentsString;
    }

    /**
    @return a string representation of the post, including only its ID, timestamp, author, and text.
    *
    */
    public String toStringPostOnly() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        String formattedTimestamp = formatter.format(timestamp);
        String postIDString = String.format("%05d", postID);
        return postIDString + " " + formattedTimestamp + " " + author + " " + text;
    }


    /**
    @return the filename associated with this post
    *
    */
    public String getFilename() {
        return "Post-" + String.format("%05d", postID) + ".txt";
    }

    /**
    @return the ID of the post
    *
    */
    public int getPostID() {
        return postID;
    }

   /**
    @return the text of the post
    *
    */
    public String getText() {
        return text;
    }

    /**
    @return the timestamp of the post
    *
    */
    public Instant getTimestamp() {
        return timestamp;
    }

   /**
    @return the author of the post
    *
    */
    public String getAuthor() {
        return author;
    }
    
}
