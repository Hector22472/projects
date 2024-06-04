/*
 * The TextBook class implements the TextBookInterface and represents a collection
 * of Post objects with the ability to add and remove posts and comments.
 * @author Hector Mendez-Garcia
 * Cs121 Spring 2023
 * @version 1.0
 */


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TextBook implements TextBookInterface {

    private ArrayList<Post> posts; // An ArrayList of Post objects
    private int lastID; // The ID number of the last post created
    
    /**
     * Constructor that creates a new TextBook object.
     * It initializes the posts ArrayList and reads from a file to set the last post ID number.
     * 
     */
    public TextBook() {
        posts = new ArrayList<>();
        lastID = 0;
        
        try {
            // Read from the file of post IDs
            Scanner scanner = new Scanner(new File(POST_LIST_FILENAME));
            while (scanner.hasNextLine()) {
                int id = Integer.parseInt(scanner.nextLine());
                Post post = new Post(id);
                posts.add(post);
                // Update the lastID to the highest ID number read from the file
                if (id > lastID) {
                    lastID = id;
                }
            }
            scanner.close();
        } catch (Exception e) {
            // Handle the case where the file is not found
            System.out.println("Error file not found");
        }
    }    
    
    /**
     * @return The ID number of the last post created
     * 
     */
    public int getLastID() {
        return lastID;
    }
    
    /**
     * @return The number of posts in the TextBook
     * 
     */
    public int getPostCount() {
        return posts.size();
    }
    
    /**
     * Returns the string representation of a Post object at the given index.
     * 
     * @param index The index of the Post to retrieve
     * 
     * @return The string representation of the Post at the given index
     * 
     */
    public String getPostString(int index) {
        if (index >= 0 && index < posts.size()) {
            return posts.get(index).toString();
        } else {
            return null;
        }
    }
    
    /**
     * Adds a new post with the given author and text to the TextBook.
     * It also updates the lastID and writes the new post ID to a file.
     * 
     * @param author The author of the new post
     * @param text The text of the new post
     * 
     * @return True if the post was successfully added, false otherwise
     * 
     */
    public boolean addPost(String author, String text) {
        lastID++;
        Post post = new Post(lastID, author, text);
        posts.add(post);
        
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(POST_LIST_FILENAME), true))) {
            // Write the new post ID to the file
            writer.println(lastID);
        } catch (FileNotFoundException e) {
            // Handle the case where the file is not found
            System.out.println("Error file not found");
            return false;
        }
        
        return true;
    }    

    /**
    This method removes a post at the given index and updates the post list file.
    If the index is out of bounds, it returns null.
    @param index the index of the post to be removed
    *
    @return the removed post if successful, null if the index is out of bounds
    *
    */
    public Post removePost(int index) {
        if (index >= 0 && index < posts.size()) {
            Post post = posts.remove(index);
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(POST_LIST_FILENAME, false));
                for (Post p : posts) {
                    writer.write(String.valueOf(p.getPostID()) + "\n");
                }
                writer.close();
            } catch (Exception e) {
                System.out.println("Error file not found");
            }
            return post;
        } else {
            return null;
        }
    }

    /**
    This method adds a comment to a post at the given index.
    If the index is out of bounds, it returns false.
    @param postIndex the index of the post to add a comment to
    @param author the author of the comment
    @param text the text of the comment
    *
    @return true if successful, false if the index is out of bounds
    *
    */
    public boolean addComment(int postIndex, String author, String text) {
        if (postIndex >= 0 && postIndex < posts.size()) {
            Post post = posts.get(postIndex);
            // Create a comment string by concatenating the author and text inputs
            post.addComment(author, text);
            return true;
        } else {
            return false;
        }
    }    

   /**
    This method returns a string representation of the TextBook object, including
    the number of posts and their IDs and titles.
    @return a string representation of the TextBook object
    *
    */
    public String toString() {
        String result = "TextBook contains " + this.posts.size() + " posts:\n";
        for (int i = 0; i < posts.size(); i++) {
            result += i + " - " + posts.get(i).toStringPostOnly() + "\n";
        }
        return result;
    }


   /**
    This method returns a new ArrayList of the posts in the TextBook object.
    @return a new ArrayList of the posts in the TextBook object
    *
    */
    public ArrayList<Post> getPosts() {
        return new ArrayList<>(posts);
    }
}

