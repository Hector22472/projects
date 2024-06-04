// CS 230 Challenge 3a - Game of Truth
//
// This starter code initializes a cellular automaton grid and displays it using
// an HTML5 Canvas.
//
// Mill's Free Speech
// 1. Opposing arguments may in fact be correct and we may be wrong
// 2. Our belief in the truth is strengthened when defending challenges
// 3. Opposing views may contain a portion of the truth

const ROWS = 100;
const COLS = 150;
const BSIZE = 5; // The size (width, height) of each cell
const POPULATION = ROWS * COLS;
// Define cell values
const UNDECIDED = 0;
const TRUTH_LOW = 1;
const TRUTH_HI = 2;
const PARTIAL_A = 3;
const PARTIAL_B = 4;
const UNTRUTH_LOW = 5;
const UNTRUTH_HI = 6;
// Color key
const colorKey = {
0: 'White (Undecided)',
1: 'Green (Truth Low)',
2: 'Dark Green (Truth High)',
3: 'Yellow (Partial A)',
4: 'Yellow (Partial B)',
5: 'Red (Untruth Low)',
6: 'Dark Red (Untruth High)',
};
// Create the population grid
var pop = Array.from({ length: ROWS }, () => Array.from({ length: COLS }, () =>
UNDECIDED));
var ctx;


// Function to simulate the cellular automaton
function simulate(censor) {
let newPop = JSON.parse(JSON.stringify(pop)); // Create a copy of the population


// Iterate through every cell in the grid
for (let row = 0; row < ROWS; ++row) {
for (let col = 0; col < COLS; ++col) {
    let cellValue = pop[row][col];
    let newValue = cellValue;
    let randomRow, randomCol, neighborValue;
    do {
        // Select one neighboring cell at random
        randomRow = row + Math.floor(Math.random() * 3) - 1;
        randomCol = col + Math.floor(Math.random() * 3) - 1;
        // Ensure the random cell is within bounds
        randomRow = Math.max(0, Math.min(ROWS - 1, randomRow));
        randomCol = Math.max(0, Math.min(COLS - 1, randomCol));
        neighborValue = pop[randomRow][randomCol];
    } while (neighborValue === cellValue); // Ensure the selected cell has not been set already


    // Apply the rules to set the new cell value
    switch (cellValue) {
    case UNDECIDED:
        newValue = neighborValue;
        break;
    case UNTRUTH_LOW:
        if (neighborValue === TRUTH_HI) newValue = UNTRUTH_HI;
        break;
    case TRUTH_LOW:
        if (neighborValue === UNTRUTH_HI) newValue = TRUTH_HI;
        break;
    case PARTIAL_A:
        if (neighborValue === PARTIAL_B || neighborValue === TRUTH_HI)
        newValue = TRUTH_LOW;
        break;
    case PARTIAL_B:
        if (neighborValue === PARTIAL_A || neighborValue === TRUTH_HI)
        newValue = TRUTH_LOW;
        break;
    case UNTRUTH_HI:
        if (!censor && neighborValue === UNTRUTH_HI) newValue = UNTRUTH_HI;
        break;
    default:
        newValue = cellValue;
    }


    // Update the new population grid with the calculated value
    newPop[row][col] = newValue;
}
}


// Update the population with the new values
pop = newPop;
}
// Function to draw the population grid on the canvas
function redraw() {
    for (let row = 0; row < ROWS; ++row) {
        for (let col = 0; col < COLS; ++col) {
            let color;
            switch (pop[row][col]) {
                case UNDECIDED:
                    color = '#FFFFFF'; // White
                    break;
                case TRUTH_LOW:
                    color = '#00FF00'; // Green
                    break;
                case TRUTH_HI:
                    color = '#008000'; // Dark Green
                    break;
                case PARTIAL_A:
                case PARTIAL_B:
                    color = '#FFFF00'; // Yellow
                    break;
                case UNTRUTH_LOW:
                    color = '#FF0000'; // Red
                    break;
                case UNTRUTH_HI:
                    color = '#800000'; // Dark Red
                    break;
                default:
                    color = '#FFFFFF'; // White (Default color)
            }
            ctx.fillStyle = color;
            ctx.fillRect(col * (BSIZE + 1), row * (BSIZE + 1), BSIZE, BSIZE);
        }
    }
}
// Function to initialize the canvas and population grid
function init() {
    console.log("Initializing canvas");
    var canvas = document.getElementById("canvas");
    canvas.width = COLS * (BSIZE + 1);
    canvas.height = ROWS * (BSIZE + 1);
    ctx = canvas.getContext("2d");
    // Set initial conditions (randomize population)
    for (let row = 0; row < ROWS; ++row) {
        for (let col = 0; col < COLS; ++col) {
            let randomValue = Math.floor(Math.random() * 7); // Random value between 0 and 6
            // Check if cell is already initialized
            while (pop[row][col] !== UNDECIDED) {
                row = Math.floor(Math.random() * ROWS);
                col = Math.floor(Math.random() * COLS);
            }
            pop[row][col] = randomValue;
        }
    }
    // Redraw the canvas
    redraw();
    // Display color key
    displayColorKey();
}
// Function to simulate one step of the automaton
function step() {
    simulate(false); // Do not apply censoring
    redraw();
}
// Function to run multiple steps of the automaton
function run(steps) {
    if (steps > 0) {
        simulate(false); // Do not apply censoring
        redraw();
        setTimeout(() => run(steps - 1), 10); // Call run recursively after a delay
    }
}
// Function to display color key
function displayColorKey() {
    const colorKeyContainer = document.getElementById('color-key');
    Object.entries(colorKey).forEach(([value, description]) => {
        const div = document.createElement('div');
        div.textContent = `${description}`;
        div.style.color = value <= 2 ? 'black' : 'white'; // Adjust text color for better visibility
        div.style.backgroundColor = value <= 2 ? 'white' : 'black'; // Adjust background color for better visibility
        colorKeyContainer.appendChild(div);
    });
}
// Initialize the canvas and population grid on page load
window.onload = init;
