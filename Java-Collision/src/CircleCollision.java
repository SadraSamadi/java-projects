public class CircleCollision {

	public boolean clash(Circle c1, Circle c2) {
		float distance = (float) Math.sqrt(Math.pow(c1.getX() - c2.getX(), 2)
				+ Math.pow(c1.getY() - c2.getY(), 2));
		return distance <= (c1.getRadius() + c2.getRadius());
	}

	private float getPhi(Circle c1, Circle c2) {
		float value = (float) (Math.atan((float) -(c2.getY() - c1.getY())
				/ (float) (c2.getX() - c1.getX())));
		return (float) ((c1.getX() < c2.getX()) ? value + Math.PI : value);
	}

	private float getTheta(Circle c1, Circle c2) {
		float value = (float) (Math.atan(((float) -c1.getSpeedY() / (float) c1
				.getSpeedX())));
		return (float) ((c1.getSpeedX() < 0) ? value + Math.PI : value);
	}

	private float getAlpha(float phi, float tetha, Circle c1) {
		return (float) (2 * phi - (tetha - Math.PI));
	}

	public void changeSpeed(Circle c1, Circle c2) {
		float phi = getPhi(c1, c2);
		float tetha = getTheta(c1, c2);
		float alpha = getAlpha(phi, tetha, c1);
		float speed = (float) Math.sqrt(Math.pow(c1.getSpeedX(), 2)
				+ Math.pow(c1.getSpeedY(), 2));
		float speedX = (float) (speed * Math.cos(alpha));
		float speedY = (float) -(speed * Math.sin(alpha));
		c1.setSpeedX(speedX);
		c1.setSpeedY(speedY);
	}

}