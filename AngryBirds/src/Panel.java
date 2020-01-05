import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable {

	public static final int WIDTH = 960;
	public static final int HEIGHT = 540;
	private final int RATE = 60;

	private RedBird rb;
	private Image background;
	private Image sling;
	private Thread thread = new Thread(this);

	public Panel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		addMouseListener(new MA());
		addMouseMotionListener(new MMA());
		setFocusable(true);
		rb = new RedBird(RedBird.X0, RedBird.Y0, 0, 0);
		background = new ImageIcon("res/back.png").getImage();
		sling = new ImageIcon("res/sling.png").getImage();
		thread.start();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHints(rh);
		g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
		g.drawImage(sling, 100, 400, 50, 100, null);
		rb.paint(g);
	}

	@Override
	public void run() {
		while (true) {
			rb.move();
			delay(1000 / RATE);
			repaint();
		}
	}

	public static void delay(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class MA extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			rb.setFirstMove(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			rb.shoot();
			rb.setRubberNormal();
		}

	}

	private class MMA extends MouseMotionAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			rb.moveWithMouse(e);
		}

	}

}