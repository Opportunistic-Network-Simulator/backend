package com.project.test.protocols;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;
import com.project.simulator.simulation.protocols.MessageTransmissionProtocol;
import com.project.simulator.simulation.protocols.SingleCopyEpidemicProtocol;

public class SingleCopyEpidemicProtocolTest {
	
	@Test
	public void testDelivery() {
		NodeGroup nodes = new NodeGroup(2);
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
		
		MessageTransmissionProtocol protocol = new SingleCopyEpidemicProtocol();
		
		protocol.handleMeet(new MeetEvent(7, node1.getId(), node2.getId()), nodes);
		
		Assertions.assertFalse(message1.isDelivered());
		Assertions.assertTrue(message2.isDelivered());
		Assertions.assertTrue(message3.isDelivered());
		Assertions.assertTrue(message4.isDelivered());

		Assertions.assertEquals(7, message2.getArrivalInstant(), 0);
		Assertions.assertEquals(7, message3.getArrivalInstant(), 0);
		Assertions.assertEquals(7, message4.getArrivalInstant(), 0);


		Assertions.assertEquals(4, message2.getDelay(), 0);
		Assertions.assertEquals(6, message3.getDelay(), 0);
		Assertions.assertEquals(2, message4.getDelay(), 0);
	}

	@Test
	public void testPassing() {
		NodeGroup nodes = new NodeGroup(2);
		Node node1 = nodes.getNode(0);
		Node node2 = nodes.getNode(1);
		Message message1 = new Message(0, 0, 0, 3, 0);
		node1.addMessage(message1);

		MessageTransmissionProtocol protocol = new SingleCopyEpidemicProtocol();

		protocol.handleMeet(new MeetEvent(7, node1.getId(), node2.getId()), nodes);

		Assertions.assertTrue(node2.getMessages().contains(message1), "Message transmitted to new node");
		Assertions.assertFalse(node1.getMessages().contains(message1), "Message removed from old node");
	}
}
