import java.awt.image.ImageObserver;

public class Bullet extends MovableObject {
	
	public Bullet(int x, int y, ImageObserver imageObserver) {
		super(x, y, imageObserver);
		setVisible(true);
	}
	
	@Override
	public void move() {
		super.move();
		if (getX() > Animate.BOX_WIDTH + 50) {
			setVisible(false);
		}
	}

}
