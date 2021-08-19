package com.project.simulator.configuration;

import lombok.AllArgsConstructor;
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

    public MessageGenerationConfiguration() {}

    public MessageGenerationConfiguration(MessageGenerationType type, int sourceNodeId, int destinationNodeId, double generationInstant) {
        this.type = type;
        this.sourceNodeId = sourceNodeId;
        this.destinationNodeId = destinationNodeId;
        this.generationInstant = generationInstant;
    }

    public MessageGenerationConfiguration(MessageGenerationType type, double generationInstant, int amountNodes) {
        this.type = type;
        this.generationInstant = generationInstant;
        this.amountNodes = amountNodes;
    }
}
