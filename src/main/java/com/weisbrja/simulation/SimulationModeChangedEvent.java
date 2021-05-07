package com.weisbrja.simulation;

import com.weisbrja.event.Event;

public class SimulationModeChangedEvent implements Event {

	private final boolean simulateGraphically;

	public SimulationModeChangedEvent(boolean simulateGraphically) {
		this.simulateGraphically = simulateGraphically;
	}

	public boolean getSimulateGraphically() {
		return simulateGraphically;
	}
}
