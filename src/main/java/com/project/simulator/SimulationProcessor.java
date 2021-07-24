package com.project.simulator;

import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.event.MessageGenerationEvent;
import com.project.simulator.generator.messageGenerator.SingleMessagesGenerator;
import com.project.simulator.simulation.protocols.MessageTransmissionProtocol;

public class SimulationProcessor {
    SimulationConfiguration config;
    public SimulationProcessor(SimulationConfiguration config) {
        this.config = config;
    }

    public void runSimulation() {
        List<MessageGenerationEvent> messages = SingleMessagesGenerator.generateMessages(config.messageGenerationConfig);
        List<MessageGenerationEvent> messages = SingleMessagesGenerator.generateMessages(config.messageGenerationConfig);
        List<MessageGenerationEvent> messages = SingleMessagesGenerator.generateMessages(config.messageGenerationConfig);
        MessageTransmissionProtocol protocol = MessageTransmissionProtocolFactory.getFromConfig(config.protocolConfig);
    }

}
