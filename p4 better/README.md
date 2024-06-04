# Project #: Project Name

* Author: Hector Mendez-Garcia
* Class: CS121 Section #
* Semester: Spring 2023

## Overview

This is a Java program that implements a game of Tic Tac Toe. The game board is represented by a 2D 
array of BoardChoice enums, where each cell represents a square on the Tic Tac Toe board and can be 
either X, O, or OPEN (empty). Players can make moves by selecting a row and column on the board. The 
game ends when one player wins or when the board is full (a tie). The program also includes methods to 
start a new game, check if the game is over, check if a player has won, and allow a player to make a 
move by selecting a row and column on the game board.

## Reflection

I found this project to be a good challenge that required me to think critically about the 
implementation of the game. One of the things that worked well for me was the use of a 2D array to 
represent the game board, which made it easier to keep track of the state of the game. One struggle I 
had was with the game state enumeration. I found it challenging to determine when to update the game 
state and to ensure that the correct state was returned at the end of the game. However, I was able to 
overcome this issue by carefully examining the code and running several test cases.

I still have some questions about the use of arrays in Java and how to best utilize them in my code. 
Additionally, I am not entirely clear on how to use the Point class and how it relates to the 
implementation of the game. To make my code easy to debug and modify, I made sure to keep my methods 
short and well-documented, which helped me to easily identify errors in my code.

If I could go back in time, I would tell myself to plan out the implementation of the game more 
thoroughly before starting to write any code. I would also suggest that I take the time to thoroughly 
review and understand the different concepts and methods used in the game before attempting to write my 
own implementation. Overall, I found this project to be a valuable learning experience that allowed me 
to gain a deeper understanding of Java programming and object-oriented design principles.

## Compiling and Using

To make a move, the user needs to know the row and column of the cell they want to select. The rows and 
columns are numbered from 0 to 2, and they represent the top, middle, and bottom rows and the left, 
center, and right columns, respectively. To start a new game, you can call the newGame() method. To make 
a move, you can call the choose() method with the BoardChoice enum representing the player (X or O) and 
the row and column of the selected cell. The choose() method returns true if the move was successful and 
false otherwise. The gameOver() method checks if the game is over by checking if any player has won or 
if the game is a tie. The win() method checks if a player has won the game.

## Sources used

"Tic Tac Toe Java Game - Build a Tic Tac Toe Game in 30 Minutes" 
YouTube, uploaded by Alex Lee, December 5th, 2019, https://www.youtube.com/watch?v=gQb3dE-y1S4

"Nested Loops & 2D Arrays | Java | Tutorial 23" 
YouTube, uploaded by Mike Dane, October 21st, 2017, https://www.youtube.com/watch?v=w-9ZTeO7q_E
