import java.awt.Color;
import java.awt.Graphics2D;

public class Circle {

	private float x;
	private float y;
	private float speedX;
	private float speedY;
	private float radius;
	private Color color;
	private boolean flag = true;
	private float mass;

	public Circle(float x, float y, float speedX, float speedY, float radius,
			Color color) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.radius = radius;
		this.color = color;
		this.mass = radius;
	}

	public void paint(Graphics2D g) {
		g.setColor(color);
		g.fillOval((int) (x - radius), (int) (y - radius), (int) (2 * radius),
				(int) (2 * radius));
	}

	public void move() {
		this.x += speedX;
		this.y += speedY;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x + radius;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y + radius;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void invertSpeedX() {
		speedX *= -1;
	}

	public void invertSpeedY() {
		speedY *= -1;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

}