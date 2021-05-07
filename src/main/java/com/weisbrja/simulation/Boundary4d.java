package com.weisbrja.simulation;

public class Boundary4d {

	private final double xMin;
	private final double xMax;
	private final double yMin;
	private final double yMax;

	public Boundary4d(double xMin, double xMax, double yMin, double yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	public double getXMin() {
		return xMin;
	}

	public double getXMax() {
		return xMax;
	}

	public double getYMin() {
		return yMin;
	}

	public double getYMax() {
		return yMax;
	}

	public Boundary2d getX() {
		return new Boundary2d(xMin, xMax);
	}

	public Boundary2d getY() {
		return new Boundary2d(yMin, yMax);
	}
}
