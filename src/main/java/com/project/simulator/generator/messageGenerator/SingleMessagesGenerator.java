package com.project.simulator.generator.messageGenerator;

import java.util.ArrayList;
import java.util.List;

import com.project.simulator.entity.event.MessageGenerationEvent;

public class SingleMessagesGenerator {
	public static List<MessageGenerationEvent> generateMessages(int nodeAmount) {
		List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<>();
		
		long sourceNodeId = (long) (Math.random() * nodeAmount);
		long destinationNodeId = (long) (Math.random() * nodeAmount);
		
		while(destinationNodeId == sourceNodeId) {
			destinationNodeId = (long) (Math.random() * nodeAmount);
		}
		
		MessageGenerationEvent messageGenerationEvent = new MessageGenerationEvent(0, sourceNodeId, destinationNodeId, Long.MAX_VALUE);
		messageGenerationQueue.add(messageGenerationEvent);
		return generateMessages(sourceNodeId, destinationNodeId);
	}
	
	public static List<MessageGenerationEvent> generateMessages(long sourceNodeId, long destinationNodeId) { //posteriormente, deve pegar dados da requisição para gerar as msg
		List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<>();
		
		MessageGenerationEvent messageGenerationEvent = new MessageGenerationEvent(0, sourceNodeId, destinationNodeId, Long.MAX_VALUE);
		messageGenerationQueue.add(messageGenerationEvent);
		return messageGenerationQueue;
	}
	
	public static List<MessageGenerationEvent> generateMessages(List<MessageGeneratorConfiguration> configList, int nodesAmount) { //posteriormente, deve pegar dados da requisição para gerar as msg
		List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<>();
		
		for(MessageGeneratorConfiguration config : configList) {
		
			long sourceNodeId;
			long destinationNodeId;
			
			if (config.getSourceNodeId() != null) {
				sourceNodeId = config.getSourceNodeId();
			} else {
				sourceNodeId = (long) (Math.random() * nodesAmount);
			}
			
			if (config.getDestinationNodeId() != null) {
				destinationNodeId = config.getDestinationNodeId();
			} else {
				destinationNodeId = (long) (Math.random() * nodesAmount);
			}
			
			while(destinationNodeId == sourceNodeId) {
				destinationNodeId = (long) (Math.random() * nodesAmount);
			}
			
			MessageGenerationEvent messageGenerationEvent = new MessageGenerationEvent(0, sourceNodeId, destinationNodeId, Long.MAX_VALUE);
			messageGenerationQueue.add(messageGenerationEvent);
		}
		return messageGenerationQueue;
	}

}
