package com.project.simulator.entity.event;

public class MessageGenerationEvent extends Event {
	private long sourceNodeId;
	private long destinationNodeId;
	
	public MessageGenerationEvent(double instant, long sourceNodeId, long destinationNodeId) {
		super(instant);
		this.sourceNodeId = sourceNodeId;
		this.destinationNodeId = destinationNodeId;
	}
	
	public long getOriginNodeId() {
		return sourceNodeId;
	}
	
	public long getDestinationNodeId() {
		return destinationNodeId;
	}	
}