import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {

    private static final int WIN_WIDTH = 800;

    private static final int WIN_HEIGHT = 600;

    public Main() {
        Dimension dimension = new Dimension(WIN_WIDTH, WIN_HEIGHT);
        setPreferredSize(dimension);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawO(g2d, WIN_WIDTH / 2, WIN_HEIGHT / 2, 200, 0);
    }

    private void drawO(Graphics2D g2d, int x, int y, int size, int ignoreSide) {
        if (size == 0)
            return;
        drawO(g2d, x, y, size);
        int newSize = size / 2;
        if (ignoreSide != 1)
            drawO(g2d, x, y - (size + newSize) / 2, newSize, 3);
        if (ignoreSide != 2)
            drawO(g2d, x + (size + newSize) / 2, y, newSize, 4);
        if (ignoreSide != 3)
            drawO(g2d, x, y + (size + newSize) / 2, newSize, 1);
        if (ignoreSide != 4)
            drawO(g2d, x - (size + newSize) / 2, y, newSize, 2);
    }

    private void drawO(Graphics2D g2d, int x, int y, int size) {
        if (size < 0)
            return;
        g2d.drawOval(x - size / 2, y - size / 2, size, size);
        int newSize = size - 10;
        drawO(g2d, x, y, newSize);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Recursive");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Main main = new Main();
        frame.setContentPane(main);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
