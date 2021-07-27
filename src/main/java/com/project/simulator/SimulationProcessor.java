package com.project.simulator;

import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.SimulationReport;
import com.project.simulator.entity.event.EventQueue;
import com.project.simulator.entity.event.MessageGenerationEvent;
import com.project.simulator.generator.MeetingTraceGenerator;
import com.project.simulator.generator.messageGenerator.MessageGenerator;
import com.project.simulator.generator.messageGenerator.MessageTransmissionProtocolFactory;
import com.project.simulator.simulation.Simulation;
import com.project.simulator.simulation.protocols.MessageTransmissionProtocol;

import java.util.List;

public class SimulationProcessor {
    SimulationConfiguration config;
    public SimulationProcessor(SimulationConfiguration config) {
        this.config = config;
    }

    public SimulationReport runSimulation() {
        List<MessageGenerationEvent> messageGenerationQueue = MessageGenerator.generate(config.getMessageGenerationConfiguration());
        MeetingTrace meetingTrace = MeetingTraceGenerator.generate(config.getMeetingTraceConfiguration());
        EventQueue eventQueue = EventQueue.makeEventQueue(meetingTrace, messageGenerationQueue);
        MessageTransmissionProtocol protocol = MessageTransmissionProtocolFactory.make(config.getProtocolConfiguration());
        Simulation simulation = new Simulation(protocol, eventQueue);
        simulation.start(true);
        return simulation.reportMessageDelay();
    }

}
