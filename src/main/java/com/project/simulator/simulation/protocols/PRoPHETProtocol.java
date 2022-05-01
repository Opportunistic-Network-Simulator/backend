package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;
import com.project.simulator.exception.ValueInputException;

public class PRoPHETProtocol extends MessageTransmissionProtocol {
	private double pInit;
	private double gamma;
	private double beta;

	PRoPHETProtocol(double pInit, double gamma, double beta) {
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
	}

	@Override
	protected boolean shouldTransfer(Node fromNode, Node toNode, Message message) {
		return false;
	}
}
