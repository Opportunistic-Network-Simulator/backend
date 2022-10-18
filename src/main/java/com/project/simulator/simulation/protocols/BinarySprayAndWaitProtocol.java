package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.MessageCopy;
import com.project.simulator.entity.Node;

public class BinarySprayAndWaitProtocol extends MessageTransmissionProtocol {
	private final int lValue;

	public BinarySprayAndWaitProtocol(int lValue) {
		this.lValue = lValue;
	}

	@Override
	protected boolean shouldTransfer(Node fromNode, Node toNode, MessageCopy message) {
		initialStoreMessage(fromNode, message);

		if(alreadyDelivered(toNode, message))
			return false;

		int messageLValueInNodeFrom = Integer.parseInt(message.getStoredValue(String.valueOf(fromNode.getId())));

		if(messageLValueInNodeFrom == 1) {
			return wait(fromNode, toNode, message, messageLValueInNodeFrom);
		} else {
			return spray(fromNode, toNode, message, messageLValueInNodeFrom);
		}
	}

	private boolean spray(Node fromNode, Node toNode, MessageCopy message, int messageLValueInNodeFrom) {
		return true;
	}

	private boolean wait(Node fromNode, Node toNode, MessageCopy message, int messageLValueInNodeFrom) {
		return message.getDestinationNode() == toNode.getId() || message.getSourceNode() == fromNode.getId();
	}

	private boolean alreadyDelivered(Node toNode, MessageCopy message) {
		return message.hasStoredElement(String.valueOf(toNode.toString()));
	}

	// TODO: trocar isso para ser um metodo chamado na criação da mensagem
	private void initialStoreMessage(Node fromNode, MessageCopy message) {
		if(!message.hasStoredElement(String.valueOf(fromNode.getId()))) {
			message.storeValue(String.valueOf(fromNode.getId()), String.valueOf(this.lValue));
		}
	}
	
	@Override
	protected void postTransfer(MessageCopy message, Node fromNode, Node toNode) {
		handleToNode(message, fromNode, toNode);
		handleFromNode(message, fromNode, toNode);
	}

	private void handleToNode(MessageCopy message, Node fromNode, Node toNode) {
		int fromNodeLValue = Integer.parseInt(message.getStoredValue(String.valueOf(fromNode.getId())));
		message.storeValue(String.valueOf(toNode.getId()), String.valueOf((int) Math.floor(fromNodeLValue / 2)));
	}

	private void handleFromNode(MessageCopy message, Node fromNode, Node toNode) {
		int messageLValueInNodeFrom = Integer.parseInt(message.getStoredValue(String.valueOf(fromNode.getId())));
		messageLValueInNodeFrom = messageLValueInNodeFrom - (int) Math.floor(messageLValueInNodeFrom / 2);
		message.storeValue(String.valueOf(fromNode.getId()), String.valueOf(messageLValueInNodeFrom));
		if(messageLValueInNodeFrom == 0) {
			fromNode.removeMessage(message.getId());
		}
	}


}
