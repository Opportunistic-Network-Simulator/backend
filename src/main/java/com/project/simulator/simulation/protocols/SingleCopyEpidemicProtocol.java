package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;

public class SingleCopyEpidemicProtocol extends MessageTransmissionProtocol {

	@Override
	protected boolean shouldTransfer(Node fromNode, Node toNode, Message message) {
		return true;
	}

	@Override
	protected void postTransfer(Message message, Node fromNode, Node toNode) {
		fromNode.removeMessage(message.getId());
	}
}
