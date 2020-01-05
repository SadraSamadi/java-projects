import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Animate extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int BOX_WIDTH = 640;
	public static final int BOX_HEIGHT = 480;

	private static Robot r;
	private static ArrayList<HeliBoy> hb = new ArrayList<HeliBoy>();

	private static final int SPEED_RATE = 60;

	private BufferedImage background;

	private int counter = 0;

	public Animate() {
		r = new Robot(100, 400, 0, 0, this);
		try {
			background = ImageIO.read(new File("res/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
		addKeyListener(new MyKeyListener());
		setFocusable(true);
		setDoubleBuffered(true);
		Thread gameLoop = new Thread(this);
		gameLoop.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(background.getSubimage(0, 0, BOX_WIDTH, background.getHeight()), 0, 0, BOX_WIDTH, BOX_HEIGHT, this);

		g.drawImage(r.getImg(), r.getX() - r.getImgWidth() / 2, r.getY() - r.getImgHeight() / 2, this);

		for (int i = 0; i < hb.size(); i++) {
			g.drawImage(hb.get(i).getImg(), hb.get(i).getX() - hb.get(i).getImgWidth() / 2,
					hb.get(i).getY() - hb.get(i).getImgHeight() / 2, this);

			for (int j = 0; j < hb.get(i).getBullets().size(); j++) {
				g.drawImage(hb.get(i).getBullets().get(j).getImg(), hb.get(i).getBullets().get(j).getX(),
						hb.get(i).getBullets().get(j).getY(), this);
			}
		}

		for (int i = 0; i < r.getBullets().size(); i++) {
			g.drawImage(r.getBullets().get(i).getImg(), r.getBullets().get(i).getX(), r.getBullets().get(i).getY(),
					this);
		}

		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(counter), 20, 20);
	}

//	private void moveInBox(MovableObject character) {
//		character.setX((int) (character.getX() + character.getSpeedX()));
//		character.setY((int) (character.getY() + character.getSpeedY()));
//
//		if (character.getX() < character.getImgWidth() / 2
//				|| character.getX() > BOX_WIDTH - character.getImgWidth() / 2) {
//			character.invertSpeedX();
//		}
//
//		if (character.getY() > BOX_HEIGHT - character.getImgHeight() / 2
//				|| character.getY() < character.getImgHeight() / 2) {
//			character.invertSpeedY();
//		}
//	}

	public static void delay(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long f = System.currentTimeMillis();
		label: while (true) {
			r.move();

			if ((System.currentTimeMillis() - f) / 1000 > 2) {
				hb.add(new HeliBoy(700, 375, -1, 0, this));
				f = System.currentTimeMillis();
			}

			for (int i = 0; i < r.getBullets().size(); i++) {
				if (r.getBullets().get(i).isVisible())
					r.getBullets().get(i).move();
				else
					r.getBullets().remove(i);
			}

			for (int i = 0; i < hb.size(); i++) {
				if (hb.get(i).isVisible())
					hb.get(i).move();
				else
					hb.remove(i);

				if (hb.get(i).getX() < -50)
					hb.remove(i);

				if (hb.get(i).getX() < r.getX() + 80) {
					JOptionPane.showMessageDialog(null, "Game Over !");
					break label;
				}

				for (int j = 0; j < hb.get(i).getBullets().size(); j++) {
					if (hb.get(i).getBullets().get(j).isVisible())
						hb.get(i).getBullets().get(j).move();
					else
						hb.get(i).getBullets().remove(j);

					if (hb.get(i).getBullets().get(j).getX() < r.getX() + 30
							&& hb.get(i).getBullets().get(j).getX() > r.getX() - 30 && !r.isDown()) {
						JOptionPane.showMessageDialog(null, "Game Over !");
						break label;
					}
				}
			}

			for (int i = 0; i < Math.min(hb.size(), r.getBullets().size()); i++) {
				if (r.getBullets().get(i).getX() > hb.get(i).getX()) {
					r.getBullets().remove(i);
					hb.remove(i);
					counter++;
				}
			}

			repaint();
			delay(1000 / SPEED_RATE);
		}
		System.exit(0);
	}

	private class MyKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			r.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			r.keyReleased(e);
		}

	}

}
