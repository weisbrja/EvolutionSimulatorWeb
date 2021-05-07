package com.weisbrja.simulation;

import com.weisbrja.AppContext;

import javax.vecmath.Vector2d;
import java.util.*;

public class Creature {

	private final List<Circle> circles;
	private final List<Muscle> muscles;
	private final LinkedHashSet<Set<Circle>> possibleConnectionSet;

	// variables affected by mutations
	private double mutationRate;
	private double structuralMutationRate;

	public Creature() {
		circles = new ArrayList<>();
		muscles = new ArrayList<>();
		possibleConnectionSet = new LinkedHashSet<>();

		mutationRate = 0.01d;
		structuralMutationRate = 0.01d;
	}

	public Creature(double mutationRate, double structuralMutationRate) {
		this.mutationRate = mutationRate;
		this.structuralMutationRate = structuralMutationRate;

		circles = new ArrayList<>();
		muscles = new ArrayList<>();

		possibleConnectionSet = new LinkedHashSet<>();
	}

	public void reset() {
		for (Circle circle : circles)
			circle.reset();
		for (Muscle muscle : muscles)
			muscle.reset();
	}

	public void randomize() {
		mutationRate = AppContext.getInstance().getRandomNumberGenerator().getRandomRange(AppContext.getInstance().getCreatureMutationRateBoundaries());
		structuralMutationRate = AppContext.getInstance().getRandomNumberGenerator().getRandomRange(AppContext.getInstance().getCreatureStructuralMutationRateBoundaries());
	}

	public void adjustToGround() {
		// find the lowest start position-y of every circle
		double lowestStartPositionY = Double.NEGATIVE_INFINITY;
		for (Circle circle : circles) {
			double circleStartPositionY = circle.getStartPosition().getY() + circle.getRadius();
			if (circleStartPositionY > lowestStartPositionY)
				lowestStartPositionY = circleStartPositionY;
		}

		// adjust the creature to the ground
		for (Circle circle : circles) {
			circle.getStartPosition().setY(circle.getStartPosition().getY() - lowestStartPositionY);
			circle.getPosition().setY(circle.getStartPosition().getY());
		}
	}

	public void update() {
		for (Muscle muscle : muscles)
			muscle.update();
		for (Circle circle : circles)
			circle.update();
	}

	public void applyForceY(double forceY) {
		for (Circle circle : circles)
			circle.applyForceY(forceY);
	}

	public void calculatePossibleConnections() {
		// calculate all possible connections
		for (int i = 0; i < circles.size() - 1; i++)
			for (int j = i + 1; j < circles.size(); j++)
				addPossibleConnection(circles.get(i), circles.get(j));

		// remove existing connections from possible connections
		for (Muscle muscle : muscles)
			removePossibleConnection(muscle.getCircle1(), muscle.getCircle2());
	}

	private void removePossibleConnection(Circle circle1, Circle circle2) {
		possibleConnectionSet.remove(getConnection(circle1, circle2));
	}

	private void addPossibleConnection(Circle circle1, Circle circle2) {
		possibleConnectionSet.add(getConnection(circle1, circle2));
	}

	private Set<Circle> getConnection(Circle circle1, Circle circle2) {
		Set<Circle> connection = new HashSet<>();
		connection.add(circle1);
		connection.add(circle2);
		return connection;
	}

	public void addRandomCircle() {
		Circle circle = new Circle();
		for (Circle possibleCircle : circles)
			addPossibleConnection(circle, possibleCircle);
		circle.randomize();
		circles.add(circle);

		addRandomMuscle(circle);
		addRandomMuscle(circle);
	}

	public void removeRandomCircle() {
		if (circles.size() > 1) {
			Circle circle = circles.get(AppContext.getInstance().getRandomNumberGenerator().nextInt(circles.size()));
			circles.remove(circle);

			possibleConnectionSet.removeIf(possibleConnection -> possibleConnection.contains(circle));

			muscles.removeIf(muscle -> muscle.getCircle1() == circle || muscle.getCircle2() == circle);
		}
	}

	public void addRandomMuscle() {
		if (!possibleConnectionSet.isEmpty()) {
			int index = AppContext.getInstance().getRandomNumberGenerator().nextInt(possibleConnectionSet.size());
			for (Set<Circle> possibleConnection : possibleConnectionSet)
				if (index-- == 0) {
					Iterator<Circle> iterator = possibleConnection.iterator();
					Circle circle1 = iterator.next();
					Circle circle2 = iterator.next();

					removePossibleConnection(circle1, circle2);

					Muscle muscle = new Muscle(circle1, circle2);
					muscle.randomize();
					muscles.add(muscle);

					break;
				}
		}
	}

	public void addRandomMuscle(Circle circle) {
		if (!possibleConnectionSet.isEmpty()) {
			List<Set<Circle>> possiblePossibleConnections = new ArrayList<>();
			for (Set<Circle> possibleConnection : possibleConnectionSet)
				if (possibleConnection.contains(circle))
					possiblePossibleConnections.add(possibleConnection);

			if (possiblePossibleConnections.size() > 0) {
				Set<Circle> connection = possiblePossibleConnections.get(AppContext.getInstance().getRandomNumberGenerator().nextInt(possiblePossibleConnections.size()));
				Iterator<Circle> iterator = connection.iterator();
				Circle circle1 = iterator.next();
				Circle circle2 = iterator.next();

				removePossibleConnection(circle1, circle2);

				Muscle muscle = new Muscle(circle1, circle2);
				muscle.randomize();
				muscles.add(muscle);
			}
		}
	}

	public void removeRandomMuscle() {
		if (!muscles.isEmpty()) {
			Muscle muscle = muscles.get(AppContext.getInstance().getRandomNumberGenerator().nextInt(muscles.size()));
			muscles.remove(muscle);

			addPossibleConnection(muscle.getCircle1(), muscle.getCircle2());
		}
	}

	public double getFitness() {
		if (getOnGround())
			return AppContext.getInstance().getCircleStartPositionBoundaries().getXMin();
		else {
			double fitness = 0d;
			for (Circle circle : circles)
				fitness += circle.getPosition().getX();
			return fitness / circles.size();
		}
	}

	public boolean getOnGround() {
		boolean onGround = true;
		for (Circle circle : circles)
			if (!circle.getOnGround()) {
				onGround = false;
				break;
			}
		return onGround;
	}

	public Vector2d getPosition() {
		// return the average position of all the circles
		Vector2d position = new Vector2d();
		for (Circle circle : circles)
			position.add(circle.getPosition());
		position.scale(1d / circles.size());
		return position;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public double getStructuralMutationRate() {
		return structuralMutationRate;
	}

	public String getSpecies() {
		return "S" + circles.size() + "-" + muscles.size();
	}

	public List<Circle> getCircles() {
		return circles;
	}

	public List<Muscle> getMuscles() {
		return muscles;
	}
}
