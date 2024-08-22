import javax.swing.*;  
import java.awt.*;     
import java.awt.event.*; 
import java.util.HashSet;
import java.util.Set;

public class PongGameVsBot extends JPanel implements KeyListener, ActionListener {

    private static final int WIDTH = 800; // Width of the game window
    private static final int HEIGHT = 600; // Height of the game window

    private int paddle1Y = 250; // Y position of the first paddle (left paddle - human player)
    private int paddle2Y = 250; // Y position of the second paddle (right paddle - computer player)
    private int paddleWidth = 10; // Width of both paddles
    private int paddleHeight = 100; // Height of both paddles
    private int paddleSpeed = 10; // Speed at which the human player's paddle moves up or down

    private int ballX = 400; // X position of the ball (starting at center)
    private int ballY = 300; // Y position of the ball (starting at center)
    private int ballSize = 20; // Diameter of the ball
    private int ballSpeedX = 5; // Speed of the ball along the X-axis
    private int ballSpeedY = 5; // Speed of the ball along the Y-axis

    private int score1 = 0; // Score for the human player (left side)
    private int score2 = 0; // Score for the computer player (right side)

    private Timer timer; // Timer to control the game's animation
    private boolean gameStarted = false; // Flag to indicate if the game has started
    private boolean hardMode = true; // Flag to indicate if hard mode is selected (default is hard)

    private int computerPaddleSpeed = 7; // Speed at which the computer's paddle moves in hard mode
    private int easyComputerPaddleSpeed = 4; // Speed at which the computer's paddle moves in easy mode

    // Set to keep track of currently pressed keys
    private Set<Integer> pressedKeys = new HashSet<>();

    // Constructor to initialize the PongGame panel
    public PongGameVsBot() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Set the preferred size of the panel
        this.setBackground(Color.BLACK); // Set the background color of the panel to black
        this.setFocusable(true); // Enable the panel to capture keyboard input
        this.addKeyListener(this); // Register the panel as a KeyListener

        timer = new Timer(10, this); // Initialize the timer to trigger action events every 10 milliseconds
        timer.start(); // Start the timer to begin the game loop
    }

    // Method to draw the game components on the screen
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper rendering

        g.setColor(Color.WHITE); // Set the drawing color to white for the paddles, ball, and scores                                                                                                         // THIS CODE WAS MADE BY SETH LASSITER 

        g.fillRect(20, paddle1Y, paddleWidth, paddleHeight); // Draw the human player's (left) paddle
        g.fillRect(WIDTH - 40, paddle2Y, paddleWidth, paddleHeight); // Draw the computer's (right) paddle

        g.fillOval(ballX, ballY, ballSize, ballSize); // Draw the ball at its current position

        g.setFont(new Font("Arial", Font.PLAIN, 30)); // Set the font for the scores
        g.drawString(String.valueOf(score1), WIDTH / 2 - 50, 30); // Draw the human player's score
        g.drawString(String.valueOf(score2), WIDTH / 2 + 30, 30); // Draw the computer's score

        // Display start message if the game hasn't started
        if (!gameStarted) {
            g.drawString("Press Space to Start", WIDTH / 2 - 150, HEIGHT / 2);
            g.drawString("Press E for Easy or H for Hard", WIDTH / 2 - 180, HEIGHT / 2 + 40);
        }
    }

    // Method to handle action events triggered by the timer
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted) { // Only update the game if it has started
            ballX += ballSpeedX; // Update the ball's position by its speed along the X-axis
            ballY += ballSpeedY; // Update the ball's position by its speed along the Y-axis

            // Check for ball collision with the top or bottom walls
            if (ballY <= 0 || ballY >= HEIGHT - ballSize) {
                ballSpeedY = -ballSpeedY; // Reverse the ball's Y direction if it hits a wall
            }

            // Check for ball collision with the human player's paddle
            if (ballX <= 30 && ballY >= paddle1Y && ballY <= paddle1Y + paddleHeight) {
                ballSpeedX = -ballSpeedX; // Reverse the ball's X direction if it hits the left paddle
            } 
            // Check for ball collision with the computer's paddle
            else if (ballX >= WIDTH - 50 && ballY >= paddle2Y && ballY <= paddle2Y + paddleHeight) {
                ballSpeedX = -ballSpeedX; // Reverse the ball's X direction if it hits the right paddle
            }

            // Check if the ball goes out of bounds on the left side (score for computer)
            if (ballX <= 0) {
                score2++; // Increment computer's score
                resetBall(); // Reset the ball to the center and change its direction
            } 
            // Check if the ball goes out of bounds on the right side (score for human)
            else if (ballX >= WIDTH - ballSize) {
                score1++; // Increment human player's score
                resetBall(); // Reset the ball to the center and change its direction
            }

            // Handle paddle movement based on pressed keys
            if (pressedKeys.contains(KeyEvent.VK_W) && paddle1Y >= paddleSpeed) {
                paddle1Y -= paddleSpeed; // Move the left paddle up
            }
            if (pressedKeys.contains(KeyEvent.VK_S) && paddle1Y <= HEIGHT - paddleHeight - paddleSpeed) {
                paddle1Y += paddleSpeed; // Move the left paddle down
            }

            // Simple AI for the computer paddle: follow the ball's Y position
            int currentPaddleSpeed = hardMode ? computerPaddleSpeed : easyComputerPaddleSpeed;
            if (paddle2Y + paddleHeight / 2 < ballY) {
                paddle2Y += currentPaddleSpeed; // Move computer paddle down
            } else if (paddle2Y + paddleHeight / 2 > ballY) {
                paddle2Y -= currentPaddleSpeed; // Move computer paddle up
            }

            // Prevent computer paddle from moving out of bounds
            paddle2Y = Math.max(paddle2Y, 0);
            paddle2Y = Math.min(paddle2Y, HEIGHT - paddleHeight);
        }
        repaint(); // Repaint the panel to update the screen with the new positions and scores
    }

    // Method to reset the ball's position to the center and change its direction
    private void resetBall() {
        ballX = WIDTH / 2; // Reset ball's X position to the center of the window
        ballY = HEIGHT / 2; // Reset ball's Y position to the center of the window
        ballSpeedX = -ballSpeedX; // Reverse the ball's X direction
    }

    // Method to handle key presses for paddle movement, game start, and difficulty selection
    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode()); // Add the pressed key to the set of pressed keys

        // Start the game when the space bar is pressed
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

    // Method to handle key releases
    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode()); // Remove the released key from the set of pressed keys
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, but required by the KeyListener interface
    }

    // Main method to set up the game window and start the game
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game"); // Create a new JFrame for the game window
        PongGameVsBot game = new PongGameVsBot(); // Create an instance of the PongGame class

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application exits when the window is closed
        frame.add(game); // Add the PongGame panel to the frame
        frame.pack(); // Size the frame to fit the preferred size of the panel
        frame.setVisible(true); // Make the frame visible to the user
        frame.setResizable(false); // Prevent the window from being resized
    }
}


