import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;

public class MovableObject implements Runnable, KeyListener {
	
	private Image img = null;
	private int imgWidth = 0;
	private int imgHeight = 0;
	private int x = 0;
	private int y = 0;
	private int speedX = 0;
	private int speedY = 0;
	private boolean visible = false;
	private ImageObserver imageObserver;
	private Thread thread;

	public MovableObject() {
		
	}
	
	public MovableObject(ImageObserver imageObserver) {
		setImageObserver(imageObserver);
	}
	
	public MovableObject(int x, int y, ImageObserver imageObserver) {
		setX(x);
		setY(y);
		setImageObserver(imageObserver);
	}
		
	public MovableObject(int x, int y, int speedX, int speedY, ImageObserver imageObserver) {
		setX(x);
		setY(y);
		setSpeedX(speedX);
		setSpeedY(speedY);
		setImageObserver(imageObserver);
	}
	
	public MovableObject(int x, int y, int speedX, int speedY, Image img, ImageObserver imageObserver) {
		setImg(img);
		setX(x);
		setY(y);
		setSpeedX(speedX);
		setSpeedY(speedY);
		setImageObserver(imageObserver);
	}
	
	public int getX() {
		return x + this.imgWidth / 2;
	}
	
	public void startThread() {
		thread = new Thread(this);
		this.thread.start();
	}

	public void setX(int x) {
		this.x = x - this.imgWidth / 2;
	}

	public int getY() {
		return y + this.imgHeight / 2;
	}

	public void setY(int y) {
		this.y = y - this.imgHeight / 2;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	public void invertSpeedX() {
		setSpeedX(getSpeedX() * -1);
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	
	public void invertSpeedY() {
		setSpeedY(getSpeedY() * -1);
	}

	public Image getImg() {
		return img;
	}
	
	public void setImg(Image img) {
		this.img = img;
	}

	public int getImgWidth() {
		return this.img.getWidth(getImageObserver());
	}
		
	public int getImgHeight() {
		return this.img.getHeight(getImageObserver());
	}
	
	public void goRight(int speed) {
		setX(getX() + speed);
	}

	public void goLeft(int speed) {
		setX(getX() - speed);
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void move() {
		setX(getX() + getSpeedX());
		setY(getY() + getSpeedY());
	}
	
	public ImageObserver getImageObserver() {
		return imageObserver;
	}

	public void setImageObserver(ImageObserver imageObserver) {
		this.imageObserver = imageObserver;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void run() {
		
	}
	
}
