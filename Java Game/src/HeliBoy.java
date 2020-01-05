import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class HeliBoy extends MovableObject {
	
	private Image[] imgHeliBoy = new Image[5];
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public HeliBoy(int x, int y, int speedX, int speedY, ImageObserver imageObserver) {
		super(x, y, speedX, speedY, imageObserver);
		
		for (int i = 0; i < 5; i++) {
			imgHeliBoy[i] = new ImageIcon("res/heliboy_" + i + ".png").getImage();
		}
		
		setVisible(true);
		
		startThread();
	}
	
	@Override
	public void run() {
		super.run();
		long f = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() - f > 2000) {
				fire();
				f = System.currentTimeMillis();
			}
			
			for (int i = 0; i < 10; i++) {
				if (i < 5) {
					setImg(imgHeliBoy[i]);
				} else {
					setImg(imgHeliBoy[9 - i]);
				}
				Animate.delay(25);
			}
		}
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public void fire() {
		Bullet temp = new Bullet(getX() - 38, getY() + 8, getImageObserver());
		temp.setSpeedX(-3);
		temp.setImg(new ImageIcon("res/bullet.png").getImage());
		bullets.add(temp);
	}
	
}
