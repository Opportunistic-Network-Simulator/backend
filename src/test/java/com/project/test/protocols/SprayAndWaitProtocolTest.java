package com.project.test.protocols;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MeetEvent;
import com.project.simulator.simulation.protocols.MessageTransmissionProtocol;
import com.project.simulator.simulation.protocols.SingleCopyEpidemicProtocol;
import com.project.simulator.simulation.protocols.SprayAndWaitProtocol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SprayAndWaitProtocolTest {
	
	@Test
	public void testSpray() {
		NodeGroup nodes = new NodeGroup(5);
		Node node1 = nodes.getNode(0);
		Node node2 = nodes.getNode(1);
		Node node3 = nodes.getNode(2);
		Node node4 = nodes.getNode(3);
		Node node5 = nodes.getNode(4);
		Message message1 = new Message(0, 0, 0, 7, 0);
		node1.addMessage(message1);
		
		MessageTransmissionProtocol protocol = new SprayAndWaitProtocol(3);

		protocol.handleMeet(new MeetEvent(1, node1.getId(), node2.getId()), nodes);
		protocol.handleMeet(new MeetEvent(2, node1.getId(), node3.getId()), nodes);
		protocol.handleMeet(new MeetEvent(3, node1.getId(), node4.getId()), nodes);
		protocol.handleMeet(new MeetEvent(4, node1.getId(), node5.getId()), nodes);

		Assertions.assertTrue(node1.getMessages().size() == 0);
		Assertions.assertTrue(node2.getMessages().size() == 1);
		Assertions.assertTrue(node3.getMessages().size() == 1);
		Assertions.assertTrue(node4.getMessages().size() == 1);
		Assertions.assertTrue(node5.getMessages().size() == 0);

		Assertions.assertEquals("1", message1.getStoredValue("1"));
		Assertions.assertEquals("1", message1.getStoredValue("2"));
		Assertions.assertEquals("1", message1.getStoredValue("3"));
		Assertions.assertEquals("0", message1.getStoredValue("0"));
		Assertions.assertFalse(message1.hasStoredElement("4"));
	}

	@Test
	public void testWait() {
		NodeGroup nodes = new NodeGroup(5);
		Node node2 = nodes.getNode(1);
		Node node3 = nodes.getNode(2);
		Message message1 = new Message(0, 0, 0, 4, 0);
		node2.addMessage(message1);
		message1.storeValue("1", "1");

		MessageTransmissionProtocol protocol = new SprayAndWaitProtocol(3);

		protocol.handleMeet(new MeetEvent(1, 1, 2), nodes);
		protocol.handleMeet(new MeetEvent(2, 1, 4), nodes);

		Assertions.assertTrue(node3.getMessages().size() == 0);
		Assertions.assertTrue(message1.isDelivered());
		Assertions.assertEquals(2, message1.getDelay());
	}

	@Test
	public void testDirectContact() {
		NodeGroup nodes = new NodeGroup(2);
		Node node1 = nodes.getNode(0);
		Node node2 = nodes.getNode(1);
		Message message1 = new Message(0, 0, 0, 1, 3);
		node1.addMessage(message1);

		MessageTransmissionProtocol protocol = new SprayAndWaitProtocol(3);

		protocol.handleMeet(new MeetEvent(5, 0, 1), nodes);

		Assertions.assertTrue(message1.isDelivered());
		Assertions.assertEquals(2, message1.getDelay());
		Assertions.assertEquals(5, message1.getArrivalInstant());
	}


}
