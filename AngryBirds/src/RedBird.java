import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class RedBird extends MovableObject {

	public static final double G = 9.81;
	public static final int X0 = 126;
	public static final int Y0 = 402;
	private final int WIDTH = 32;
	private final int HEIGHT = 32;
	private final int RANGE = 80;

	private double birdRadius;
	private boolean firstMove;
	private Rubber rubber;
	private int[] hintsX = new int[10];
	private int[] hintsY = new int[hintsX.length];

	public RedBird(int x, int y, int speedX, int speedY) {
		super(x, y, speedX, speedY);
		setImg(new ImageIcon("res/red_bird.png").getImage());
		setWidth(WIDTH);
		setHEIGHT(HEIGHT);
		birdRadius = (WIDTH + HEIGHT) / 4;
		rubber = new Rubber();
	}

	public void moveWithMouse(MouseEvent e) {
		if (firstMove) {
			int mx = e.getX();
			int my = e.getY();
			double distance = Math.sqrt(Math.pow(mx - X0, 2)
					+ Math.pow(my - Y0, 2));
			if (distance <= RANGE) {
				setX(mx);
				setY(my);
			} else {
				int x = mx - X0;
				int y = my - Y0;
				double ratio = (double) y / (double) Math.abs(x);
				double theta = (x >= 0) ? Math.atan(ratio) : Math.PI
						- Math.atan(ratio);
				setX((int) (X0 + RANGE * Math.cos(theta)));
				setY((int) (Y0 + RANGE * Math.sin(theta)));
			}
			rubber.setX(getX());
			rubber.setY(getY());
			hints();
		}
	}

	public void setRubberNormal() {
		rubber.setNormal();
	}

	@Override
	public void paint(Graphics2D g) {
		paintHints(g);
		rubber.paintRightLine(g);
		g.drawImage(getImg(), getX() - WIDTH / 2, getY() - HEIGHT / 2, WIDTH,
				HEIGHT, null);
		rubber.paintLeftLine(g);
	}

	public void setFirstMove(MouseEvent e) {
		double distance = Math.sqrt(Math.pow(e.getX() - getX(), 2)
				+ Math.pow(e.getY() - getY(), 2));
		firstMove = (distance < birdRadius);
	}

	public double getSpeed() {
		return Math.sqrt(Math.pow(getX() - X0, 2) + Math.pow(getY() - Y0, 2));
	}

	public double getTheta() {
		int x = getX() - X0;
		int y = -(getY() - Y0);
		double ratio = (double) y / (double) x;
		return (x > 0) ? Math.atan(ratio) + Math.PI : Math.atan(ratio);
	}

	public void hints() {
		double speed = getSpeed();
		double theta = getTheta();
		for (double t = 0, i = 0; i < hintsX.length; t += 0.3, i++) {
			hintsX[(int) i] = (int) (speed * Math.cos(theta) * t + X0);
			hintsY[(int) i] = (int) -(-0.5 * G * t * t + speed
					* Math.sin(theta) * t - Y0);
		}
	}

	public void paintHints(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		for (int i = 0; i < hintsX.length; i++) {
			g.drawLine(hintsX[i], hintsY[i], hintsX[i], hintsY[i]);
		}
	}

	public void shoot() {
		double speed = getSpeed() / 8;
		double theta = getTheta();
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				double t = 0;
				while (getX() < Panel.WIDTH && getX() > 0 && getY() > 0
						&& getY() < Panel.HEIGHT) {
					setSpeedX((int) (speed * Math.cos(theta)));
					setSpeedY((int) -(speed * Math.sin(theta) - G * t));
					t += 0.1;
					Panel.delay(200);
				}
				setSpeedX(0);
				setSpeedY(0);
				Panel.delay(500);
				setX(X0);
				setY(Y0);
			}
		});
		thread.start();
	}

}
