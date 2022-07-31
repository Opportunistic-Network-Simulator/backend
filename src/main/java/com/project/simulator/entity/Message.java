package com.project.simulator.entity;

import java.util.HashMap;
import java.util.Map;

import com.project.interfaces.commandLine.report.CommandLineReporter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	
	private long id;
	private long size;
	private long sourceNode;
	private long destinationNode;
	private double generationInstant;
	private double arrivalInstant;
	private boolean delivered;
	private long hopCount, hopLimit;
	private double delay;
	private Map<String, String> storedProperties = new HashMap<>();
	private CommandLineReporter reporter;
	
	public Message(long messageIdCounter, long size, long sourceNode, long destinationNode, double generationInstant, long hopLimit, CommandLineReporter reporter) {
		this.id = messageIdCounter;
		this.size = size;
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.generationInstant = generationInstant;
		this.hopLimit = hopLimit;
		this.hopCount = 0;
		this.delivered = false;
		this.reporter = reporter;
	}

	public Message(long messageIdCounter, long size, long sourceNode, long destinationNode, double generationInstant) {
		this.id = messageIdCounter;
		this.size = size;
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.generationInstant = generationInstant;
		this.delivered = false;
	}
	
	public void notifyNewNode(long newNodeId, double instant) {
		if(this.destinationNode == newNodeId) {
			this.arrivalInstant = instant;
			if(this.reporter != null)
				this.reporter.reportMessageArrived(this.id, instant);
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

	public void incrementHop() { hopCount++; }

	public boolean canHop() { return hopCount < hopLimit; }

	//TODO: add number of hops and hop limit to report
	
}
