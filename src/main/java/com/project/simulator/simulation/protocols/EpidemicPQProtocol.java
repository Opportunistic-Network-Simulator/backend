package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;

public class EpidemicPQProtocol extends MessageTransmissionProtocol {
	private double p;
	private double q;
	
	public EpidemicPQProtocol(double p, double q) {
		this.p = p;
		this.q = q;
	}

	protected boolean shouldTransfer(Node fromNode, Node toNode, Message message) {
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

}
