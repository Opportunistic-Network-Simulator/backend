package com.project.simulator.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MessageGenerationConfiguration {
    MessageGenerationType type;
    String fileName;
    int sourceNodeId;
    int destinationNodeId;
    double generationInstant;
    int amountNodes;

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
