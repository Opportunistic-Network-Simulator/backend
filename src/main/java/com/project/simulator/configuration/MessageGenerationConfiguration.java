package com.project.simulator.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageGenerationConfiguration {
    MessageGenerationType type;
    String fileName;
    int sourceNodeId;
    int destinationNodeId;
    double generationInstant;
    int amountNodes;
}
