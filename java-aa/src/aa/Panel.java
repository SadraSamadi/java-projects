package aa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private Color backgroundColor = Color.WHITE;
	private Color circleColor = Color.BLACK;
	public static int circleRadius = 80;
	private int counter = 0;
	private int needlesNumber = 18;
	private ArrayList<Needle> needles = new ArrayList<Needle>();
	
	private final int BOX_WIDTH = 480;
	private final int BOX_HEIGHT = 640;
	private final int FRAME_RATE = 60;
	
	public Panel() {
		for (int i = 0; i < needlesNumber ; i++) {
			Needle n = new Needle(0, 150 + 20 * i, 0, 250 + 20 * i, needlesNumber - i);
			n.setVisible(true);
			needles.add(n);
		}
		for (int i = 0; i < 8; i++) {
			Needle n = new Needle(0, 0, 0, 0, -1);
			n.setVisible(true);
			n.setLineVisible(true);
			n.setRotating(true);
			n.setOnCircle(true);
			n.setChecked(true);
			n.setAngle(360 - i * 45);
			needles.add(n);
		}
		setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
		addKeyListener(new KA());
		setFocusable(true);
		new Thread(this).start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.translate(BOX_WIDTH / 2, BOX_HEIGHT / 3);
		
		g2d.setColor(backgroundColor);
		g2d.fillRect(-(BOX_WIDTH / 2), -(BOX_HEIGHT / 3), BOX_WIDTH, BOX_HEIGHT);
		
		g2d.setColor(circleColor);
		g2d.fillOval(-circleRadius, -circleRadius, circleRadius * 2, circleRadius * 2);
		
		for (Needle n : needles)
			if (n.isVisible())
				n.paint(g2d);
		
		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void run() {
		while (true) {
			if (counter == needlesNumber) {
				backgroundColor = Color.GREEN;
			}
			for (int i = 0; i < needles.size(); i++) {
				if (needles.get(i).isRotating()) {
					needles.get(i).rotate();
				}
				if (needles.get(i).isOnCircle() && !needles.get(i).isChecked()) {
					for (int j = 0; j < needles.size(); j++) {
						if (i != j) {
							if (collision(needles.get(i).getX2(), needles.get(i).getY2(),
									needles.get(j).getX2(), needles.get(j).getY2())) {
								backgroundColor = Color.RED;
							}
						}
					}
					needles.get(i).setChecked(true);
				}
			}
			delay(1000 / FRAME_RATE);
			repaint();
			if (backgroundColor == Color.RED) {
				JOptionPane.showMessageDialog(null, "Game Over !");
				System.exit(0);
			} else if (backgroundColor == Color.GREEN) {
				JOptionPane.showMessageDialog(null, "You Win !");
				System.exit(0);
			}
		}
	}
	
	public boolean collision(int x1, int y1, int x2, int y2) {
		int distance = (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		if (distance < 2 * Needle.Circle.RADIUS)
			return true;
		else
			return false;
	}
	
	public static void delay(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class KA extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				if (counter < needlesNumber) {
					needles.get(counter).setRotating(true);
					needles.get(counter).setLineVisible(true);
					needles.get(counter).setOnCircle(true);
					for (int i = counter; i < needlesNumber ; i++) {
						needles.get(i).setY1(needles.get(i).getY1() - 20);
						needles.get(i).setY2(needles.get(i).getY2() - 20);
					}
					counter++;
				}
				break;
			default:
				break;
			}
		}
		
	}
	
}
