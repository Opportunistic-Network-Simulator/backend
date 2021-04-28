package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;

public abstract class MessageTransmissionProtocol {
	public abstract void handleMeet(MeetEvent meet, NodeGroup nodes);
}
