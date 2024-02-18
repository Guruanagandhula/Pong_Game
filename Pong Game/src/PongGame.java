import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongGame extends JFrame implements KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final int PADDLE_WIDTH = 20;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BALL_SIZE = 20;
    private int paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddleSpeed = 10;
    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = HEIGHT / 2 - BALL_SIZE / 2;
    private int ballSpeedX = 5;
    private int ballSpeedY = 2;
    private boolean gameOver = false;
    public PongGame() {
        setTitle("WELCOME TO MY PONG GAME");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    public void movePaddle1(int direction) {
        paddle1Y += direction * paddleSpeed;
        if (paddle1Y < 0) {
            paddle1Y = 0;
        } else if (paddle1Y > HEIGHT - PADDLE_HEIGHT) {
            paddle1Y = HEIGHT - PADDLE_HEIGHT;
        }
    }
    public void movePaddle2() {
        if (!gameOver) {
            if (ballY < paddle2Y + PADDLE_HEIGHT / 2) {
                paddle2Y -= paddleSpeed;
            } else {
                paddle2Y += paddleSpeed;
            }

            if (paddle2Y < 0) {
                paddle2Y = 0;
            } else if (paddle2Y > HEIGHT - PADDLE_HEIGHT) {
                paddle2Y = HEIGHT - PADDLE_HEIGHT;
            }
        }
    }
    public void moveBall() {
        if (!gameOver) {
            ballX += ballSpeedX;
            ballY += ballSpeedY;
            if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
                ballSpeedY = -ballSpeedY;
            }
            if (ballX <= PADDLE_WIDTH && ballY + BALL_SIZE >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
                ballSpeedX = -ballSpeedX;
            } else if (ballX + BALL_SIZE >= WIDTH - PADDLE_WIDTH && ballY + BALL_SIZE >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
                ballSpeedX = -ballSpeedX;
            }
            if (ballX <= 0 || ballX >= WIDTH - BALL_SIZE) {
                gameOver = true;
                showGameOverDialog();
            }
        }
    }
    public void resetBall() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballSpeedX = -ballSpeedX;
    }

    public void showGameOverDialog() {
        int option = JOptionPane.showConfirmDialog(this, "Game Over! Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            gameOver = false;
            resetBall();
        } else {
            System.exit(0);
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.fillRect(PADDLE_WIDTH, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT); 
        g.fillRect(WIDTH - 2 * PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT); 

        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE); 

        Toolkit.getDefaultToolkit().sync(); 
    }
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                movePaddle1(-1);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                movePaddle1(1);
            }
        }
    }
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
    public static void main(String[] args) {
        PongGame game = new PongGame();
        game.setVisible(true);

        while (true) {
            game.movePaddle2();
            game.moveBall();
            game.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
