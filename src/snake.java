import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

class snake extends JPanel implements ActionListener {
    static final int ScreenW = 600;
    static final int ScreenH = 600;
    static final int unitS = 25;
    static final int gameU = (ScreenW * ScreenH) / unitS;
    static final int delay = 75;
    final int[] x = new int[gameU];
    final int[] y = new int[gameU];
    int bodysize = 6;
    int applesA;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    snake() {
        random = new Random();
        this.setPreferredSize(new Dimension(ScreenW, ScreenH));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyAdapter());
        start();
    }

    public void start() {
        createApple();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.darkGray);
            for (int i = 0; i < ScreenH / unitS; i++) {
                g.drawLine(i * unitS, 0, i * unitS, ScreenH);
                g.drawLine(0, i * unitS, ScreenW, i * unitS);
            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, unitS, unitS);

            for (int i = 0; i < bodysize; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], unitS, unitS);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], unitS, unitS);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: " + applesA, (ScreenW - metrics.stringWidth("SCORE: " + applesA)) / 2, g.getFont().getSize());
        } else {
            gameover(g);
        }
    }

    public void createApple() {
        appleX = random.nextInt((int) (ScreenW / unitS)) * unitS;
        appleY = random.nextInt((int) (ScreenH / unitS)) * unitS;
    }

    public void move() {
        for (int i = bodysize - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - unitS;
                break;
            case 'D':
                y[0] = y[0] + unitS;
                break;
            case 'L':
                x[0] = x[0] - unitS;
                break;
            case 'R':
                x[0] = x[0] + unitS;
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodysize++;
            applesA++;
            createApple();
        }
    }

    public void checkColisions() {
        for (int i = bodysize; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                System.out.println("Collision detected at index: " + i);
                running = false;
                break;
            }
        }

        if (x[0] < 0) {
            System.out.println("Snake went off the left edge.");
            running = false;
        }

        if (x[0] > ScreenW) {
            System.out.println("Snake went off the right edge.");
            running = false;
        }

        if (y[0] > ScreenH) {
            System.out.println("Snake went off the bottom edge.");
            running = false;
        }

        if (y[0] < 0) {
            System.out.println("Snake went off the top edge.");
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameover(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (ScreenW - metrics1.stringWidth("GAME OVER")) / 2, ScreenH / 2);

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("SCORE: " + applesA, (ScreenW - metrics2.stringWidth("SCORE: " + applesA)) / 2, g.getFont().getSize());
    }

    public class MyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkColisions();
        }
        repaint();
    }
}
