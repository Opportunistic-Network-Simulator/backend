package com.project.simulator.generator;

import java.util.ArrayList;
import java.util.List;

import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.MessageGenerationEvent;

public class SingleMessagesGenerator {
	public static List<MessageGenerationEvent> generateMessages(NodeGroup nodes) { //posteriormente, deve pegar dados da requisição para gerar as msg
		List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<MessageGenerationEvent>();
		
		long sourceNodeId = (long) (Math.random() * nodes.getSize());
		long destinationNodeId = (long) (Math.random() * nodes.getSize());
		
		while(destinationNodeId == sourceNodeId) {
			destinationNodeId = (long) (Math.random() * nodes.getSize());
		}
		
		MessageGenerationEvent messageGenerationEvent = new MessageGenerationEvent(0, sourceNodeId, destinationNodeId);
		messageGenerationQueue.add(messageGenerationEvent);
		return messageGenerationQueue;
	}

}
