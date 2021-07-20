package com.project.simulator.entity;

import java.util.HashMap;
import java.util.Map;

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
	private boolean delivered;
	private double delay;
	private Map<String, String> storedProperties = new HashMap<String, String>();
	
	public Message(long messageIdCounter, long size, long sourceNode, long destinationNode, double genarationInstant) {
		this.id = messageIdCounter;
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
	
	public void storeValue(String key, String value) {
		this.storedProperties.put(key, value);
	}
	
	public boolean hasStoredElement(String key) {
		return this.storedProperties.containsKey(key);
	}
	
	public String getStoredValue(String key) {
		return this.storedProperties.get(key);
	}
	
}
