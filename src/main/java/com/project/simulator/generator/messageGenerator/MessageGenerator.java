package com.project.simulator.generator.messageGenerator;

import com.project.exception.SimulatorException;
import com.project.interfaces.commandLine.parser.FileNamesParser;
import com.project.simulator.configuration.MessageGenerationConfiguration;
import com.project.simulator.entity.event.MessageGenerationEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MessageGenerator {
    public static List<MessageGenerationEvent> generate(MessageGenerationConfiguration config) {
        switch(config.getType()) {
            case FILE:
                return generateFileNodes(config);
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

    private static List<MessageGenerationEvent> generateFileNodes(MessageGenerationConfiguration config) {
        List<MessageGenerationEvent> messageGenerationQueue = new ArrayList<>();
        long amountNodes = config.getAmountNodes();

        String fileName = config.getFileName();
        FileNamesParser fileNamesParser = new FileNamesParser();
        String filePath = fileNamesParser.toAbsoluteInputMessageGenerationFile(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // TODO: add possibility to have double instant
                if (!line.matches("\\d+ \\d+ \\d+")) {
                    throw new SimulatorException("Invalid line in message generation file (Should be '\\d+ \\d+ \\d+'). Line: " + line);
                }

                double instant = Double.parseDouble(line.split(" ")[0]);
                long sourceNodeId = Long.parseLong(line.split(" ")[1]);
                long destinationNodeId = Long.parseLong(line.split(" ")[2]);

                if (sourceNodeId == destinationNodeId) {
                    throw new SimulatorException("Source node cannot be equals to destination node.");
                }
                if (Math.max(sourceNodeId, destinationNodeId) > amountNodes) {
                    throw new SimulatorException("nodeId value is greater than the amount of nodes.");
                }

                MessageGenerationEvent messageGenerationEvent = new MessageGenerationEvent(instant, sourceNodeId, destinationNodeId);
                messageGenerationQueue.add(messageGenerationEvent);
            }
        } catch (IOException e) {
            throw new SimulatorException(e.getMessage());
        }

        return messageGenerationQueue;
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
