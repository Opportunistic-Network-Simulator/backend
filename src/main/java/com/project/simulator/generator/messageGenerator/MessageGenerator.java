package com.project.simulator.generator.messageGenerator;

import com.project.exception.SimulatorException;
import com.project.simulator.configuration.MessageGenerationConfiguration;
import com.project.simulator.entity.event.MessageGenerationEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MessageGenerator {
    public static List<MessageGenerationEvent> generate(MessageGenerationConfiguration config) {
        switch(config.getType()) {
            case FILE:
                try {
                    return generateFileNodes(config);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
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

    private static List<MessageGenerationEvent> generateFileNodes(MessageGenerationConfiguration config) throws FileNotFoundException {
        List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<>();
        long amountNodes = config.getAmountNodes();

        File messageGenerationFile = new File("../../files/messageGenerationFile");
        Scanner reader = new Scanner(messageGenerationFile);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            if (!data.matches("\\d+ \\d+")) {
                throw new SimulatorException("The lines of the message generation file must be of the format \"\\d+ \\d+\".");
            }

            long sourceNodeId = Integer.parseInt(data.split(" ")[0]);
            long destinationNodeId = Integer.parseInt(data.split(" ")[1]);
            if (sourceNodeId == destinationNodeId) {
                throw new SimulatorException("Source node cannot be destination node.");
            }
            if (Math.max(sourceNodeId, destinationNodeId) > amountNodes) {
                throw new SimulatorException("nodeId value is greater than the amount of nodes.");
            }

            MessageGenerationEvent messageGenerationEvent = new MessageGenerationEvent(config.getGenerationInstant(), sourceNodeId, destinationNodeId);
            messageGenerationQueue.add(messageGenerationEvent);
        }
        reader.close();

        return messageGenerationQueue;
    }

}
