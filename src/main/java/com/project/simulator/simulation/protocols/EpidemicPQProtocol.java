package com.project.simulator.simulation.protocols;

import java.util.ArrayList;
import java.util.List;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;

public class EpidemicPQProtocol extends MessageTransmissionProtocol {
	private double p;
	private double q;
	
	public EpidemicPQProtocol(double p, double q) {
		this.p = p;
		this.q = q;
	}
	
	@Override
	public void handleMeet(MeetEvent meet, NodeGroup nodes) {		
		Node node1 = nodes.getNode(meet.getNode1Id());
		Node node2 = nodes.getNode(meet.getNode2Id());
		
		List<Message> oneToTwo = transferedMessages(node1, node2);
		
		List<Message> twoToOne = transferedMessages(node2, node1);

		transfer(oneToTwo, node1, node2, meet.instant);
		transfer(twoToOne, node2, node1, meet.instant);
	}
	
	private List<Message> transferedMessages(Node fromNode, Node toNode) {
		List<Message> transferedMessages = new ArrayList<Message>();
		for(Message message : fromNode.getMessages()) {
			if(!shouldTransfer(fromNode, toNode,  message)) continue;
			transferedMessages.add(message);
		}
		return transferedMessages;
	}

	private boolean shouldTransfer(Node fromNode, Node toNode, Message message) {
		if(message.getDestinationNode() == toNode.getId()) {
			return true;
		}
		else if(message.getSourceNode() == fromNode.getId()) {
			double randomValue = Math.random();
			if (randomValue <= q) return true;
			else return false;
		} else {
			double randomValue = Math.random();
			if (randomValue <= p) return true;
			else return false;
		}
	}
	
	private void transfer(List<Message> messages, Node fromNode, Node toNode, double instant) {
		for(Message message : messages) {
			toNode.receiveMessage(message, instant);
		}
	}

}
