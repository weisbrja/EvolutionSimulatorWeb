package com.weisbrja.simulation;

import com.weisbrja.AppContext;

public class Simulator {

	private Creature creature;

	private double fitness;
	private double probability;

	public Simulator() {
	}

	public Simulator(Creature creature) {
		this.creature = creature;
	}

	public void generateRandomCreature() {
		creature = new Creature();
		creature.randomize();

		// generate random circles
		int randomCircleCount = 2 + AppContext.getInstance().getRandomNumberGenerator().nextInt(4);
		for (int i = 0; i < randomCircleCount; i++) {
			Circle circle = new Circle();
			circle.randomize();
			creature.getCircles().add(circle);
		}
		creature.adjustToGround();
		creature.calculatePossibleConnections();

		// generate random muscles
		int maxMuscleCount = randomCircleCount * (randomCircleCount - 1) / 2;
		int randomMuscleCount = Math.min(2, AppContext.getInstance().getRandomNumberGenerator().nextInt(maxMuscleCount + 1));
		for (int i = 0; i < randomMuscleCount; i++)
			creature.addRandomMuscle();
	}

	private void nextSimulationStep() {
		creature.applyForceY(AppContext.getInstance().getGravityY());
		creature.update();
	}

	private void nextDrawStep() {
		AppContext.getInstance().getEventBus().emit(new CreatureDrawEvent(creature));
	}

	public void start(int cycleCount) {
		// simulate the creature as fast as possible for the given number of cycles
		for (int i = 0; i < cycleCount; i++) {
			if (AppContext.getInstance().getStopCreaturesWhenOnGround() && creature.getOnGround())
				break;
			nextSimulationStep();
		}
	}

	public void startGraphically(int cycleCount) {
//		AppContext.getInstance().getEventBus().emit(new SimulatorStartedEvent(creature.getSpecies(), creature.getMutationRate(), creature.getStructuralMutationRate()));

		// simulate and draw the creature for the given number of cycles
		for (int i = 0; i < cycleCount; i++) {
			if (AppContext.getInstance().getStopCreaturesWhenOnGround() && creature.getOnGround())
				break;
			nextSimulationStep();
			nextDrawStep();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		AppContext.getInstance().getEventBus().emit(new SimulatorDoneEvent());
	}

	public Creature getCreature() {
		return creature;
	}

	public void setCreature(Creature creature) {
		this.creature = creature;
	}

	public void calculateFitness() {
		fitness = creature.getFitness();
	}

	public double getFitness() {
		return fitness;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}
}
