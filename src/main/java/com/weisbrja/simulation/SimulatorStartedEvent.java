package com.weisbrja.simulation;

import com.weisbrja.event.Event;

public class SimulatorStartedEvent implements Event {

	private final String species;
	private final double mutationRate;
	private final double structuralMutationRate;

	public SimulatorStartedEvent(String species, double mutationRate, double structuralMutationRate) {
		this.species = species;
		this.mutationRate = mutationRate;
		this.structuralMutationRate = structuralMutationRate;
	}

	public String getSpecies() {
		return species;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public double getStructuralMutationRate() {
		return structuralMutationRate;
	}
}
