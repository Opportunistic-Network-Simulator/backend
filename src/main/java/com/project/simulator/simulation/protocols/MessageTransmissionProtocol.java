package com.project.simulator.simulation.protocols;

import java.util.ArrayList;
import java.util.List;

import com.project.simulator.entity.MessageCopy;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;

public abstract class MessageTransmissionProtocol {
	public void handleMeet(MeetEvent meet, NodeGroup nodes) {
		Node node1 = nodes.getNode(meet.getNode1Id());
		Node node2 = nodes.getNode(meet.getNode2Id());
		preTransfer(node1, node2, meet.instant);
		List<MessageCopy> oneToTwo = transferredMessages(node1, node2);
		List<MessageCopy> twoToOne = transferredMessages(node2, node1);

		transferMessages(oneToTwo, node1, node2, meet.instant);
		transferMessages(twoToOne, node2, node1, meet.instant);
	}

	private List<MessageCopy> transferredMessages(Node fromNode, Node toNode) {
		List<MessageCopy> transferredMessages = new ArrayList<>();
		for (MessageCopy message : fromNode.getBuffer()) {
			if (shouldTransfer(fromNode, toNode, message) && message.canHop(toNode.getId()) && toNode.canReceive(message, transferredMessages.size())) {
				transferredMessages.add(message);
				message.incrementTransferCount();
			}
		}
		return transferredMessages;
	}
	
	private void transferMessages(List<MessageCopy> messages, Node fromNode, Node toNode, double instant) {
		for(MessageCopy message : messages) {
			message.getReporter().reportMessageTransmitted(message.getId(), instant, fromNode.getId(), toNode.getId());
			toNode.receiveMessage(message, instant);
			postTransfer(message, fromNode, toNode);
		}
	}

	protected void preTransfer(Node node1, Node node2, double instant) {}

	protected abstract boolean shouldTransfer(Node fromNode, Node toNode, MessageCopy message);

	protected void postTransfer(MessageCopy message, Node fromNode, Node toNode) {}
}
