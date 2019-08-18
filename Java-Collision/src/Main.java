import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel implements Runnable {

	private final static int HEIGHT = 650;
	private final static int WIDTH = 1300;
	private final static int RATE = 60;
	private final static Color BACKGROUND_COLOR = Color.BLACK;

	private Thread thread;
	private CircleCollision collision;
	private ArrayList<Circle> circles = new ArrayList<Circle>();
	private final Random R = new Random();
	private Circle c = new Circle(WIDTH / 2, HEIGHT / 2, 0, 0, 100, Color.BLUE);
	private ArrayList<Point> points = new ArrayList<Point>();

	public Main() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		circles.add(new Circle(300, 300, 10, 10, 25, Color.RED));
		collision = new CircleCollision();
		thread = new Thread(this);
		thread.start();
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		Graphics2D g = (Graphics2D) arg0;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		c.paint(g);
		for (Circle c : circles) {
			c.paint(g);
			g.setColor(Color.YELLOW);
			g.drawLine(0, (int) c.getY(), WIDTH, (int) c.getY());
			g.drawLine((int) c.getX(), 0, (int) c.getX(), HEIGHT);
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine((int) c.getX(), (int) c.getY(), (int) this.c.getX(),
					(int) this.c.getY());

			int x = (int) ((c.getX() * this.c.getRadius() + this.c.getX()
					* c.getRadius()) / (c.getRadius() + this.c.getRadius()));
			int y = (int) ((c.getY() * this.c.getRadius() + this.c.getY()
					* c.getRadius()) / (c.getRadius() + this.c.getRadius()));
			g.setColor(Color.PINK);
			g.fillOval(x - 5, y - 5, 10, 10);
		}
		g.setColor(Color.WHITE);
		g.drawLine(0, (int) c.getY(), WIDTH, (int) c.getY());
		g.drawLine((int) c.getX(), 0, (int) c.getX(), HEIGHT);
		g.setColor(Color.GREEN);
		try {
			for (Point p : points)
				g.drawLine((int) p.getX(), (int) p.getY(), (int) c.getX(),
						(int) c.getY());
		} catch (ConcurrentModificationException e) {

		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setTitle("Circles");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(new Main());
		f.setResizable(false);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	@Override
	public void run() {
		while (true) {
			for (Circle c1 : circles) {
				c1.move();
				moveInBox(c1);
				if (collision.clash(c1, c)) {
					if (c1.getFlag()) {
						collision.changeSpeed(c1, c);
						c1.setFlag(false);
					}
				} else {
					c1.setFlag(true);
				}
				if (points.size() > 100)
					points.remove(0);
				points.add(new Point((int) c1.getX(), (int) c1.getY()));
			}
			moveInBox(c);
			c.move();
			repaint();
			delay(1000 / RATE);
		}
	}

	private void moveInBox(Circle c) {
		if (c.getX() <= c.getRadius() || c.getX() >= WIDTH - c.getRadius())
			c.invertSpeedX();
		if (c.getY() <= c.getRadius() || c.getY() >= HEIGHT - c.getRadius())
			c.invertSpeedY();
	}

	public static void delay(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}