package com.project.simulator.generator.messageGenerator;

import com.project.simulator.configuration.MessageGenerationConfiguration;
import com.project.simulator.entity.event.MessageGenerationEvent;

import java.util.ArrayList;
import java.util.List;

public class MessageGenerator {
    public static List<MessageGenerationEvent> generate(MessageGenerationConfiguration config) {
        switch(config.getType()) {
            case FILE:
                throw new IllegalStateException("Message generation type not implemented: file");
            case ALL_PAIRS:
                return generateAllPairs(config);
            case FIXED_NODES:
                return generateFixedNodes(config);
            case RANDOM_NODES:
                return generateRandomNodes(config);
            default:
                throw new IllegalStateException("Unexpected value: " + config.getType());
        }
    }

    private static List<MessageGenerationEvent> generateAllPairs(MessageGenerationConfiguration config) {
        List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<>();

        for(int i = 0; i < config.getAmountNodes(); i++) {
            for(int j = 0; j < config.getAmountNodes(); j++) {
                if(i!=j) {
                    messageGenerationQueue.add(new MessageGenerationEvent(config.getGenerationInstant(), i, j));
                }
            }
        }

        return messageGenerationQueue;
    }

    private static List<MessageGenerationEvent> generateFixedNodes(MessageGenerationConfiguration config) {
        List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<>();

        MessageGenerationEvent messageGenerationEvent = new MessageGenerationEvent(config.getGenerationInstant(), config.getSourceNodeId(), config.getDestinationNodeId());
        messageGenerationQueue.add(messageGenerationEvent);

        return messageGenerationQueue;
    }

    private static List<MessageGenerationEvent> generateRandomNodes(MessageGenerationConfiguration config) {
        List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<>();

        long sourceNodeId = (long) (Math.random() * config.getAmountNodes());
        long destinationNodeId = (long) (Math.random() * config.getAmountNodes());

        while(destinationNodeId == sourceNodeId) {
            destinationNodeId = (long) (Math.random() * config.getAmountNodes());
        }

        MessageGenerationEvent messageGenerationEvent = new MessageGenerationEvent(config.getGenerationInstant(), sourceNodeId, destinationNodeId);
        messageGenerationQueue.add(messageGenerationEvent);
        return messageGenerationQueue;
    }

}
