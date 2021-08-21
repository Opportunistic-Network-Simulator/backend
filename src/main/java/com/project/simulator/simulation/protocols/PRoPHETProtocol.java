package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;

public class PRoPHETProtocol extends MessageTransmissionProtocol {

	@Override
	public void handleMeet(MeetEvent meet, NodeGroup nodes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean shouldTransfer(Node fromNode, Node toNode, Message message) {
		// TODO Auto-generated method stub
		return false;
	}

}
