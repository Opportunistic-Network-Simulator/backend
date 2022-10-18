package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.MessageCopy;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;
import com.project.simulator.exception.ValueInputException;

import java.util.Map;
import java.util.Objects;

public class PRoPHETProtocol extends MessageTransmissionProtocol {
	private final double pInit;
	private final double gamma;
	private final double beta;
	private boolean isFirstMeet;
	private Map<Long, Node> nodes;

	public PRoPHETProtocol(double pInit, double gamma, double beta) {
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

		isFirstMeet = true;
	}

	@Override
	public void handleMeet(MeetEvent meet, NodeGroup nodes) {
		if (isFirstMeet) {
			setNodes(nodes.getNodes());
			isFirstMeet = false;
		}

		super.handleMeet(meet, nodes);
	}

	public void setNodes(Map<Long, Node> nodes) {
		this.nodes = nodes;

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
		updateProbabilities(node1, node2);
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

	private void updateProbabilities(Node node1, Node node2) {
		long id1 = node1.getId();
		long id2 = node2.getId();

		updateProbability(node1, String.valueOf(id2));
		updateProbability(node2, String.valueOf(id1));

		transitivity(node1, node2);
		transitivity(node2, node1);
	}

	private void updateProbability(Node node, String idOtherNode) {
		double oldProb = Double.parseDouble(node.getStoredValue(idOtherNode));
		double newProb = oldProb + (1 - oldProb) * pInit;
		node.storeValue(idOtherNode, String.valueOf(newProb));
	}

	private void transitivity(Node fromNode, Node toNode) {
		String toNodeId = String.valueOf(toNode.getId());
		double toNodeProb = Double.parseDouble(fromNode.getStoredValue(toNodeId));

		for(String nodeIdStr : fromNode.getStoredProperties().keySet()) {
			if(!nodeIdStr.equals("lastMeetTime") && !nodeIdStr.equals(toNodeId)) {
				double oldProb = Double.parseDouble(fromNode.getStoredValue(nodeIdStr));
				double transProb = Double.parseDouble(toNode.getStoredValue(nodeIdStr));
				double newProb = oldProb + (1 - oldProb) * toNodeProb * transProb * beta;
				fromNode.storeValue(nodeIdStr, String.valueOf(newProb));
			}
		}
	}

	@Override
	protected boolean shouldTransfer(Node fromNode, Node toNode, MessageCopy message) {
		long destNodeId = message.getDestinationNode();
		if (destNodeId == toNode.getId()) {
			return true;
		}
		if (destNodeId == fromNode.getId()) {
			return false;
		}

		double probFrom = Double.parseDouble(fromNode.getStoredValue(String.valueOf(destNodeId)));
		double probTo   = Double.parseDouble(toNode.getStoredValue(String.valueOf(destNodeId)));

		return probTo > probFrom;
	}
}
