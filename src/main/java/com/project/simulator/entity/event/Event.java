package com.project.simulator.entity.event;


public abstract class Event implements Comparable<Event> {
	
	public final double instant;
	
	public Event(double instant) {
		this.instant = instant;
	}
	
	@Override
	public int compareTo(Event otherEvent) {
		return Double.compare(this.instant, otherEvent.instant); //increasing way
	}
}
