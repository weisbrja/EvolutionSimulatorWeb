package com.weisbrja.simulation;

import com.weisbrja.event.Event;

public class CreatureDrawEvent implements Event {

	private final Creature creature;

	public CreatureDrawEvent(Creature creature) {
		this.creature = creature;
	}

	public Creature getCreature() {
		return creature;
	}
}
