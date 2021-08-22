package com.project.simulator.threadHandler;

import java.util.List;

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

public class SimulationThreadHandler extends Thread {
	
	private SimulationConfiguration config;
	private SimulationThreadReportHandler simulationThreadReportHandler;
	
	public SimulationThreadHandler(SimulationThreadReportHandler simulationThreadReportHandler, SimulationConfiguration config) {
		this.simulationThreadReportHandler = simulationThreadReportHandler;
		this.config = config;
	}
	
	public void run() {
		List<MessageGenerationEvent> messageGenerationQueue = MessageGenerator.generate(config.getMessageGenerationConfiguration());
        MeetingTrace meetingTrace = MeetingTraceGenerator.generate(config.getMeetingTraceConfiguration());
        EventQueue eventQueue = EventQueue.makeEventQueue(meetingTrace, messageGenerationQueue);
        MessageTransmissionProtocol protocol = MessageTransmissionProtocolFactory.make(config.getProtocolConfiguration());
        Simulation simulation = new Simulation(protocol, eventQueue);
        simulation.start(true);
        SimulationReport report = simulation.reportSimulationResult();
	    this.simulationThreadReportHandler.addSimulationReport(report);    
	}

}