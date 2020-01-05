import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Rubber {

	private final int LX = 109;
	private final int LY = 408;
	private final int CX = RedBird.X0 - 5;
	private final int CY = RedBird.Y0 + 10;
	private final int RX = 145;
	private final int RY = 408;
	private final int WIDTH = 3;
	private final Color color = Color.DARK_GRAY;

	private int x;
	private int y;

	public Rubber() {
		x = CX;
		y = CY;
	}

	private Graphics2D setGraphics(Graphics2D g) {
		g.setColor(color);
		g.setStroke(new BasicStroke(WIDTH));
		return g;
	}

	public void paintRightLine(Graphics2D g) {
		g = setGraphics(g);
		g.drawLine(RX, RY, x, y);
	}

	public void paintLeftLine(Graphics2D g) {
		g = setGraphics(g);
		g.drawLine(LX, LY, x, y);
	}

	public void setX(int x) {
		this.x = x - 5;
	}

	public void setY(int y) {
		this.y = y + 10;
	}

	public void setNormal() {
		x = CX;
		y = CY;
	}

}
