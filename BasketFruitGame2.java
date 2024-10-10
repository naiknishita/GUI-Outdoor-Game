// Author: Nishita Naik
// Roll no: 27
// Title: Goa Legislative Assembly
// Start date: 30/09/2024
// Modified Date: 05/10/2024

// Discription: basket fruit game is Game of collecting fuits which appers one by as you collect the fruits in the bakest.
//you get only 30 secs to collect the fruits.
// try to collect as many fruits as you can.
//



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BasketFruitGame2 extends JPanel implements ActionListener {

    private int basketX = 200;  // Initial basket X position
    private int basketY = 300;  // Initial basket Y position
    private int fruitX;         // X position of the fruit
    private int fruitY;         // Y position of the fruit
    private int fruitsCollected = 0;  // Counter for collected fruits
    private Image basketImage;  // Image of the basket
    private Image[] fruitImages; // Array to hold fruit images
    private Image currentFruitImage; // The currently displayed fruit image
    private Timer gameTimer;    // Timer for game loop
    private Timer countdownTimer; // Timer for countdown
    private int timeLeft = 30;  // 30 seconds countdown

    public BasketFruitGame2() {
        // Load basket image
        basketImage = new ImageIcon("C:\\Users\\nishi\\Desktop\\Notes\\MCA\\FY\\OOP\\Outdoor_game\\basketImage.png").getImage();
        
        // Load multiple fruit images into an array
        fruitImages = new Image[] {
            new ImageIcon("C:\\Users\\nishi\\Desktop\\Notes\\MCA\\FY\\OOP\\Outdoor_game\\apple.png").getImage(),
            new ImageIcon("C:\\Users\\nishi\\Desktop\\Notes\\MCA\\FY\\OOP\\Outdoor_game\\orange.png").getImage(),
            new ImageIcon("C:\\Users\\nishi\\Desktop\\Notes\\MCA\\FY\\OOP\\Outdoor_game\\strawberry.png").getImage(),
            new ImageIcon("C:\\Users\\nishi\\Desktop\\Notes\\MCA\\FY\\OOP\\Outdoor_game\\grape.png").getImage()
        };

        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.GREEN);
        setFocusable(true);
        addKeyListener(new KeyHandler());

        // Start the game and countdown timers
        gameTimer = new Timer(100, this);
        gameTimer.start();
        
        countdownTimer = new Timer(1000, new CountdownTimer());
        countdownTimer.start();

        spawnFruit();  // Spawn the first fruit
    }

    // Method to place the fruit at a random position and choose a random fruit image
    private void spawnFruit() {
        Random random = new Random();
        fruitX = random.nextInt(400 - 40); // Keep within bounds of the game panel
        fruitY = random.nextInt(300); // Keep within upper bounds for fruit
        // Select a random fruit image from the array
        currentFruitImage = fruitImages[random.nextInt(fruitImages.length)];
    }

    // Method to check if the basket has collected the fruit
    private void checkFruitCollection() {
        if (new Rectangle(basketX, basketY, 50, 50).intersects(new Rectangle(fruitX, fruitY, 40, 40))) {
            fruitsCollected++;  // Increase the score
            spawnFruit();       // Spawn a new fruit with a new image
        }
    }

    // Method to paint the basket, fruit, score, and time left
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the basket
        g.drawImage(basketImage, basketX, basketY, 70, 70, this);
        
        // Draw the current fruit
        g.drawImage(currentFruitImage, fruitX, fruitY, 30, 30, this);
        
        // Draw the score and timer
        g.setColor(Color.BLACK);
        g.drawString("Fruits Collected: " + fruitsCollected, 10, 20);
        g.drawString("Time Left: " + timeLeft + " sec", 10, 40);

        // If time is up, show game over message
        if (timeLeft <= 0) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Game Over!", 140, 200);
            g.drawString("Fruits Collected: " + fruitsCollected, 120, 240);
        }
    }

    // Method to update game on timer tick
    @Override
    public void actionPerformed(ActionEvent e) {
        if (timeLeft > 0) {
            checkFruitCollection();  // Check if the basket collects a fruit
            repaint();               // Repaint the game panel
        } else {
            gameTimer.stop();  // Stop the game if time is up
        }
    }

    // Key handler for basket movement
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (timeLeft > 0) {  // Only move the basket if the game is still running
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (basketX > 0) basketX -= 10;  // Move left
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (basketX < getWidth() - 50) basketX += 10;  // Move right
                        break;
                    case KeyEvent.VK_UP:
                        if (basketY > 0) basketY -= 10;  // Move up
                        break;
                    case KeyEvent.VK_DOWN:
                        if (basketY < getHeight() - 50) basketY += 10;  // Move down
                        break;
                }
            }
        }
    }

    // Countdown timer for the 30 seconds
    private class CountdownTimer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (timeLeft > 0) {
                timeLeft--;  // Decrease time left by 1 second
            } else {
                countdownTimer.stop();  // Stop the countdown when time is up
            }
            repaint();  // Repaint to show updated time
        }
    }

    // Main method to set up the game window
    public static void main(String[] args) {
        JFrame frame = new JFrame("Basket Fruit Game");
        BasketFruitGame2 gamePanel = new BasketFruitGame2();
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
