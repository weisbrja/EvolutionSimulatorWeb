package com.weisbrja.simulation;

import com.weisbrja.event.Event;

public class GenerationDoneEvent implements Event {

	private final int generationCount;
	private final double bestDistance;
	private final double medianDistance;

	public GenerationDoneEvent(int generation, double bestDistance, double medianDistance) {
		this.generationCount = generation;
		this.bestDistance = bestDistance;
		this.medianDistance = medianDistance;
	}

	public int getGenerationCount() {
		return generationCount;
	}

	public double getBestDistance() {
		return bestDistance;
	}

	public double getMedianDistance() {
		return medianDistance;
	}
}
