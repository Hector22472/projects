import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

/**
 * The MineWalkerPanel class represents the panel that contains the Mine Walker game.
 * It contains a grid of TileButtons and a path that the player must follow without hitting any mines.
 * The player starts at the top left of the grid and must move to the bottom right.
 * The class also contains a control panel to start a new game and adjust the percentage of mines,
 * as well as a color legend for the different tile button colors.
 * 
 * @author Hector Mendez-Garcia
 * CS-121 Spring 2023
 * 
 */

public class MineWalkerPanel extends JPanel{

    //Instantiates all necessary variables for the code
    private static final long serialVersionUID = 1L;
    private ArrayList<Point> path;
    private TileButton[][] grid;
    private ActionListener listener;
    private int width;
    private int height;
    private int lives;
    private int minePercent;
    private Random rand;
    private JLabel numLives;
    private Color[] mineColor = {Color.green, Color.yellow, Color.orange, Color.red, Color.black};
    private JSlider controller;
    private int score;
    private JLabel scoreDisplay;
    private Point prevPoint = new Point(0,0);

    /**
     * Constructor for the MineWalkerPanel class.
     * @param width The width of the grid.
     * @param height The height of the grid.
     */
    public MineWalkerPanel(int width, int height){
        //New Layout 
        this.setLayout(new BorderLayout(width, height));
        this.listener = new MineListener();
        this.width = width;
        this.height = height;
        this.lives = 5;
        this.minePercent = 30;
        this.score = lives * (width * height);
        this.rand = new Random();

        //Creates new JPanel
        JPanel gamePanel = new JPanel();

        //New JPanel Layout
        gamePanel.setLayout(new GridLayout(width, height));
        gamePanel.setPreferredSize(new Dimension(600,600));
        grid = new TileButton[width][height];

        //Creates new tile buttons for the game
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                grid[i][j] = new TileButton(i, j);
                grid[i][j].setHidden(true);
                grid[i][j].setBackground(Color.lightGray);
                grid[i][j].setPreferredSize(new Dimension(10, 10));
                gamePanel.add(grid[i][j]);
                grid[i][j].addActionListener(this.listener);
            }
        }

        //Gets a path for the game that will always exist
        this.path = RandomPath.getPath(height);
        for(Point p : path){
            grid[(int) p.getX()][(int) p.getY()].setPath(true);
        }

        //Calculates how many mines to place
        int minesToPlace = (100 - path.size()) * minePercent/100;

        //Sets random mines
        for(int i = 0; i < minesToPlace; i++){
            int num1 = rand.nextInt(10);
            int num2 = rand.nextInt(10);
            while(grid[num1][num2].isPath() && !grid[num1][num2].isMine()){
                num1 = rand.nextInt(10);
                num2 = rand.nextInt(10);
            }

            grid[num1][num2].setMine(true);
        }

        //Checks how many mines are surrounding that space
        for(int i = 0; i < height; i++){ 
            for(int j = 0; j < width; j++){
                int closeMines = 0;
                if(j < 9){
                    if(grid[i][j+1].isMine()){
                        closeMines++;
                    }
                }
                if(i < 9){
                    if(grid[i+1][j].isMine()){
                        closeMines++;
                    }
                }
                if(j > 0){
                    if(grid[i][j-1].isMine()){
                        closeMines++;
                    }
                }
                if(i > 0){
                    if(grid[i-1][j].isMine()){
                        closeMines++;
                    }
                }
                if(grid[i][j].isMine())
                {
                    closeMines = 4;
                }

                grid[i][j].setNumMineNeighbors(closeMines);
                grid[0][0].setHidden(false);
                grid[0][0].setBackground(mineColor[grid[0][0].getNumMineNeighbors()]);
                grid[0][0].setText("X");
                grid[9][9].setBackground(Color.blue);
            }
        }

        this.add(gamePanel);

        //Creates a new JPanel to create a new game
        JPanel controlsPanel = new JPanel();

        //adds a button for a new game
        JButton newGame = new JButton("New Game");
        newGame.addActionListener(new NewGameListener());

        //adds a text area to change the number of mines
        JLabel inputButton = new JLabel("Mine Percent: ");

        JSlider controller = new JSlider(10,100);
        controller.setPaintTrack(true);
        controller.setPaintTicks(true);
        controller.setPaintLabels(true);
        controller.setMajorTickSpacing(10);
        controller.setMinorTickSpacing(5);
        controller.setValue(30);
        this.controller = controller;

        controlsPanel.add(newGame);
        controlsPanel.add(inputButton);
        controlsPanel.add(controller);
        this.add(controlsPanel, BorderLayout.NORTH);

        //Creates a new JPanel for the color legend
        JPanel colorKeyPanel = new JPanel();
        colorKeyPanel.setLayout(new GridLayout(6, 1));

        JButton zero = new JButton("0 Mine Neighbors");
        zero.setBackground(Color.green);
        zero.setEnabled(false);
        JButton one = new JButton("1 Mine Neighbor");
        one.setBackground(Color.yellow);
        one.setEnabled(false);
        JButton two = new JButton("2 Mine Neighbors");
        two.setBackground(Color.orange);
        two.setEnabled(false);
        JButton three = new JButton("3 Mine Neighbors");
        three.setBackground(Color.red);
        three.setEnabled(false);
        JButton mine = new JButton("Exploded Mine");
        mine.setBackground(Color.black);
        mine.setForeground(Color.white);
        mine.setEnabled(false);
        JButton you = new JButton("X <-- You");
        you.setEnabled(false);

        colorKeyPanel.add(zero);
        colorKeyPanel.add(one);
        colorKeyPanel.add(two);
        colorKeyPanel.add(three);
        colorKeyPanel.add(mine);
        colorKeyPanel.add(you);
        this.add(colorKeyPanel, BorderLayout.WEST);

        //New JPanel to display for lives
        JPanel reportPanel = new JPanel();

        this.numLives = new JLabel("Lives: " + lives);
        reportPanel.add(numLives);

        this.scoreDisplay = new JLabel("Score: " + score);
        reportPanel.add(scoreDisplay);

        this.add(reportPanel, BorderLayout.SOUTH);

    }

    /**
    * ActionListener that starts a new game when the "New Game" button is clicked.
    */
    private class NewGameListener implements ActionListener{
        /**
        * actionPerformed method that generates a new game with new randomly placed mines, resets the board,
        * and updates the score and lives display.
        * @param e ActionEvent object representing the button click event.
        */
        public void actionPerformed(ActionEvent e){
            //gets text for minepercent
            minePercent = controller.getValue();

            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    grid[i][j].setHidden(true);
                    grid[i][j].setBackground(Color.lightGray);
                    grid[i][j].setMine(false);
                }
            }

            path.clear();
            path = RandomPath.getPath(height);

            for(Point p : path){
                grid[(int) p.getX()][(int) p.getY()].setPath(true);
            }
            //Calculates how many mines to place
            int minesToPlace = (100 - path.size()) * minePercent/100;
            
            for(int i = 0; i <= minesToPlace; i++){
                int num1 = rand.nextInt(10);
                int num2 = rand.nextInt(10);
                while(grid[num1][num2].isPath() && !grid[num1][num2].isMine()){
                    num1 = rand.nextInt(10);
                    num2 = rand.nextInt(10);
                }
                grid[num1][num2].setMine(true);
            }
            //Goes through all the mines and counts how many mines are surrounding that space
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    int closeMines = 0;
                    if(j < 9){
                        if(grid[i][j+1].isMine()){
                            closeMines++;
                        }
                    }
                    if(i < 9){
                        if(grid[i+1][j].isMine()){
                            closeMines++;
                        }
                    }
                    if(j > 0){
                        if(grid[i][j-1].isMine()){
                            closeMines++;
                        }
                    }
                    if(i > 0){
                        if(grid[i-1][j].isMine()){
                            closeMines++;
                        }
                    }
                    if(grid[i][j].isMine()){
                        closeMines = 4;
                    }
                    grid[i][j].setNumMineNeighbors(closeMines);
                    grid[0][0].setHidden(false);
                    grid[0][0].setBackground(mineColor[grid[0][0].getNumMineNeighbors()]);
                    grid[0][0].setText("X");
                    grid[9][9].setBackground(Color.blue);
                }
            }

            lives = 5;
            numLives.setText("Lives: " + lives);
            prevPoint = new Point(0,0);

            //Makes all spaces back to original
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    grid[i][j].setText("");
                }
            }

            grid[0][0].setText("X");
            score = lives * (width * height);
            scoreDisplay.setText("Score: " + score);
        }
    }

    /**
    * This class implements an ActionListener that listens for when a "New Game" button is clicked.
    * It resets the game board and places mines randomly, then sets the number of mines that surround each space on the board.
    * It also resets the number of lives and score.
    */
    private class MineListener implements ActionListener{
        /**
        * This method is called when a "New Game" button is clicked.
        * It gets the text for mine percent, resets the game board, and places mines randomly.
        * It then sets the number of mines that surround each space on the board.
        * It also resets the number of lives and score.
        *
        * @param e the ActionEvent that triggered this method
        */
        public void actionPerformed(ActionEvent e){
            TileButton click = (TileButton) e.getSource(); //gets the source of the button
            Point clickPoint = new Point(click.getX()/60, click.getY()/60); //gets the spot on the grid where the button is
            if(!this.gameOver()){
                if(((clickPoint.getX() - prevPoint.getX() > 1) || (clickPoint.getY() - prevPoint.getY() > 1)) || ((clickPoint.getX() - prevPoint.getX() < -1) || (clickPoint.getY() - prevPoint.getY() < -1))){
                } //Checks to see if the clicked point isn't next to the previously clicked piece
                else if(((clickPoint.getX() - prevPoint.getX() == 1) && (clickPoint.getY() - prevPoint.getY() == 1)) || ((clickPoint.getX() - prevPoint.getX() == -1) && (clickPoint.getY() - prevPoint.getY() == 1))){
                } //Checks to see if the player clicks diagonally
                else if(((clickPoint.getX() - prevPoint.getX() == -1) && (clickPoint.getY() - prevPoint.getY() == -1)) || ((clickPoint.getX() - prevPoint.getX() == 1) && (clickPoint.getY() - prevPoint.getY() == -1))){
                } //Checks to see if the player clicks diagonally
            else{
                click.setHidden(false);
                if(!click.isMine()){
                    for(int i = 0; i < height; i++){
                        for(int j = 0; j < width; j++){
                            grid[i][j].setText("");
                        }
                    }
                    //Sets the color of the mine to the number of mines around it
                    click.setBackground(mineColor[click.getNumMineNeighbors()]);
                    click.setText("X");
                    score--;
                    scoreDisplay.setText("Score: " + score);
                    prevPoint = new Point(click.getX()/60, click.getY()/60);
                }
                //Checks to see if the mine has already been clicked
                else if(click.isMine() && click.getBackground() != Color.black){
                    lives--;
                    numLives.setText("Lives: " + lives);
                    score -= 100;
                    scoreDisplay.setText("Score: " + score);
                    click.setBackground(Color.black);
                }
            }
        }
        this.gameOver();
    }

    /**
     * Checks if the game is over by checking if all lives are lost or the final point is reached
     *
     * @return true if game is over, false otherwise
     */
    private boolean gameOver(){
        if(lives <= 0){
            JOptionPane.showMessageDialog(null, "You Lose");

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                    this.reveal(i, j);
                }
            }
        return true;
        }
        else if(prevPoint.getX() == 9 && prevPoint.getY() == 9){
            JOptionPane.showMessageDialog(null, "You Win!");

            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    this.reveal(i, j);
                }
            }
            return true;
        }
        return false;
    }

        /*
        * This method is used to reveal a cell in the grid by setting its background color. 
        * If the cell is hidden and contains a mine, its background color is set to dark gray.
        */
        private void reveal(int x, int y){
            if(grid[x][y].isHidden() && grid[x][y].isMine()){
                grid[x][y].setBackground(Color.darkGray);
            }
        }

    }
}