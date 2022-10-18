package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.MessageCopy;
import com.project.simulator.entity.Node;

public class EpidemicPQProtocol extends MessageTransmissionProtocol {
	private final double p;
	private final double q;
	
	public EpidemicPQProtocol(double p, double q) {
		this.p = p;
		this.q = q;
	}

	protected boolean shouldTransfer(Node fromNode, Node toNode, MessageCopy message) {
		if(message.getDestinationNode() == toNode.getId()) {
			return true;
		}
		else if(message.getSourceNode() == fromNode.getId()) {
			double randomValue = Math.random();
			return randomValue <= q;
		} else {
			double randomValue = Math.random();
			return randomValue <= p;
		}
	}

}
