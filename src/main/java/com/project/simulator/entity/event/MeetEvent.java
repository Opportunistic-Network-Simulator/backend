package com.project.simulator.entity.event;

public class MeetEvent extends Event {
	private final long node1Id;
	private final long node2Id;
	
	public MeetEvent(double instant, long node1Id, long node2Id) {
		super(instant);
		this.node1Id = node1Id;
		this.node2Id = node2Id;
	}
	
	public long getNode1Id() {
		return node1Id;
	}

	public long getNode2Id() {
		return node2Id;
	}
}
