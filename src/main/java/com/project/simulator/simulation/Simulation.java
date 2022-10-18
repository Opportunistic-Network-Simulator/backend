package com.project.simulator.simulation;

import java.util.ArrayList;
import java.util.List;

import com.project.interfaces.commandLine.report.CommandLineReporter;
import com.project.simulator.entity.*;
import com.project.simulator.entity.event.Event;
import com.project.simulator.entity.event.EventQueue;
import com.project.simulator.entity.event.MeetEvent;
import com.project.simulator.entity.event.MessageGenerationEvent;
import com.project.simulator.entity.event.SimulationOverEvent;
import com.project.simulator.simulation.protocols.MessageTransmissionProtocol;

public class Simulation {
	private final NodeGroup nodes;
	private final MessageGroup messages;
	private final EventQueue eventQueue;
	private final MessageTransmissionProtocol messageTransmissionProtocol;
	private final CommandLineReporter reporter;
	private boolean simulationHappening = false;
	private final double lastProgress = 0;
	private final boolean stopOnEndOfArrivals;
	private final long amountNodes;
	private final long capacity;

	public Simulation(MessageTransmissionProtocol messageTransmissionProtocol,
					  EventQueue eventQueue,
					  boolean stopOnEndOfArrivals,
					  CommandLineReporter reporter,
					  long amountNodes,
					  long capacity) {
		this.messageTransmissionProtocol = messageTransmissionProtocol;
		this.eventQueue = eventQueue;
		this.nodes = new NodeGroup(reporter);
		this.messages = new MessageGroup(reporter);
		this.stopOnEndOfArrivals = stopOnEndOfArrivals;
		this.reporter = reporter;
		this.amountNodes = amountNodes;
		this.capacity = capacity;
	}
	
	public void start() {
		System.out.println("start");
		for (long i = 0; i < this.amountNodes; i++) {
			this.nodes.getNode(i, this.capacity);
		}
		this.simulationHappening = true;
		while(this.simulationHappening) {
//			this.showProgress();
			this.handle(eventQueue.nextEvent(), this.stopOnEndOfArrivals);
		}

		this.reporter.reportMessageDelivery(this.messages.getMessageList());
	}
	
	private void handle(Event event, boolean stopOnEndOfArrivals) {
		if(event instanceof MessageGenerationEvent) {
			this.handleMessageGeneration((MessageGenerationEvent) event);
		} else if(event instanceof MeetEvent) {
			this.handleMeet((MeetEvent) event, stopOnEndOfArrivals);
		} else if(event instanceof SimulationOverEvent) {
			this.handleSimulationOver((SimulationOverEvent) event);
		}
	}
	
	private void handleMeet(MeetEvent event, boolean stopOnEndOfArrivals) {
		this.messageTransmissionProtocol.handleMeet(event, this.nodes);
		if(stopOnEndOfArrivals && this.messages.checkEndOfArrivals()) {
			this.handleEndOfArrivals(event.instant);
		}
	}
	
	private void handleEndOfArrivals(double instant) {
		this.eventQueue.simulationGenerateEvent(new SimulationOverEvent(instant));
	}

	private void handleSimulationOver(SimulationOverEvent event) {
		this.simulationHappening = false;
	}
	
	private void handleMessageGeneration(MessageGenerationEvent event) {
		Message generatedMessage = this.messages.generateMessage(event.getOriginNodeId(), event.getDestinationNodeId(), event.instant, event.getHopLimit());
		MessageCopy generatedCopy = generatedMessage.createCopy();
		this.nodes.getNode(generatedMessage.getSourceNode()).generateMessage(generatedCopy);
	}
	
	public SimulationReport reportSimulationResult() {
		List<Double> delays = new ArrayList<>();
		int deliveredTotal = 0;
		for(Message message : this.messages) {
			if(message.isDelivered()) {
				delays.add(message.getDelay());
				deliveredTotal++;
			}
		}
		double deliveryRatio = (double) deliveredTotal / this.messages.getSize();
		double averageDelay = delays.isEmpty() ? 0 : delays.stream().mapToDouble(Double::doubleValue).average().getAsDouble();

		double variance = 0;
		for (Double delay : delays) {
			variance += Math.pow(delay - averageDelay, 2);
		}
		variance /= delays.size();

		double std = Math.sqrt(variance);
		return new SimulationReport(averageDelay, deliveryRatio,  variance, std);
	}
	
//	private void showProgress() {
//		if(this.eventQueue.getProgress() > this.lastProgress + 0.1) {
//			this.lastProgress = this.eventQueue.getProgress();
//			System.out.println("progress: " + (this.lastProgress*100) + "%");
//		}
//	}

	public double getProgress() {
		return this.eventQueue.getProgress();
	}

}
