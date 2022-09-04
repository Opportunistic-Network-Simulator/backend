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
public class Node {
	
	private long id;
	private long capacity;
	private List<Message> messages;
	private Map<String, String> storedProperties = new HashMap<>();
	CommandLineReporter reporter;
	
	public Node(long id, long capacity, CommandLineReporter reporter) {
		this.id = id;
		this.capacity = (capacity > 0) ? capacity: Long.MAX_VALUE;
		this.messages = new ArrayList<>();
		this.reporter = reporter;
	}

	public Node(long id, long capacity) {
		this.id = id;
		this.capacity = (capacity > 0) ? capacity: Long.MAX_VALUE;
		this.messages = new ArrayList<>();
	}
	
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	public void receiveMessage(Message message, double instant) {
		if(this.messages.contains(message)) {
			return; //não recebo msg que já tenho
			
		}
		if(this.messages.size() >= this.capacity) {
			return; // rejects message if capacity is full
		}
		message.notifyNewNode(this.id, instant);
		message.incrementHop();
		if(message.getDestinationNode() == this.id) {
			message.setArrivalInstant(instant);
			message.setDelay(instant - message.getGenerationInstant());
			message.setDelivered(true);
		}
		this.messages.add(message);
	}
	
	public void removeMessage(long removedMessageId) {
		Integer index = null;
		
		for(Message message : this.messages) {
			if(message.getId() == removedMessageId && message.getDestinationNode() != this.id) {
				index = this.messages.indexOf(message);
			}
		}

		if(index != null)
			this.messages.remove(index.intValue());
	}
	
	public void sendMessages(Node receiverNode, double instant) {
		for(Message message : this.messages) {
			if(message.getDestinationNode() != this.id) { //não envio se eu sou o destino (já chegou onde deveria)
				receiverNode.receiveMessage(message, instant);
			}
		}
	}
	
	public void storeValue(String key, String value) {
		this.storedProperties.put(key, value);
	}

	public Map<String, String> getStoredProperties() {
		return storedProperties;
	}

	public boolean hasStoredElement(String key) {
		return this.storedProperties.containsKey(key);
	}
	
	public String getStoredValue(String key) {
		return this.storedProperties.get(key);
	}

}
