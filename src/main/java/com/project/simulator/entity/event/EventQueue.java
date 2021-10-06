package com.project.simulator.entity.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.project.simulator.entity.MeetingTrace;

import lombok.Getter;

@Getter
public class EventQueue {
	private List<Event> eventsInTheFuture;
	private List<Event> eventsInThePast;
	
	public EventQueue(MeetingTrace meetingTrace, List<MessageGenerationEvent> messageGenerationEventQueue) {
		this.eventsInTheFuture = new ArrayList<Event>();
		this.eventsInThePast = new ArrayList<Event>();
		this.eventsInTheFuture.addAll(meetingTrace.generateMeetEventQueue());
		this.eventsInTheFuture.addAll(messageGenerationEventQueue);
		this.sortEvents();
	}
	
	public Event nextEvent() {
		if(eventsInTheFuture.size() == 0) return new SimulationOverEvent();
		Collections.sort(this.eventsInTheFuture);
		Event nextEvent = eventsInTheFuture.get(0);
		eventsInTheFuture.remove(0);
		eventsInThePast.add(nextEvent);
		return nextEvent;
	}
	
	public void simulationGenerateEvent(Event event) {
		this.eventsInTheFuture.add(event);
		Collections.sort(this.eventsInTheFuture);
	}
	
	public double getProgress() {
		System.out.println("progress: " + (double) this.eventsInThePast.size() / (double) (this.eventsInTheFuture.size() + this.eventsInThePast.size()));
		return (double) this.eventsInThePast.size() / (double) (this.eventsInTheFuture.size() + this.eventsInThePast.size());
	}

	public static EventQueue makeEventQueue(MeetingTrace meetingTrace, List<MessageGenerationEvent> messageGenerationEventQueue) {
		return new EventQueue(meetingTrace, messageGenerationEventQueue);
	}

	private EventQueue sortEvents() {
		Collections.sort(this.eventsInTheFuture);
		return null;
	}
}
