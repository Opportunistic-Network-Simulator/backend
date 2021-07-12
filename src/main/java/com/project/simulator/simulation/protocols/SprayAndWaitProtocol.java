package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;

public class SprayAndWaitProtocol extends MessageTransmissionProtocol {
	private int lValue;
	
	public SprayAndWaitProtocol(int lValue) {
		this.lValue = lValue;
	}

	@Override
	public void handleMeet(MeetEvent meet, NodeGroup nodes) {
		Node node1 = nodes.getNode(meet.getNode1Id());
		Node node2 = nodes.getNode(meet.getNode2Id());
		
		for(Message message : node1.getMessages()) {
			checkMessageTransfer(node1, node2, message, meet);
		}
		
	}

	private void checkMessageTransfer(Node fromNode, Node toNode, Message message, MeetEvent meet) {
		initialStoreMessage(fromNode, message);
		
		if(alreadyDelivered(toNode, message))
			return;
		
		int messageLValueInNodeFrom = Integer.valueOf(message.getStoredValue(String.valueOf(fromNode.getId())));

		if(messageLValueInNodeFrom == 1) {
			wait(fromNode, toNode, message, messageLValueInNodeFrom, meet);
		} else {
			spray(fromNode, toNode, message, messageLValueInNodeFrom, meet);
		}
	}

	private void spray(Node fromNode, Node toNode, Message message, int messageLValueInNodeFrom, MeetEvent meet) {
		toNode.receiveMessage(message, meet.instant);
		message.storeValue(String.valueOf(fromNode.getId()), String.valueOf(messageLValueInNodeFrom - 1));
		message.storeValue(String.valueOf(toNode.getId()), String.valueOf(1));
	}

	private void wait(Node fromNode, Node toNode, Message message, int messageLValueInNodeFrom, MeetEvent meet) {
		if (message.getDestinationNode() == toNode.getId()) {
			toNode.receiveMessage(message, meet.instant);
		}
		
	}

	private boolean alreadyDelivered(Node toNode, Message message) {
		return message.hasStoredElement(String.valueOf(toNode.toString()));
	}

	private void initialStoreMessage(Node fromNode, Message message) {
		if(!message.hasStoredElement(String.valueOf(fromNode.getId()))) {
			message.storeValue(String.valueOf(fromNode.getId()), String.valueOf(this.lValue));
		}
	}

}
