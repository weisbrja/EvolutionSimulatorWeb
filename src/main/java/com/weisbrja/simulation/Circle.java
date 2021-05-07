package com.weisbrja.simulation;

import com.weisbrja.AppContext;

import javax.vecmath.Vector2d;

public class Circle {

	private Vector2d position;
	private Vector2d velocity;
	private Vector2d acceleration;

	// variables affected by mutations
	private final Vector2d startPosition;
	private double frictionPercentage;
	private double radius;

	public Circle() {
		frictionPercentage = 0.5d;
		radius = (AppContext.getInstance().getCircleRadiusBoundaries().getMin() + AppContext.getInstance().getCircleRadiusBoundaries().getMax()) / 2d;
		double startPositionX = (AppContext.getInstance().getCircleStartPositionBoundaries().getXMin() + AppContext.getInstance().getCircleStartPositionBoundaries().getXMax()) / 2d;
		double startPositionY = (AppContext.getInstance().getCircleStartPositionBoundaries().getYMin() + AppContext.getInstance().getCircleStartPositionBoundaries().getYMax()) / 2d - radius;
		startPosition = new Vector2d(startPositionX, startPositionY);
		reset();
	}

	public Circle(double frictionPercentage, double radius, Vector2d startPosition) {
		this.frictionPercentage = frictionPercentage;
		this.radius = radius;
		this.startPosition = startPosition;
		reset();
	}

	public void reset() {
		position = (Vector2d) startPosition.clone();
		velocity = new Vector2d(0d, 0d);
		acceleration = new Vector2d(0d, 0d);
	}

	public void randomize() {
		frictionPercentage = AppContext.getInstance().getRandomNumberGenerator().nextDouble();
		radius = AppContext.getInstance().getRandomNumberGenerator().getRandomRange(AppContext.getInstance().getCircleRadiusBoundaries());
		startPosition.setX(AppContext.getInstance().getRandomNumberGenerator().getRandomRange(AppContext.getInstance().getCircleStartPositionBoundaries().getX()) - radius);
		startPosition.setY(AppContext.getInstance().getRandomNumberGenerator().getRandomRange(AppContext.getInstance().getCircleStartPositionBoundaries().getY()) - radius);
		reset();
	}

	public void update() {
		// check if the circle is rolling on the ground
		if (getOnGround() && velocity.getY() == 0d) {
			// add the x-acceleration to the x-velocity
			velocity.setX(velocity.getX() + acceleration.getX());

			// add the y-acceleration to the y-velocity if it is lifting the circle up
			if (acceleration.getY() < 0d)
				velocity.setY(velocity.getY() + acceleration.getY());

			// apply friction to the x-velocity
			velocity.setX(velocity.getX() * (1d - frictionPercentage));
		} else {
			// add the acceleration to the velocity
			velocity.add(acceleration);
		}

		// apply air friction to the velocity
		velocity.scale(AppContext.getInstance().getAirFriction());

		// check if the circle would collide with the ground
		if (position.getY() + velocity.getY() + radius > 0d) {
			// move the circle until it collides with the ground
			Vector2d velocity1 = (Vector2d) velocity.clone();
			velocity1.scale((-radius - position.getY()) / velocity.getY());
			position.add(velocity1);

			// negate the y-velocity and scale it by the ground dampening factor
			velocity.setY(-velocity.getY() * AppContext.getInstance().getGroundDamping());

			// calculate the rest of the velocity
			Vector2d velocity2 = (Vector2d) velocity.clone();
			velocity2.normalize();
			velocity2.scale(velocity.length() - velocity1.length());

			// check if the rest y-velocity is less than or equal to the gravity-y
			if (Math.abs(velocity2.getY()) <= AppContext.getInstance().getGravityY()) {
				// set the rest y-velocity and the y-velocity to zero
				velocity2.setY(0d);
				velocity.setY(0d);
			}

			// add the rest of the velocity to the position
			position.add(velocity2);
		} else {
			// add the velocity to the position
			position.add(velocity);
		}

		// reset the acceleration
		acceleration.scale(0d);
	}

	public void applyForce(Vector2d force) {
		acceleration.add(force);
	}

	public void applyForceY(double forceY) {
		acceleration.setY(acceleration.getY() + forceY);
	}

	public boolean getOnGround() {
		return position.getY() + radius == 0d && velocity.getY() == 0d;
	}

	public double getFrictionPercentage() {
		return frictionPercentage;
	}

	public double getRadius() {
		return radius;
	}

	public Vector2d getStartPosition() {
		return startPosition;
	}

	public Vector2d getPosition() {
		return position;
	}
}
