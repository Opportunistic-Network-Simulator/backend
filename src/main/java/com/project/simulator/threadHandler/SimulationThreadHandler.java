package com.project.simulator.threadHandler;


import java.util.List;

import com.project.interfaces.commandLine.report.CommandLineReporter;
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

import lombok.Getter;
import lombok.Setter;

@Getter
public class SimulationThreadHandler extends Thread {
	
	private SimulationConfiguration config;
	private boolean error;
	private String errorMessage;
	
	@Setter
	private boolean running;
	private boolean done;
	private Simulation simulation;
	private CommandLineReporter reporter;
	
	public SimulationThreadHandler(SimulationConfiguration config, CommandLineReporter reporter) {
		this.config = config;
		this.error = false;
		this.reporter = reporter;
		this.done = false;
	}
	
	public void run() {
		try {
//			this.running = true;
			List<MessageGenerationEvent> messageGenerationQueue = MessageGenerator.generate(config.getMessageGenerationConfiguration());
	        MeetingTrace meetingTrace = MeetingTraceGenerator.generate(config.getMeetingTraceConfiguration());
	        reporter.reportMeetingTrace(meetingTrace);
	        EventQueue eventQueue = EventQueue.makeEventQueue(meetingTrace, messageGenerationQueue);
	        MessageTransmissionProtocol protocol = MessageTransmissionProtocolFactory.make(config.getProtocolConfiguration());
	        this.simulation = new Simulation(protocol, eventQueue, true);
	        simulation.start();
	        this.done = true;
	        System.out.println("finished simulation");
			
		} catch(Exception e) {
			e.printStackTrace();
			this.error = true;
			this.errorMessage = e.getMessage();
		}
		this.running = false;
		  
	}

	public SimulationReport getReport() {
		return this.simulation.reportSimulationResult();
	}

	public double getProgress() {
		if(this.simulation == null) //caso em que simulation não começou ainda para essa thread
			return 0;
		if(this.done)
			return 1;
		return this.simulation.getProgress();
	}

}
