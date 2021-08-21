package com.project.simulator.entity.event;


public abstract class Event implements Comparable<Event> {
	
	public final double instant;
	
	public Event(double instant) {
		this.instant = instant;
	}
	
	@Override
	public int compareTo(Event otherEvent) {
		if(this.instant > otherEvent.instant) return 1; //increasing way
		if(this.instant < otherEvent.instant) return -1;
		return 0;
	}
}
