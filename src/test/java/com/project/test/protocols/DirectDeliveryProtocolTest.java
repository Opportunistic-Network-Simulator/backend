package com.project.test.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;
import com.project.simulator.simulation.protocols.DirectDeliveryProtocol;
import com.project.simulator.simulation.protocols.MessageTransmissionProtocol;
import com.project.simulator.simulation.protocols.SingleCopyEpidemicProtocol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DirectDeliveryProtocolTest {
	
	@Test
	public void testDelivery() {
		NodeGroup nodes = new NodeGroup(5);
		Node node1 = nodes.getNode(0);
		Node node2 = nodes.getNode(1);
		Message message1 = new Message(0, 0, 0, 1, 0);
		Message message2 = new Message(1, 0, 1, 0, 1);
		node1.addMessage(message1);
		node2.addMessage(message2);

		MessageTransmissionProtocol protocol = new DirectDeliveryProtocol();

		protocol.handleMeet(new MeetEvent(5, 0, 1), nodes);

		Assertions.assertTrue(message1.isDelivered());
		Assertions.assertTrue(message2.isDelivered());

		Assertions.assertEquals(5, message1.getDelay());
		Assertions.assertEquals(4, message2.getDelay());
	}

	@Test
	public void testNoDelivery() {
		NodeGroup nodes = new NodeGroup(5);
		Node node1 = nodes.getNode(0);
		Node node2 = nodes.getNode(1);
		Message message1 = new Message(0, 0, 0, 3, 0);
		Message message2 = new Message(1, 0, 1, 3, 1);
		node1.addMessage(message1);
		node2.addMessage(message2);

		MessageTransmissionProtocol protocol = new DirectDeliveryProtocol();

		protocol.handleMeet(new MeetEvent(5, 0, 1), nodes);

		Assertions.assertFalse(message1.isDelivered());
		Assertions.assertFalse(message2.isDelivered());

		Assertions.assertFalse(node2.getMessages().contains(message1));
		Assertions.assertFalse(node1.getMessages().contains(message2));
	}
}
