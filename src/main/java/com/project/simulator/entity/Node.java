package com.project.simulator.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Node {
	
	private long id;
	private List<Message> messages;
	
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	public void receiveMessage(Message message, double instant) {
		if(this.messages.contains(message)) return; //não recebo msg que já tenho
		this.messages.add(message);
		if(message.getDestinationNode() == this.id) {
			message.setArrivalInstant(instant);
			message.setDelay(instant - message.getGenarationInstant());
		}
	}
	
	public void sendMessages(Node receiverNode, double instant) {
		for(Message message : this.messages) {
			if(message.getDestinationNode() != this.id) { //não envio se eu sou o destino (já chegou onde deveria)
				receiverNode.receiveMessage(message, instant);
			}
		}
	}

}
