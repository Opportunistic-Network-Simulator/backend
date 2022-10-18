package com.project.simulator.entity;

import java.util.*;

import com.project.interfaces.commandLine.report.CommandLineReporter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
	
	private long id;
	private long capacity;
	private List<Long> deliveredMessageIds;
	private Queue<MessageCopy> buffer;
	private Map<String, String> storedProperties = new HashMap<>();
	CommandLineReporter reporter;
	
	public Node(long id, long capacity, CommandLineReporter reporter) {
		this.id = id;
		this.capacity = (capacity > 0) ? capacity: Long.MAX_VALUE;
		this.buffer = new LinkedList<>();
		this.deliveredMessageIds = new ArrayList<>();
		this.reporter = reporter;
	}

	public Node(long id, long capacity) {
		this.id = id;
		this.capacity = (capacity > 0) ? capacity: Long.MAX_VALUE;
		this.buffer = new LinkedList<>();
		this.deliveredMessageIds = new ArrayList<>();
	}

	private void adjustBuffer(){
		while (buffer.size() > this.capacity) this.buffer.remove();
	}
	
	public void generateMessage(MessageCopy message) {
		this.buffer.add(message);
		if (buffer.size() > this.capacity) adjustBuffer();
	}
	
	public void receiveMessage(MessageCopy message, double instant) {
		message.notifyNewNode(this.id, instant);
		if(message.getDestinationNode() == this.id) {
			this.deliveredMessageIds.add(message.getId());
		}
		else {
			MessageCopy copied_message = message.createCopy();
			this.buffer.add(copied_message);
			adjustBuffer();
		}
	}

	public boolean canReceive(MessageCopy message, int counter) {
		if (message.getDestinationNode() == this.id) return !this.deliveredMessageIds.contains(message.getId());
		return !(this.buffer.stream().anyMatch(msg -> msg.getId() == message.getId()));
	}
	
	public void removeMessage(long removedMessageId) {
		this.buffer.removeIf(message -> message.getId() == removedMessageId);
	}
	
	public void sendMessages(Node receiverNode, double instant) {
		for(MessageCopy message : this.buffer) {
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
