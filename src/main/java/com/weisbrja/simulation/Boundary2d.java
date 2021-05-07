package com.weisbrja.simulation;

public class Boundary2d {

	private final double min;
	private final double max;

	public Boundary2d(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}
}
