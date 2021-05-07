package com.weisbrja.simulation;

import com.weisbrja.AppContext;

import javax.vecmath.Vector2d;

public class Muscle {

	private final Circle circle1;
	private final Circle circle2;

	private double clock;
	private double targetLength;

	// variables affected by mutations
	private double strength;
	private Vector2d lengthPhases;
	private Vector2d clockPhases;
	private double clockSpeed;

	public Muscle(Circle circle1, Circle circle2) {
		this.circle1 = circle1;
		this.circle2 = circle2;

		strength = (AppContext.getInstance().getMuscleStrengthBoundaries().getMin() + AppContext.getInstance().getMuscleStrengthBoundaries().getMax()) / 2d;
		lengthPhases = new Vector2d(AppContext.getInstance().getMuscleLengthPhasesBoundaries().getMin(), AppContext.getInstance().getMuscleLengthPhasesBoundaries().getMax());
		clockPhases = new Vector2d(0d, 0.5d);
		clockSpeed = (AppContext.getInstance().getMuscleClockSpeedBoundaries().getMin() + AppContext.getInstance().getMuscleClockSpeedBoundaries().getMax()) / 2d;
		reset();
	}

	public Muscle(Circle circle1, Circle circle2, double strength, Vector2d lengthPhases, Vector2d clockPhases, double clockSpeed) {
		this.circle1 = circle1;
		this.circle2 = circle2;
		this.strength = strength;
		this.lengthPhases = lengthPhases;
		this.clockPhases = clockPhases;
		this.clockSpeed = clockSpeed;
		reset();
	}

	public void reset() {
		clock = 0d;
	}

	public void randomize() {
		strength = AppContext.getInstance().getRandomNumberGenerator().getRandomRange(AppContext.getInstance().getMuscleStrengthBoundaries());
		lengthPhases = new Vector2d(AppContext.getInstance().getRandomNumberGenerator().getRandomRange(AppContext.getInstance().getMuscleLengthPhasesBoundaries()), AppContext.getInstance().getRandomNumberGenerator().getRandomRange(AppContext.getInstance().getMuscleLengthPhasesBoundaries()));
		clockPhases = new Vector2d(AppContext.getInstance().getRandomNumberGenerator().nextDouble(), AppContext.getInstance().getRandomNumberGenerator().nextDouble());
		clockSpeed = AppContext.getInstance().getRandomNumberGenerator().getRandomRange(AppContext.getInstance().getMuscleClockSpeedBoundaries());
	}

	public void update() {
		// update the target length based on the clock and the clock phases
		if (clockPhases.getX() < clockPhases.getY())
			if (clock >= clockPhases.getX() && clock < clockPhases.getY())
				targetLength = lengthPhases.getX();
			else
				targetLength = lengthPhases.getY();
		else
			if (clock >= clockPhases.getY() && clock < clockPhases.getX())
				targetLength = lengthPhases.getY();
			else
				targetLength = lengthPhases.getX();

		clock = (clock + clockSpeed) % 1d;

		// calculate the distance between the circles
		double positionDifferenceX = circle1.getPosition().getX() - circle2.getPosition().getX();
		double positionDifferenceY = circle1.getPosition().getY() - circle2.getPosition().getY();
		double distance = Math.sqrt(positionDifferenceX * positionDifferenceX + positionDifferenceY * positionDifferenceY);

		if (distance > 0d) {
			double forceScalingFactor = getForceScalingFactor(distance, targetLength);

			// apply the forces to the circles
			Vector2d force1 = (Vector2d) circle1.getPosition().clone();
			force1.sub(circle2.getPosition());
			force1.normalize();
			force1.scale(forceScalingFactor / 2d);

			Vector2d force2 = (Vector2d) circle2.getPosition().clone();
			force2.sub(circle1.getPosition());
			force2.normalize();
			force2.scale(forceScalingFactor / 2d);

			circle1.applyForce(force1);
			circle2.applyForce(force2);
		}
	}

	public double getForceScalingFactor(double distance, double targetLength) {
		double forceScalingFactor = 1d - distance / targetLength;
		return Math.min(Math.max(forceScalingFactor, -AppContext.getInstance().getMaxMuscleForce()), AppContext.getInstance().getMaxMuscleForce()) * strength;
	}

	public boolean getExpanding() {
		return targetLength == Math.max(lengthPhases.getX(), lengthPhases.getY());
	}

	public Circle getCircle1() {
		return circle1;
	}

	public Circle getCircle2() {
		return circle2;
	}

	public double getStrength() {
		return strength;
	}

	public Vector2d getLengthPhases() {
		return lengthPhases;
	}

	public Vector2d getClockPhases() {
		return clockPhases;
	}

	public double getClockSpeed() {
		return clockSpeed;
	}
}
