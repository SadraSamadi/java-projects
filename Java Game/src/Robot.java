import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Robot extends MovableObject {
	
	private Image[] imgRobot = new Image[3];
	private Image imgDown;
	private Image imgJumped;
	private boolean isDown = false;
	private boolean isJumped = false;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public Robot(int x, int y, int speedX, int speedY, ImageObserver imageObserver) {
		super(x, y, speedX, speedY, imageObserver);
		
		for (int i = 0; i < 3; i++) {
			imgRobot[i] = new ImageIcon("res/robot_" + i + ".png").getImage();
		}
		
		imgDown = new ImageIcon("res/robot_down.png").getImage();
				
		imgJumped = new ImageIcon("res/robot_jumped.png").getImage();
		
		setVisible(true);
		
		startThread();
	}
	
	@Override
	public void run() {
		super.run();
		while (true) {
			if (!isDown && !isJumped) {
				for (int i = 0; i < 6; i++) {
					if (i < 3) {
						setImg(imgRobot[i]);
					} else {
						setImg(imgRobot[5 - i]);
					}
					Animate.delay(50);
				}
				Animate.delay(2000);
			}
		}
	}

	public void jump() {
		Thread jump = new Thread() {
			public void run() {
				isJumped = true;
				setImg(imgJumped);
				int max = 20;
				for (int t = 0; t < max; t++) {
					if (t < max / 2) {
						setSpeedY(-(max / 2 - 1 - t));
					} else {
						setSpeedY((t - max / 2));
					}
					Animate.delay(20);
				}
				setSpeedY(0);
				setNormal();
			}
		};
		jump.start();
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public void fire() {
		if (!isDown && !isJumped) {
			Bullet temp = new Bullet(getX() + 50, getY() - 25, getImageObserver());
			temp.setSpeedX(10);
			temp.setImg(new ImageIcon("res/bullet1.png").getImage());
			bullets.add(temp);
		}
	}
	
	public void setNormal() {
		this.isDown = false;
		
		this.isJumped = false;
		
		setImg(imgRobot[0]);
	}
	
	public void setDown() {
		this.isDown = true;
		setImg(imgDown);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			if (!isJumped)
				setDown();
			break;
		case KeyEvent.VK_RIGHT:
			setSpeedX(6);
			break;
		case KeyEvent.VK_LEFT:
			setSpeedX(-6);
			break;
		case KeyEvent.VK_SPACE:
			if (!isDown)
				jump();
			break;
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			setNormal();
			break;
		case KeyEvent.VK_RIGHT:
			setSpeedX(0);
			break;
		case KeyEvent.VK_LEFT:
			setSpeedX(0);
			break;
		default:
			break;
		}
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public boolean isJumped() {
		return isJumped;
	}

	public void setJumped(boolean isJumped) {
		this.isJumped = isJumped;
	}
	
	
	
}