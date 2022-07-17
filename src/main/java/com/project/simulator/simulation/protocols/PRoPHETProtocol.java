package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.exception.ValueInputException;

import java.util.Map;
import java.util.Objects;

public class PRoPHETProtocol extends MessageTransmissionProtocol {
	private final double pInit;
	private final double gamma;
	private final double beta;
	Map<Long, Node> nodes;

	PRoPHETProtocol(double pInit, double gamma, double beta, NodeGroup nodes) {
		if(pInit >= 0 && pInit <= 1) {
			this.pInit = pInit;
		} else {
			throw new ValueInputException("P init value should be in the interval [0, 1].");
		}

		if(gamma >= 0 && gamma < 1) {
			this.gamma = gamma;
		} else {
			throw new ValueInputException("Gamma value should be in the interval [0, 1).");
		}

		if(beta >= 0 && beta <= 1) {
			this.beta = beta;
		} else {
			throw new ValueInputException("Beta value should be in the interval [0, 1].");
		}

		this.nodes = nodes.getNodes();

		for(Long nodeId : this.nodes.keySet()) {
			this.nodes.get(nodeId).storeValue("lastMeetTime", String.valueOf(0));
			for(Long otherNodeId: this.nodes.keySet()) {
				if(!Objects.equals(otherNodeId, nodeId)) {
					this.nodes.get(nodeId).storeValue(String.valueOf(otherNodeId), String.valueOf(pInit));
				}
			}
		}
	}

	@Override
	protected void preTransfer(Node node1, Node node2, double instant) {
		ageNode(node1, instant);
		ageNode(node2, instant);
	}

	private void ageNode(Node node, double instant) {
		double lastTime = Double.parseDouble(node.getStoredValue("lastMeetTime"));
		double elapsedTime = instant - lastTime;

		for(String nodeIdStr : node.getStoredProperties().keySet()) {
			if(!nodeIdStr.equals("lastMeetTime")) {
				double oldProb = Double.parseDouble(node.getStoredValue(nodeIdStr));
				double newProb = oldProb * Math.pow(gamma, elapsedTime);
				node.storeValue(nodeIdStr, String.valueOf(newProb));
			}
		}

		node.storeValue("lastMeetTime", String.valueOf(instant));
	}

	@Override
	protected boolean shouldTransfer(Node fromNode, Node toNode, Message message) {
		return false;
	}
}
