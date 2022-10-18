package com.project.simulator.entity.event;

public class SimulationOverEvent extends Event {
	public SimulationOverEvent() {
		super(0);
	}
	
	public SimulationOverEvent(double instant) {
		super(instant);
	}
}
