package com.project.simulator.entity.event;

public class MessageGenerationEvent extends Event {
	private final long sourceNodeId;
	private final long destinationNodeId;
	private final long hopLimit;
	
	public MessageGenerationEvent(double instant, long sourceNodeId, long destinationNodeId, long hopLimit) {
		super(instant);
		this.sourceNodeId = sourceNodeId;
		this.destinationNodeId = destinationNodeId;
		this.hopLimit = hopLimit;
	}
	
	public long getOriginNodeId() {
		return sourceNodeId;
	}
	
	public long getDestinationNodeId() {
		return destinationNodeId;
	}

	public long getHopLimit() { return hopLimit; }
}