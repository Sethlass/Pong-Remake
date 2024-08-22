                                        Pong Game
Overview

This project is a simple recreation of the classic "Pong" game, which itself is a 1-to-1 recreation of the historical "Tennis for Two" game. It is built using Java and utilizes Swing for the graphical user interface (GUI). The game features two paddles, a ball, and a basic scoring system, mimicking the original tennis gameplay on a digital screen.

Features

Two-player control: Each player controls a paddle using keyboard keys.

Ball movement and collision detection: The ball bounces off the paddles and walls, simulating real-time gameplay.

Scoring system: Points are awarded when the opponent misses the ball, with scores displayed at the top of the screen.

Responsive design: The game updates smoothly with a consistent frame rate, ensuring a seamless experience.

Controls

Player 1:
W key: Move paddle up
S key: Move paddle down

Player 2:
Up Arrow key: Move paddle up
Down Arrow key: Move paddle down

How to Run

Once the file is downloaded, you can click the run-through debugging, and the game should work fluently. 

Requirements

Java Development Kit (JDK): Ensure you have Java installed on your system.
License
This project is open-source and free to use or modify under the MIT License.

UPDATE 1.1

Initial Setup

The original Pong game was implemented with the following key features:

Two Human Players: Both players controlled paddles on either side of the screen.
Basic Game Loop: The game used a Timer to control the ball movement and handle collisions with the paddles and walls.
Scoring System: Each player scored when the ball passed the opponent's paddle.
Enhancements
1. Game Start with Space Bar
Purpose:
To prevent the game from starting immediately upon launch and allow the player to start the game at will by pressing the space bar.

Implementation:
A boolean flag gameStarted was introduced to track whether the game has started.
The game loop (ball movement, collision detection) only runs if gameStarted is true.
A message "Press Space to Start" is displayed on the screen before the game starts.
The game starts when the player presses the space bar (KeyEvent.VK_SPACE).



    private boolean gameStarted = false; // Flag to indicate if the game has started
       // Method to handle key presses for paddle movement and game start
       @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        gameStarted = true; // Start the game when space bar is pressed
        }
        }
     // In the paintComponent method
      if (!gameStarted) {
    g.drawString("Press Space to Start", WIDTH / 2 - 150, HEIGHT / 2);
    }
2. Simultaneous Paddle Movement
Purpose:
To address an issue where only one player could move their paddle at a time. This change allows both paddles to move simultaneously.

Implementation:
A Set<Integer> named pressedKeys was introduced to track currently pressed keys.
The keyPressed method adds the pressed key to the pressedKeys set, and keyReleased removes it.
Paddle movements are updated based on the keys present in the pressedKeys set, allowing for simultaneous movement.

           private Set<Integer> pressedKeys = new HashSet<>();
           // Method to handle key presses for paddle movement and game start
          @Override
          public void keyPressed(KeyEvent e) {
           pressedKeys.add(e.getKeyCode()); // Add the pressed key to the set of pressed keys
         }

           // Method to handle key releases
           @Override
           public void keyReleased(KeyEvent e) {
    pressedKeys.remove(e.getKeyCode()); // Remove the released key from the set of pressed keys
    }
    // In the actionPerformed method
    if (pressedKeys.contains(KeyEvent.VK_W) && paddle1Y >= paddleSpeed) {
    paddle1Y -= paddleSpeed; // Move the left paddle up
    }
    if (pressedKeys.contains(KeyEvent.VK_S) && paddle1Y <= HEIGHT - paddleHeight - paddleSpeed) {
    paddle1Y += paddleSpeed; // Move the left paddle down
    }
3. Transition to Computer vs. Human Mode
Purpose:
To change the game mode from a two-player human-controlled game to a computer vs. human game, where the human controls one paddle and the computer controls the other.

Implementation:
The left paddle (paddle1Y) is controlled by the human player using the 'W' and 'S' keys.
The right paddle (paddle2Y) is controlled by the computer, which tracks the ball's Y position.
The computer paddle's speed is defined by computerPaddleSpeed and updated in the game loop to follow the ball.
Code Snippet:

    private int computerPaddleSpeed = 7; // Speed at which the computer's paddle moves
            // Simple AI for the computer paddle: follow the ball's Y position
           if (paddle2Y + paddleHeight / 2 < ballY) {
    paddle2Y += computerPaddleSpeed; // Move computer paddle down
         } else if (paddle2Y + paddleHeight / 2 > ballY) {
    paddle2Y -= computerPaddleSpeed; // Move computer paddle up
        }
4. Difficulty Modes (Easy and Hard)
Purpose:
To introduce different difficulty levels for the computer opponent, making the game more versatile and challenging.

Implementation:
Two modes are introduced: Easy and Hard.
Hard Mode (default): The computer paddle moves faster and tracks the ball more precisely.
Easy Mode: The computer paddle moves slower and less accurately.
The difficulty can be toggled before the game starts by pressing 'E' (Easy) or 'H' (Hard).
The selected difficulty affects the computer paddle's speed during gameplay.

Code:

                    private boolean hardMode = true; // Flag to indicate if hard mode is selected (default is hard)
                    private int easyComputerPaddleSpeed = 4; // Speed at which the computer's paddle moves in easy mode
                    // Method to handle key presses for paddle movement, game start, and difficulty selection
                    @Override
            public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        gameStarted = true; // Start the game
        }
    // Set game to Easy mode when 'E' is pressed
    if (e.getKeyCode() == KeyEvent.VK_E && !gameStarted) {
        hardMode = false;
    }
    // Set game to Hard mode when 'H' is pressed
    if (e.getKeyCode() == KeyEvent.VK_H && !gameStarted) {
        hardMode = true;
    }
    }
    // In the actionPerformed method
    int currentPaddleSpeed = hardMode ? computerPaddleSpeed : easyComputerPaddleSpeed;
    if (paddle2Y + paddleHeight / 2 < ballY) {
    paddle2Y += currentPaddleSpeed; // Move computer paddle down
    } else if (paddle2Y + paddleHeight / 2 > ballY) {
    paddle2Y -= currentPaddleSpeed; // Move computer paddle up
    }
