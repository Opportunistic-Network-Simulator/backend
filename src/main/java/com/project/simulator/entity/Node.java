package com.project.simulator.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
	
	private long id;
	private List<Message> messages;
	
	public Node(long id) {
		this.id = id;
		this.messages = new ArrayList<Message>();
	}
	
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	public void receiveMessage(Message message, double instant) {
		if(this.messages.contains(message)) return; //não recebo msg que já tenho
		this.messages.add(message);
		message.notifyNewNode(this.id, instant);
		if(message.getDestinationNode() == this.id) {
			message.setArrivalInstant(instant);
			message.setDelay(instant - message.getGenarationInstant());
		}
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

}
