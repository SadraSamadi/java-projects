package aa;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Needle {
	
	private int X1, X2, Y1, Y2;
	private int needleNnumber;
	private boolean visible;
	private boolean onCircle;
	private boolean checked;
	private boolean lineVisible;
	private boolean rotating;
	private Circle circle;
	private Line line;
	private Number number;
	private int angle = 360;
	private final int SPEED = 1;
	
	public Needle(int x1, int y1, int x2, int y2, int number) {
		this.X1 = x1;
		this.X2 = x2;
		this.Y1 = y1;
		this.Y2 = y2;
		this.needleNnumber = number;
		circle = new Circle(this.X2, this.Y2);
		line = new Line(this.X1, this.Y1, this.X2, this.Y2);
		this.number = new Number(this.X2 - 3, this.Y2 + 3);
		checked = false;
		onCircle = false;
		visible = false;
		lineVisible = false;
		rotating = false;
	}
	
	public void paint(Graphics2D g) {
		circle.paint(g);
		circle.update();
		if (lineVisible)
			line.paint(g);
		line.update();
		number.paint(g);
		number.update();
	}
	
	public void rotate() {
		X1 = (int) (Panel.circleRadius * Math.cos(5 * Math.PI / 2 - Math.toRadians(angle)));
		Y1 = (int) (Panel.circleRadius * Math.sin(5 * Math.PI / 2 - Math.toRadians(angle)));
		X2 = (int) (180 * Math.cos(5 * Math.PI / 2 - Math.toRadians(angle)));
		Y2 = (int) (180 * Math.sin(5 * Math.PI / 2 - Math.toRadians(angle)));
		
		if (angle > 0)
			angle -= SPEED;
		else
			angle = 360;
	}

	public int getX2() {
		return X2;
	}

	public int getY2() {
		return Y2;
	}

	public int getY1() {
		return Y1;
	}

	public void setY1(int y1) {
		Y1 = y1;
	}

	public void setY2(int y2) {
		Y2 = y2;
	}
	
	public boolean isOnCircle() {
		return onCircle;
	}

	public void setOnCircle(boolean onCircle) {
		this.onCircle = onCircle;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isLineVisible() {
		return lineVisible;
	}

	public void setLineVisible(boolean lineVisible) {
		this.lineVisible = lineVisible;
	}

	public boolean isRotating() {
		return rotating;
	}

	public void setRotating(boolean rotating) {
		this.rotating = rotating;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public class Circle {
		
		private int x, y;
		public static final int RADIUS = 8;
		private Color color;
		
		public Circle(int x, int y) {
			this.x = x;
			this.y = y;
			this.color = Color.BLACK;
		}
		
		public void update() {
			x = X2;
			y = Y2;
		}
		
		private void paint(Graphics2D g) {
			g.setColor(color);
			g.fillOval(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
		}
		
	}
	
	private class Line {
		
		private int x1, x2, y1, y2;
		private Color color;
		
		public Line(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
			this.color = Color.BLACK;
		}
		
		public void update() {
			x1 = X1;
			y1 = Y1;
			x2 = X2;
			y2 = Y2;
		}
		
		private void paint(Graphics2D g) {
			g.setColor(color);
			g.drawLine(x1, y1, x2, y2);
		}
		
	}
	
	private class Number {
		
		private int x, y;
		private int number;
		private Color color;
		
		public Number(int x, int y) {
			this.x = x;
			this.y = y;
			this.number = needleNnumber;
			this.color = Color.WHITE;
		}
		
		public void update() {
			x = X2 - 3;
			y = Y2 + 3;
		}
		
		private void paint(Graphics2D g) {
			if (number != - 1) {
				g.setColor(color);
				g.setFont(new Font("", Font.BOLD, 8));
				g.drawString(String.valueOf(number), x, y);
			}
		}
		
	}
	
}
