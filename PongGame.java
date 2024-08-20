import javax.swing.*;  // Importing Swing components for creating the game window and GUI elements
import java.awt.*;     // Importing AWT components for handling graphics and colors
import java.awt.event.*; // Importing AWT event-handling components for capturing keyboard actions

// The PongGame class extends JPanel to allow custom drawing and implements KeyListener and ActionListener interfaces
// to handle keyboard input and timer events.
public class PongGame extends JPanel implements KeyListener, ActionListener {

    // Constants for the game window size
    private static final int WIDTH = 800; // Width of the game window
    private static final int HEIGHT = 600; // Height of the game window

    // Variables for the paddles' positions and dimensions
    private int paddle1Y = 250; // Y position of the first paddle (left paddle)
    private int paddle2Y = 250; // Y position of the second paddle (right paddle)
    private int paddleWidth = 10; // Width of both paddles
    private int paddleHeight = 100; // Height of both paddles
    private int paddleSpeed = 10; // Speed at which paddles move up or down

    // Variables for the ball's position, size, and speed
    private int ballX = 400; // X position of the ball (starting at center)
    private int ballY = 300; // Y position of the ball (starting at center)
    private int ballSize = 20; // Diameter of the ball
    private int ballSpeedX = 5; // Speed of the ball along the X-axis
    private int ballSpeedY = 5; // Speed of the ball along the Y-axis

    // Variables to keep track of the scores for each player
    private int score1 = 0; // Score for the first player (left side)
    private int score2 = 0; // Score for the second player (right side)

    // Timer to control the game's animation, firing events at regular intervals
    private Timer timer;

    // Constructor to initialize the PongGame panel
    public PongGame() {
        // Set the preferred size of the panel to match the game window dimensions
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Set the background color of the panel to black
        this.setBackground(Color.BLACK);

        // Enable the panel to capture keyboard input
        this.setFocusable(true);
        this.addKeyListener(this); // Register the panel as a KeyListener

        // Initialize the timer to trigger action events every 10 milliseconds
        timer = new Timer(10, this);
        timer.start(); // Start the timer to begin the game loop
    }

    // Method to draw the game components on the screen
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper rendering

        g.setColor(Color.WHITE); // Set the drawing color to white for the paddles, ball, and scores

        // Draw the paddles at their respective positions
        g.fillRect(20, paddle1Y, paddleWidth, paddleHeight); // Left paddle
        g.fillRect(WIDTH - 40, paddle2Y, paddleWidth, paddleHeight); // Right paddle

        // Draw the ball at its current position
        g.fillOval(ballX, ballY, ballSize, ballSize);

        // Draw the scores at the top center of the window
        g.setFont(new Font("Arial", Font.PLAIN, 30)); // Set the font for the scores
        g.drawString(String.valueOf(score1), WIDTH / 2 - 50, 30); // Draw the first player's score
        g.drawString(String.valueOf(score2), WIDTH / 2 + 30, 30); // Draw the second player's score
    }

    // Method to handle action events triggered by the timer
    @Override
    public void actionPerformed(ActionEvent e) {
        // Update the ball's position by its speed in both X and Y directions
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Check for ball collision with the top or bottom walls
        if (ballY <= 0 || ballY >= HEIGHT - ballSize) {
            ballSpeedY = -ballSpeedY; // Reverse the ball's Y direction if it hits a wall
        }

        // Check for ball collision with the left paddle
        if (ballX <= 30 && ballY >= paddle1Y && ballY <= paddle1Y + paddleHeight) {
            ballSpeedX = -ballSpeedX; // Reverse the ball's X direction if it hits the left paddle
        } 
        // Check for ball collision with the right paddle
        else if (ballX >= WIDTH - 50 && ballY >= paddle2Y && ballY <= paddle2Y + paddleHeight) {
            ballSpeedX = -ballSpeedX; // Reverse the ball's X direction if it hits the right paddle
        }

        // Check if the ball goes out of bounds on the left side (score for player 2)
        if (ballX <= 0) {
            score2++; // Increment player 2's score
            resetBall(); // Reset the ball to the center and change its direction
        } 
        // Check if the ball goes out of bounds on the right side (score for player 1)
        else if (ballX >= WIDTH - ballSize) {
            score1++; // Increment player 1's score
            resetBall(); // Reset the ball to the center and change its direction
        }

        repaint(); // Repaint the panel to update the screen with the new positions and scores
    }

    // Method to reset the ball's position to the center and change its direction
    private void resetBall() {
        ballX = WIDTH / 2; // Reset ball's X position to the center of the window
        ballY = HEIGHT / 2; // Reset ball's Y position to the center of the window
        ballSpeedX = -ballSpeedX; // Reverse the ball's X direction
    }

    // Method to handle key presses for paddle movement
    @Override
    public void keyPressed(KeyEvent e) {
        // Move the left paddle up when the 'W' key is pressed, if not at the top edge
        if (e.getKeyCode() == KeyEvent.VK_W && paddle1Y >= paddleSpeed) {
            paddle1Y -= paddleSpeed; // Decrease the Y position to move the paddle up
        } 
        // Move the left paddle down when the 'S' key is pressed, if not at the bottom edge
        else if (e.getKeyCode() == KeyEvent.VK_S && paddle1Y <= HEIGHT - paddleHeight - paddleSpeed) {
            paddle1Y += paddleSpeed; // Increase the Y position to move the paddle down
        }

        // Move the right paddle up when the 'UP' arrow key is pressed, if not at the top edge
        if (e.getKeyCode() == KeyEvent.VK_UP && paddle2Y >= paddleSpeed) {
            paddle2Y -= paddleSpeed; // Decrease the Y position to move the paddle up
        } 
        // Move the right paddle down when the 'DOWN' arrow key is pressed, if not at the bottom edge
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && paddle2Y <= HEIGHT - paddleHeight - paddleSpeed) {
            paddle2Y += paddleSpeed; // Increase the Y position to move the paddle down
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used, but required by the KeyListener interface
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, but required by the KeyListener interface
    }

    // Main method to set up the game window and start the game
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game"); // Create a new JFrame for the game window
        PongGame game = new PongGame(); // Create an instance of the PongGame class

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application exits when the window is closed
        frame.add(game); // Add the PongGame panel to the frame
        frame.pack(); // Size the frame to fit the preferred size of the panel
        frame.setVisible(true); // Make the frame visible to the user
        frame.setResizable(false); // Prevent the window from being resized
    }
}

