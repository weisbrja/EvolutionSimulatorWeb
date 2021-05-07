package com.weisbrja;

import com.weisbrja.event.EventBus;
import com.weisbrja.simulation.Boundary2d;
import com.weisbrja.simulation.Boundary4d;

public class AppContext {

	private static AppContext instance;

	private final EventBus eventBus;

	private final RandomNumberGenerator randomNumberGenerator;

	private final boolean stopCreaturesWhenOnGround;

	private final double gravityY;
	private final double groundDamping;
	private final double airFriction;

	private final Boundary4d circleStartPositionBoundaries;
	private final Boundary2d circleRadiusBoundaries;

	private final double maxMuscleForce;
	private final Boundary2d muscleStrengthBoundaries;
	private final Boundary2d muscleLengthPhasesBoundaries;
	private final Boundary2d muscleClockSpeedBoundaries;

	private final Boundary2d creatureMutationRateBoundaries;
	private final Boundary2d creatureStructuralMutationRateBoundaries;

	private AppContext(EventBus eventBus, RandomNumberGenerator randomNumberGenerator, boolean stopCreaturesWhenOnGround, double gravityY, double groundDamping, double airFriction, Boundary4d circleStartPositionBoundaries, Boundary2d circleRadiusBoundaries, double maxMuscleForce, Boundary2d muscleStrengthBoundaries, Boundary2d muscleLengthPhasesBoundaries, Boundary2d muscleClockSpeedBoundaries, Boundary2d creatureMutationRateBoundaries, Boundary2d creatureStructuralMutationRateBoundaries) {
		this.eventBus = eventBus;
		this.randomNumberGenerator = randomNumberGenerator;
		this.stopCreaturesWhenOnGround = stopCreaturesWhenOnGround;
		this.gravityY = gravityY;
		this.groundDamping = groundDamping;
		this.airFriction = airFriction;
		this.maxMuscleForce = maxMuscleForce;
		this.circleStartPositionBoundaries = circleStartPositionBoundaries;
		this.circleRadiusBoundaries = circleRadiusBoundaries;
		this.muscleStrengthBoundaries = muscleStrengthBoundaries;
		this.muscleLengthPhasesBoundaries = muscleLengthPhasesBoundaries;
		this.muscleClockSpeedBoundaries = muscleClockSpeedBoundaries;
		this.creatureMutationRateBoundaries = creatureMutationRateBoundaries;
		this.creatureStructuralMutationRateBoundaries = creatureStructuralMutationRateBoundaries;
	}

	public static void newInstance(EventBus eventBus, RandomNumberGenerator randomNumberGenerator, boolean stopCreaturesWhenOnGround, double gravityY, double groundDamping, double airFriction, Boundary4d circleStartPositionBoundaries, Boundary2d circleRadiusBoundaries, double maxMuscleForce, Boundary2d muscleStrengthBoundaries, Boundary2d muscleLengthPhasesBoundaries, Boundary2d muscleClockSpeedBoundaries, Boundary2d creatureMutationRateBoundaries, Boundary2d creatureStructuralMutationRateBoundaries) {
		instance = new AppContext(eventBus, randomNumberGenerator, stopCreaturesWhenOnGround, gravityY, groundDamping, airFriction, circleStartPositionBoundaries, circleRadiusBoundaries, maxMuscleForce, muscleStrengthBoundaries, muscleLengthPhasesBoundaries, muscleClockSpeedBoundaries, creatureMutationRateBoundaries, creatureStructuralMutationRateBoundaries);
	}

	public static AppContext getInstance() {
		return instance;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public RandomNumberGenerator getRandomNumberGenerator() {
		return randomNumberGenerator;
	}

	public boolean getStopCreaturesWhenOnGround() {
		return stopCreaturesWhenOnGround;
	}

	public double getGravityY() {
		return gravityY;
	}

	public double getGroundDamping() {
		return groundDamping;
	}

	public double getAirFriction() {
		return airFriction;
	}

	public Boundary2d getCircleRadiusBoundaries() {
		return circleRadiusBoundaries;
	}

	public Boundary4d getCircleStartPositionBoundaries() {
		return circleStartPositionBoundaries;
	}

	public double getMaxMuscleForce() {
		return maxMuscleForce;
	}

	public Boundary2d getMuscleStrengthBoundaries() {
		return muscleStrengthBoundaries;
	}

	public Boundary2d getMuscleLengthPhasesBoundaries() {
		return muscleLengthPhasesBoundaries;
	}

	public Boundary2d getMuscleClockSpeedBoundaries() {
		return muscleClockSpeedBoundaries;
	}

	public Boundary2d getCreatureMutationRateBoundaries() {
		return creatureMutationRateBoundaries;
	}

	public Boundary2d getCreatureStructuralMutationRateBoundaries() {
		return creatureStructuralMutationRateBoundaries;
	}
}
