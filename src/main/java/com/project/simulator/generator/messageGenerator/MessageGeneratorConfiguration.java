package com.project.simulator.generator.messageGenerator;

import java.util.ArrayList;
import java.util.List;

public class MessageGeneratorConfiguration {
	private final Long sourceNodeId;
	private final Long destinationNodeId;
	
	private MessageGeneratorConfiguration(Long sourceNodeId, Long destinationNodeId) {
		this.sourceNodeId = sourceNodeId;
		this.destinationNodeId = destinationNodeId;
	}
	
	public static MessageGeneratorConfiguration randomNodes() {
		return new MessageGeneratorConfiguration(null, null);
	}
	
	public static List<MessageGeneratorConfiguration> fixedNodes(long sourceNodeId, long destinationNodeId) {
		List<MessageGeneratorConfiguration> configList = new ArrayList<>();
		configList.add(new MessageGeneratorConfiguration(sourceNodeId, destinationNodeId));
		return configList;
	}

	public static List<MessageGeneratorConfiguration> allPairs(int numberOfNodes){
		List<MessageGeneratorConfiguration> configList = new ArrayList<>();
		for(int i = 0; i < numberOfNodes; i++) {
			for(int j = 0; j < numberOfNodes; j++) {
				if(i!=j) {
				configList.add(new MessageGeneratorConfiguration((long) i, (long) j));
				}
			}
		}
		return configList;
	}
	
	public Long getSourceNodeId() {
		return sourceNodeId;
	}

	public Long getDestinationNodeId() {
		return destinationNodeId;
	}
}
