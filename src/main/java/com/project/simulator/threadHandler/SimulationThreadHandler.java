package com.project.simulator.threadHandler;

import java.util.List;

import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.SimulationReport;
import com.project.simulator.entity.event.Event;
import com.project.simulator.entity.event.EventQueue;
import com.project.simulator.entity.event.MeetEvent;
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
//        for(Event event : eventQueue.getEventsInTheFuture()) {
//        	if(event instanceof MessageGenerationEvent) {
//    			System.out.println("msg gen: " + event.instant);
//    		} else if(event instanceof MeetEvent) {
//    			System.out.println("meet: " + event.instant);
//    		}
//        }
        MessageTransmissionProtocol protocol = MessageTransmissionProtocolFactory.make(config.getProtocolConfiguration());
        Simulation simulation = new Simulation(protocol, eventQueue, true);
        simulation.start();
        SimulationReport report = simulation.reportSimulationResult();
	    this.simulationThreadReportHandler.addSimulationReport(report);    
	}

}
