package com.project.simulator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

	private long id;
	private long size;
	private long sourceNode;
	private long destinationNode;
	private double genarationInstant;
	private double arrivalInstant;
	private double delay;
	
	public Message(long id, long size, long sourceNode, long destinationNode, long genarationInstant) {
		this.id = id;
		this.size = size;
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.genarationInstant = genarationInstant;
	}
	
}
