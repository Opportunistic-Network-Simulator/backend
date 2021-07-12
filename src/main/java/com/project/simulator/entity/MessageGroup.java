package com.project.simulator.entity;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MessageGroup implements Iterable<Message> {
	private Map<Long, Message> messages;
	
	public MessageGroup() {
		this.messages = new TreeMap<Long, Message>();
	}
	
	public Message getMessage(long messageId) {
		return messages.get(messageId);
	}
	
	public Message generateMessage(long sourceNodeId, long destinationNodeId, double instant) {
		Message newMessage = new Message(0l, sourceNodeId, destinationNodeId, instant);
		messages.put(newMessage.getId(), newMessage);
		return newMessage;
	}
	
	public int getSize() {
		return this.messages.size();
	}

	@Override
	public Iterator<Message> iterator() {
		return this.messages.values().iterator();
	}
	
	public boolean checkEndOfArrivals() {
//		Set<Long> keys = this.messages.keySet();
//		for(Long key : keys) {
//			Message message = this.getMessage(key);
		for(Message message : this) {
			if(!message.isDelivered())
				return false;
		}
		return true;
	}
}
