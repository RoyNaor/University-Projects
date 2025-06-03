package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    // Constants to set up the game's window size and mechanics
    static final int SCREEN_WIDTH = 600;  // Width of the game screen
    static final int SCREEN_HEIGHT = 600; // Height of the game screen
    static final int UNIT_SIZE = 25;      // Size of the objects (like snake and apple)
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE; // Total playable units
    static final int DELAY = 75;          // Timer delay for game updates, in milliseconds

    // Arrays to hold the x and y coordinates of the snake's parts
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;                    // Initial number of snake parts
    int applesEaten = 0;                  // Counter for the apples eaten by the snake
    int appleX;                           // X-coordinate of the current apple
    int appleY;                           // Y-coordinate of the current apple
    char direction = 'R';                 // Initial direction of the snake (Right)
    boolean running = false;              // Game state flag
    Timer timer;                          // Timer to schedule game updates
    Random rnd;                           // Random number generator for placing apples

    public GamePanel() {
        rnd = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();  // Redraws the panel
    }

    public void startGame() {
        newApple();  // Places the first apple
        running = true;  // Starts the game
        timer = new Timer(DELAY, this);
        timer.start();  // Starts the timer to trigger game updates
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);  // Draws the apple

            for(int i = 0; i < bodyParts; i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);  // Draws the head of the snake
                }
                else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);  // Draws the body of the snake
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        }
        else {
            gameOver(g);  // Draws the game over screen
        }
    }

    public void newApple() {
        appleX = rnd.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = rnd.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;  // Randomly places a new apple
    }

    public void move() {
        for(int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];  // Shifts the snake parts forward in the array
            y[i] = y[i - 1];
        }

        // Moves the head in the current direction
        switch (direction) {
            case 'U': y[0] -= UNIT_SIZE; break;
            case 'D': y[0] += UNIT_SIZE; break;
            case 'R': x[0] += UNIT_SIZE; break;
            case 'L': x[0] -= UNIT_SIZE; break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;    // Increases the size of the snake
            applesEaten++;  // Increases score
            newApple();     // Places a new apple
        }
    }

    public void checkCollisions() {
        // Checks if the head collides with any body part
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;  // Ends the game if collision detected
                break;
            }
        }

        // Check if the snake hits the borders
        if (x[0] < 0 || x[0] > SCREEN_WIDTH - 25 || y[0] < 0 || y[0] > SCREEN_HEIGHT - 25) {
            running = false;
        }

        if (!running) timer.stop();  // Stops the timer if the game ends
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (SCREEN_WIDTH - metrics.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2);
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') direction = 'L';  // Changes the snake's direction unless going directly opposite
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') direction = 'R';
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D') direction = 'U';
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U') direction = 'D';
                    break;
            }
        }
    }
}
