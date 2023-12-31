import javax.swing.*;

public class main {
    public static <Snake> void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            snake snake = new snake();
            JFrame frame = new JFrame("Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(snake);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
