# Project #: MineWalker

* Author: Hector Mendez-Garcia
* Class: CS121 Section #
* Semester: Spring 2023

## Overview

MineWalker is a game that challenges players to navigate a concealed minefield 
from the top left corner of a grid to the lower right corner. Adjacent tiles 
reveal the number of mines in the area, and players can only move up, down, 
left, or right within bounds. If a player steps on a mine, they lose a life, 
but they do not enter the mine position. Once a mine is exposed, players cannot 
step on it again. The game ends when the player runs out of lives before 
reaching the goal.

## Reflection

The MineWalker project was a challenging but rewarding experience. One thing 
that worked well was breaking down the problem into smaller, more manageable 
tasks. This made it easier to focus on one aspect of the project at a time and 
prevented me from feeling overwhelmed. However, one of the struggles I faced 
was implementing the game logic correctly. Specifically, it was difficult to 
figure out how to calculate the number of mines to place and ensure that they 
were not placed on the path or on top of existing mines.

Despite spending a lot of time on the project, there are still some concepts 
that aren't quite clear to me. Like the use of JButtons and how I spend so much 
time trying to figure out how to overlap a button in order to reveal the tile 
underneath To make my code easy to debug and modify, I used a combination of 
print statements and the debugger. Whenever I encountered an error, I would 
print out relevant variables and their values to try to pinpoint the source of 
the problem. I also used the debugger to step through my code line by line to 
see what was happening at each step.

If I could go back in time, I would tell myself to spend more time planning and 
designing the program before starting to code. I found that I had to go back 
and change a lot of my code as I realized that my initial design wouldn't work. 
In the future, I would spend more time thinking through the problem and coming 
up with a solid plan before diving into the code.

## Compiling and Using

The constructor of the MineWalkerPanel class takes two arguments, width and 
height, representing the grid size. The panel displays a grid of TileButtons 
with a path that the player must follow without hitting any mines. The control 
panel includes a New Game button and a JSlider to adjust the percentage of 
mines. During the game, the player must use the mouse to uncover TileButtons, 
starting with the top left corner, and avoid mines, with the number of lives 
displayed in the top left corner of the panel. The game ends if the player 
loses all lives.

## Sources used

"Java panels" YouTube, uploaded by Bro Code, August 12th 2020, https://www.
youtube.com/watch?v=dvzAuq-YDpM&t=1s

"Coding Challenge #71: Minesweeper" YouTube, uploaded by The Coding Train, May 
18th 2017, https://www.youtube.com/watch?v=LFU5ZlrR21E&t=771s

"11 Multiple Panels in one JFrame | Softcore Production" YouTube, uploaded by 
Vishal mahor, June 20th 2021, https://www.youtube.com/watch?v=cJAXgWQC6oM

@misc{stackoverflow, 
   url={https://stackoverflow.com/questions/52037634/making-a-button-disappear-after-clicking-another-button},
   title={Making a button disappear after clicking another button},
   year={2018 Accesssed: 2018-08-07}
}