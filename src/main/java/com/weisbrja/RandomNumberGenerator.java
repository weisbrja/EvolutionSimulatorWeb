package com.weisbrja;

import com.weisbrja.simulation.Boundary2d;

import java.util.Random;

public class RandomNumberGenerator extends Random {

	public RandomNumberGenerator(long randomSeed) {
		super(randomSeed);
	}

	public double getRandomRange(double min, double max) {
		return min + (max - min) * nextDouble();
	}

	public double getRandomRange(Boundary2d boundaries) {
		return getRandomRange(boundaries.getMin(), boundaries.getMax());
	}
}
