package com.project.simulator.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageGenerationConfiguration {

	private MessageGenerationType type;
    private String fileName;
    private int sourceNodeId;
    private int destinationNodeId;
    private double generationInstant;
    private int amountNodes;
    private long hopLimit;
    private long capacity;

    public MessageGenerationConfiguration() {}

    public MessageGenerationConfiguration(MessageGenerationType type, int sourceNodeId, int destinationNodeId, double generationInstant, int amountNodes) {
        this.type = type;
        this.sourceNodeId = sourceNodeId;
        this.destinationNodeId = destinationNodeId;
        this.generationInstant = generationInstant;
        this.amountNodes = amountNodes;
    }

    public MessageGenerationConfiguration(MessageGenerationType type, double generationInstant, int amountNodes) {
        this.type = type;
        this.generationInstant = generationInstant;
        this.amountNodes = amountNodes;
    }
}
