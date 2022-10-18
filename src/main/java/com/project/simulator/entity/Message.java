package com.project.simulator.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private List<MessageCopy> children;
	private double arrivalInstant;
	private boolean delivered;
	private long hopLimit;
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
		this.delivered = false;
		this.reporter = reporter;
		this.children = new ArrayList<>();
	}

	public Message(long messageIdCounter, long size, long sourceNode, long destinationNode, double generationInstant) {
		this.id = messageIdCounter;
		this.size = size;
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.generationInstant = generationInstant;
		this.delivered = false;
		this.children = new ArrayList<>();
	}

	public Message(Message message) {
		this.id = message.getId();
		this.size = message.getSize();
		this.sourceNode = message.getSourceNode();
		this.destinationNode = message.getDestinationNode();
		this.generationInstant = message.getGenerationInstant();
		this.hopLimit = message.getHopLimit();
		this.delivered = false;
		this.reporter = message.getReporter();
		this.storedProperties = message.getStoredProperties();
		this.children = new ArrayList<>();
	}
	
	public void notifyNewNode(long newNodeId, double instant) {
		if(this.destinationNode == newNodeId) {
			this.arrivalInstant = instant;
			this.setDelay(instant - this.getGenerationInstant());
			this.setDelivered(true);
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

//	public void incrementHop() { hopCount++; }

//	public boolean canHop(long toNode) {
//		if (toNode != this.destinationNode)
//			return hopCount < hopLimit;
//		else
//			return true;
//	}

	public void addChild(MessageCopy child){
		this.children.add(child);
	}

	public void removeChild(MessageCopy child){
		this.children.removeIf(copy -> copy == child);
	}

	public MessageCopy createCopy(){
		MessageCopy copy = new MessageCopy(this);
		this.addChild(copy);
		return copy;
	}

	public MessageCopy createCopy(MessageCopy other){
		MessageCopy copy = new MessageCopy(this);
		copy.setHopCount(other.getHopCount() + 1);
		this.addChild(copy);
		return copy;
	}

	//TODO: add number of hops and hop limit to report
	
}
