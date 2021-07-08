package com.project.simulator.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	public static long idCounter = 0;
	
	private long id;
	private long size;
	private long sourceNode;
	private long destinationNode;
	private double genarationInstant;
	private double arrivalInstant;
	private boolean delivered;
	private double delay;
	
	public Message(long size, long sourceNode, long destinationNode, double genarationInstant) {
		this.id = idCounter++;
		this.size = size;
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.genarationInstant = genarationInstant;
		this.delivered = false;
	}
	
	public void notifyNewNode(long newNodeId, double instant) {
		if(this.genarationInstant != 0) return;
		// System.out.println("Message " + this.id + " delivered to node " + newNodeId);
		if(this.destinationNode == newNodeId) {
			this.arrivalInstant = instant;
			// System.out.println("Message " + this.id + " arrived at " + instant);
		}
	}
	
}
