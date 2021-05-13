package com.project.simulator.generator.messageGenerator;

public class MessageGeneratorConfiguration {
	private Long sourceNodeId;
	private Long destinationNodeId;
	
	private MessageGeneratorConfiguration(Long sourceNodeId, Long destinationNodeId) {
		this.sourceNodeId = sourceNodeId;
		this.destinationNodeId = destinationNodeId;
	}
	
	public static MessageGeneratorConfiguration randomNodes() {
		return new MessageGeneratorConfiguration(null, null);
	}
	
	public static MessageGeneratorConfiguration fixedNodes(long sourceNodeId, long destinationNodeId) {
		return new MessageGeneratorConfiguration(sourceNodeId, destinationNodeId);
	}

	public Long getSourceNodeId() {
		return sourceNodeId;
	}

	public Long getDestinationNodeId() {
		return destinationNodeId;
	}
}
