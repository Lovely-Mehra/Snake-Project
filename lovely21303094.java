import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
public class lovely21303094 extends JPanel implements KeyListener {
    private final int BOX_SIZE = 20;
    private final int BOARD_WIDTH = 30;
    private final int BOARD_HEIGHT = 20;
    private final int INITIAL_SNAKE_LENGTH = 3;
    private final int INITIAL_DELAY = 150;

    private ArrayList<Point> snake;
    private Point food;
    private int direction;
    private boolean gameOver;
    private Timer timer;

    public lovely21303094() {
        setPreferredSize(new Dimension(BOARD_WIDTH * BOX_SIZE, BOARD_HEIGHT * BOX_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        startGame();
    }

    private void startGame() {
        snake = new ArrayList<>();
        for (int i = 0; i < INITIAL_SNAKE_LENGTH; i++) {
            snake.add(new Point(BOARD_WIDTH / 2 + i, BOARD_HEIGHT / 2));
        }

        generateFood();

        direction = KeyEvent.VK_LEFT;
        gameOver = false;

        timer = new Timer(INITIAL_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    move();
                    checkCollisions();
                    repaint();
                }
            }
        });
        timer.start();
    }

    private void move() {
        Point head = snake.get(0);
        Point newHead = new Point(head.x, head.y);

        switch (direction) {
            case KeyEvent.VK_UP:
                newHead.y--;
                break;
            case KeyEvent.VK_DOWN:
                newHead.y++;
                break;
            case KeyEvent.VK_LEFT:
                newHead.x--;
                break;
            case KeyEvent.VK_RIGHT:
                newHead.x++;
                break;
        }

        snake.add(0, newHead);
        if (!newHead.equals(food)) {
            snake.remove(snake.size() - 1);
        } else {
            generateFood();
        }
    }

    private void generateFood() {
        Random random = new Random();
        int x = random.nextInt(BOARD_WIDTH);
        int y = random.nextInt(BOARD_HEIGHT);
        food = new Point(x, y);
    }

    private void checkCollisions() {
        Point head = snake.get(0);

        if (head.x < 0 || head.x >= BOARD_WIDTH || head.y < 0 || head.y >= BOARD_HEIGHT) {
            gameOver = true;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver = true;
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw snake
        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x * BOX_SIZE, point.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
        }

        // Draw food
        g.setColor(Color.RED);
        g.fillRect(food.x * BOX_SIZE, food.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);

        // Draw game over message
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", BOARD_WIDTH * BOX_SIZE / 2 - 130, BOARD_HEIGHT * BOX_SIZE / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT && direction != KeyEvent.VK_RIGHT) ||
            (key == KeyEvent.VK_RIGHT && direction != KeyEvent.VK_LEFT) ||
            (key == KeyEvent.VK_UP && direction != KeyEvent.VK_DOWN) ||
            (key == KeyEvent.VK_DOWN && direction != KeyEvent.VK_UP)) {
            direction = key;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new lovely21303094());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
