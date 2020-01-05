import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovableObject extends Thread implements Runnable, KeyListener {

	private Image img;
	private int width;
	private int HEIGHT;
	private int x;
	private int y;
	private int speedX;
	private int speedY;
	private boolean visible;

	public MovableObject() {
		
	}

	public MovableObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public MovableObject(int x, int y, int speedX, int speedY) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
	}

	public MovableObject(int x, int y, int speedX, int speedY, Image img) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.img = img;
		width = img.getWidth(null);
		HEIGHT = img.getHeight(null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void invertSpeedX() {
		speedX *= -1;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public void invertSpeedY() {
		speedY *= -1;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getImgWidth() {
		return img.getWidth(null);
	}

	public int getImgHEIGHT() {
		return img.getHeight(null);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int HEIGHT) {
		this.HEIGHT = HEIGHT;
	}

	public void goRight(int speed) {
		x += speed;
	}
	
	public void goLeft(int speed) {
		x -= speed;
	}
	
	public void goUp(int speed) {
		y -= speed;
	}

	public void goDown(int speed) {
		y += speed;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void move() {
		x += speedX;
		y += speedY;
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	public void paint(Graphics2D g) {
		
	}

	@Override
	public void run() {

	}

}