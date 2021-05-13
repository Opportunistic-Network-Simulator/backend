package com.project.simulator.generator.messageGenerator;

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
		return generateMessages(sourceNodeId, destinationNodeId, nodes);
	}
	
	public static List<MessageGenerationEvent> generateMessages(long sourceNodeId, long destinationNodeId, NodeGroup nodes) { //posteriormente, deve pegar dados da requisição para gerar as msg
		List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<MessageGenerationEvent>();
		
		MessageGenerationEvent messageGenerationEvent = new MessageGenerationEvent(0, sourceNodeId, destinationNodeId);
		messageGenerationQueue.add(messageGenerationEvent);
		return messageGenerationQueue;
	}
	
	public static List<MessageGenerationEvent> generateMessages(MessageGeneratorConfiguration config, NodeGroup nodes) { //posteriormente, deve pegar dados da requisição para gerar as msg
		List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<MessageGenerationEvent>();
		
		long sourceNodeId;
		long destinationNodeId;
		
		if (config.getSourceNodeId() != null) {
			sourceNodeId = config.getSourceNodeId();
		} else {
			sourceNodeId = (long) (Math.random() * nodes.getSize());
		}
		
		if (config.getDestinationNodeId() != null) {
			destinationNodeId = config.getDestinationNodeId();
		} else {
			destinationNodeId = (long) (Math.random() * nodes.getSize());
		}
		
		while(destinationNodeId == sourceNodeId) {
			destinationNodeId = (long) (Math.random() * nodes.getSize());
		}
		
		for(int i = 0; i < 450; i++) {
			MessageGenerationEvent messageGenerationEvent = new MessageGenerationEvent(i, sourceNodeId, destinationNodeId);
			messageGenerationQueue.add(messageGenerationEvent);
		}
		return messageGenerationQueue;
	}

}
