package com.project.simulator.simulation.protocols;

import java.util.ArrayList;
import java.util.List;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;

public abstract class MessageTransmissionProtocol {
	public void handleMeet(MeetEvent meet, NodeGroup nodes) {
		Node node1 = nodes.getNode(meet.getNode1Id());
		Node node2 = nodes.getNode(meet.getNode2Id());
		List<Message> oneToTwo = transferredMessages(node1, node2);
		List<Message> twoToOne = transferredMessages(node2, node1);
		
		preTransfer(node1, node2, meet.instant);
		transferMessages(oneToTwo, node1, node2, meet.instant);
		transferMessages(twoToOne, node2, node1, meet.instant);
	}

	private List<Message> transferredMessages(Node fromNode, Node toNode) {
		List<Message> transferredMessages = new ArrayList<>();

		for (Message message : fromNode.getMessages()) {
			if (shouldTransfer(fromNode, toNode, message) && message.canHop()) transferredMessages.add(message);
		}
		return transferredMessages;
	}
	
	private void transferMessages(List<Message> messages, Node fromNode, Node toNode, double instant) {
		for(Message message : messages) {
			message.getReporter().reportMessageTransmitted(message.getId(), instant, fromNode.getId(), toNode.getId());
			toNode.receiveMessage(message, instant);
			postTransfer(message, fromNode, toNode);
		}
	}

	protected void preTransfer(Node node1, Node node2, double instant) {}

	protected abstract boolean shouldTransfer(Node fromNode, Node toNode, Message message);
	
	protected void postTransfer(Message message, Node fromNode, Node toNode) {}
}
