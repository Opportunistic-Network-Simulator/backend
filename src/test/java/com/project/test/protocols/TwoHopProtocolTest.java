package com.project.test.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;
import com.project.simulator.simulation.protocols.MessageTransmissionProtocol;
import com.project.simulator.simulation.protocols.SingleCopyEpidemicProtocol;
import com.project.simulator.simulation.protocols.TwoHopProtocol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TwoHopProtocolTest {

	// TODO: implement
	@Test
	public void testDelivery() {
		NodeGroup nodes = new NodeGroup();
		Node node1 = nodes.getNode(0);
		Node node2 = nodes.getNode(1);
		Message message1 = new Message(0, 0, 0, 3, 0);
		Message message2 = new Message(2, 0, 5, 1, 3);
		Message message3 = new Message(3, 0, 2, 1, 1);
		Message message4 = new Message(1, 0, 1, 0, 5);
		node1.addMessage(message1);
		node1.addMessage(message2);
		node1.addMessage(message3);
		node2.addMessage(message4);
		
		MessageTransmissionProtocol protocol = new TwoHopProtocol();
	}
}
