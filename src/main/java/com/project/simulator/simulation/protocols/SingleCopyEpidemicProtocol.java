package com.project.simulator.simulation.protocols;

import java.util.ArrayList;
import java.util.List;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;

public class SingleCopyEpidemicProtocol extends MessageTransmissionProtocol {

	@Override
	public void handleMeet(MeetEvent meet, NodeGroup nodes) {		
		Node node1 = nodes.getNode(meet.getNode1Id());
		Node node2 = nodes.getNode(meet.getNode2Id());
		
		System.out.println("Meet between nodes " + node1.getId() + " and " + node2.getId() + " at " + meet.instant);

		List<Message> oneToTwo = transferedMessages(node1, node2);
		
		List<Message> twoToOne = transferedMessages(node2, node1);

		transfer(oneToTwo, node1, node2, meet.instant);
		transfer(twoToOne, node2, node1, meet.instant);
	}

	private List<Message> transferedMessages(Node node1, Node node2) {
		List<Message> transferedMessages = new ArrayList<Message>();
		for(Message message : node1.getMessages()) {
			if(message.getDestinationNode() == node1.getId()) continue;
			transferedMessages.add(message);
		}
		return transferedMessages;
	}
	
	private void transfer(List<Message> messages, Node node1, Node node2, double instant) {
		for(Message message : messages) {
			if(message.getDestinationNode() == node1.getId()) continue;
			node2.receiveMessage(message, instant);
			node1.removeMessage(message.getId());
		}
	}
	
}
