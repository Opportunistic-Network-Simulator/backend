package com.project.simulator.entity;

import com.project.interfaces.commandLine.report.CommandLineReporter;

import java.util.*;

public class MessageGroup implements Iterable<Message> {
	private final Map<Long, Message> messages;
	private int messageIdCounter;
	private CommandLineReporter reporter;
	
	public MessageGroup(CommandLineReporter reporter) {
		this.messages = new TreeMap<>();
		this.reporter = reporter;
		reporter.writeMessageReportHeader();
	}

	public MessageGroup() {
		this.messages = new TreeMap<>();
	}
	
	public Message getMessage(long messageId) {
		return messages.get(messageId);
	}
	
	public Message generateMessage(long sourceNodeId, long destinationNodeId, double instant, long hopLimit) {
		Message newMessage = new Message(messageIdCounter++, 0L, sourceNodeId, destinationNodeId, instant, hopLimit, this.reporter);
		messages.put(newMessage.getId(), newMessage);
		if (this.reporter != null) {
			reporter.reportMessageCreated(newMessage);
		}
		return newMessage;
	}
	
	public int getSize() {
		return this.messages.size();
	}

	public List<Message> getMessageList() {
		return new ArrayList<>(this.messages.values());
	}

	@Override
	public Iterator<Message> iterator() {
		return this.messages.values().iterator();
	}
	
	public boolean checkEndOfArrivals() {
		
		if(this.getSize() == 0) {
			return false;
		}
		
		for(Message message : this) {
			if(!message.isDelivered())
				return false;
		}
		return true;
	}
}
