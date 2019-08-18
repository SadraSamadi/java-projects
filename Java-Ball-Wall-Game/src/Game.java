import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JFrame {
	
	Random rn = new Random();
	
	private static final int BOX_WIDTH = 450;
	private static final int BOX_HEIGHT = 400;
	
	private static final int WALL_WIDTH = 100;
	private static final int WALL_HEIGHT = 20;
	private final float BOTTOM_WALL_SPEED;
	private final float TOP_WALL_SPEED;
	private static float bottomWallX = 200;
	private static float bottomWallY = BOX_HEIGHT - WALL_HEIGHT;
	private static float topWallX = 200;
	private static float topWallY = 0;
	
	private static float sp = 0;
	
	private static float ballRadius = 25;
	private static float ballX;
	private static float ballY;
	private static float ballSpeedX = 3;
	private static float ballSpeedY = 3;
	
	private static final int UPDATE_RATE = 60;
	
	private static boolean go = false;
	private static String onGo;
	private static int size;
	
	private final int RANGE;
		
	public Game(int range, float speed) {
		this.RANGE = range;
		this.BOTTOM_WALL_SPEED = speed;
		this.TOP_WALL_SPEED = 9 - speed;
		
		ballX = rn.nextInt((int) (BOX_WIDTH - 2 * ballRadius)) + ballRadius;
		ballY = BOX_HEIGHT / 4;
		
		if (rn.nextBoolean()) {
			ballSpeedX *= -1;
		}
		
		final long startTime = System.currentTimeMillis();
		
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(Color.GREEN);
				g.fillRect(0, 0, BOX_WIDTH, BOX_HEIGHT);
				
				g.setColor(Color.DARK_GRAY);
				g.fillRect((int) bottomWallX, (int) bottomWallY, WALL_WIDTH, WALL_HEIGHT);
				g.fillRect((int) topWallX, (int) topWallY, WALL_WIDTH, WALL_HEIGHT);
				
				g.setColor(Color.RED);
				g.fillOval((int) (ballX - ballRadius), (int) (ballY - ballRadius), (int) (2 * ballRadius), (int) (2 * ballRadius));
				
				if (go) {
					g.setColor(Color.BLUE);
					g.setFont(new Font("Consolas", Font.BOLD, size));
					g.drawString(onGo, 180, 240);
				}
			}
		};
		panel.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
		
		Thread thread = new Thread() {
			public void run() {
				while (true) {
					ballX += ballSpeedX;
					ballY += ballSpeedY;
					
					if (ballX - ballRadius < 0) {
						ballSpeedX *= -1;
					}
					
					if (ballX + ballRadius > BOX_WIDTH) {
						ballSpeedX *= -1;
					}
					
					if (ballY < RANGE && ballSpeedY < 0) {
						if (ballX < topWallX + WALL_WIDTH / 2) {
							if (topWallX > 0) {
								topWallX += -TOP_WALL_SPEED;
							}
						} else if (ballX > topWallX + WALL_WIDTH / 2) {
							if (topWallX < BOX_WIDTH - WALL_WIDTH) {
								topWallX += TOP_WALL_SPEED;
							}
						}
					}
										
					if (ballY - ballRadius < WALL_HEIGHT && ballSpeedY < 0) {
						if (ballX >= topWallX && ballX <= topWallX + WALL_WIDTH) {
							ballSpeedY *= -1;
						} else if (ballX < topWallX && ballX > topWallX - ballRadius / 2) {
							double t = Math.sqrt(Math.pow(ballY - WALL_HEIGHT, 2) +
									Math.pow(ballX - topWallX, 2));
							if (t < ballRadius) {
								ballSpeedY *= -1;
							}
						} else if (ballX > topWallX + WALL_WIDTH && ballX < topWallX + WALL_WIDTH + ballRadius / 2) {
							double t = Math.sqrt(Math.pow(ballY - (BOX_HEIGHT - WALL_HEIGHT), 2) +
									Math.pow(ballX - (topWallX + WALL_WIDTH), 2));
							if (t < ballRadius) {
								ballSpeedY *= -1;
							}
						} else {
							if (ballY - ballRadius < 0) {
								JOptionPane.showMessageDialog(null, "You Win !\nin " +
										(System.currentTimeMillis() - startTime) / 1000 + " seconds");
								break;
							}
						}
					}
					
					if (ballY + ballRadius > BOX_HEIGHT - WALL_HEIGHT && ballSpeedY > 0) {
						if (ballX >= bottomWallX && ballX <= bottomWallX + WALL_WIDTH) {
							ballSpeedY *= -1;
						} else if (ballX < bottomWallX && ballX > bottomWallX - ballRadius / 2) {
							double t = Math.sqrt(Math.pow(ballY - (BOX_HEIGHT - WALL_HEIGHT), 2) +
									Math.pow(ballX - bottomWallX, 2));
							if (t < ballRadius) {
								ballSpeedY *= -1;
							}
						} else if (ballX > bottomWallX + WALL_WIDTH && ballX < bottomWallX + WALL_WIDTH + ballRadius / 2) {
							double t = Math.sqrt(Math.pow(ballY - (BOX_HEIGHT - WALL_HEIGHT), 2) +
									Math.pow(ballX - (bottomWallX + WALL_WIDTH), 2));
							if (t < ballRadius) {
								ballSpeedY *= -1;
							}
						} else {
							if (ballY + ballRadius > BOX_HEIGHT) {
								JOptionPane.showMessageDialog(null, "You Lost !\nin " +
										(System.currentTimeMillis() - startTime) / 1000 + " seconds");
								break;
							}
						}
					}
					
					panel.repaint();
					
					bottomWallX += sp;
					
					try {
						Thread.sleep(1000 / UPDATE_RATE);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				dispose();
			}
		};
		
		Thread start = new Thread() {
			public void run() {
				go = true;
				for (int i = 3; i > 0; i--) {
					onGo = String.valueOf(i);
					for (int j = 200; j > 100; j--) {
						size = j;
						repaint();
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				go = false;
				thread.start();
			}
		};
		start.start();
		
		setTitle("Game");
		setLocation(450, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(panel);
		pack();
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
	}
	
	private class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			switch (arg0.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				if (bottomWallX >= BOX_WIDTH - WALL_WIDTH)
					sp = 0;
				else
					sp = BOTTOM_WALL_SPEED;
				break;
			case KeyEvent.VK_LEFT:
				if (bottomWallX <= 0)
					sp = 0;
				else
					sp = -BOTTOM_WALL_SPEED;
				break;
			default:
				break;
			}
		}
	
		@Override
		public void keyReleased(KeyEvent arg0) {
			switch (arg0.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				sp = 0;
				break;
			case KeyEvent.VK_LEFT:
				sp = 0;
				break;
			default:
				break;
			}
		}
	
		@Override
		public void keyTyped(KeyEvent arg0) {
			
		}
	}
	
	
}
