package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;

public class EpidemicProtocol extends MessageTransmissionProtocol {

	@Override
	public void handleMeet(MeetEvent meet, NodeGroup nodes) {		
		Node node1 = nodes.getNode(meet.getNode1Id());
		Node node2 = nodes.getNode(meet.getNode2Id());
		
		System.out.println("Meet between nodes " + node1.getId() + " and " + node2.getId() + " at " + meet.instant);

		for(Message message : node1.getMessages()) {
			node2.receiveMessage(message, meet.instant);
		}
		
		for(Message message : node2.getMessages()) {
			node1.receiveMessage(message, meet.instant);
		}
	}
	
}
