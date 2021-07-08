package com.project.simulator.simulation;

import java.util.Arrays;
import java.util.OptionalDouble;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.MessageGroup;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.event.Event;
import com.project.simulator.entity.event.EventQueue;
import com.project.simulator.entity.event.MeetEvent;
import com.project.simulator.entity.event.MessageGenerationEvent;
import com.project.simulator.entity.event.SimulationOverEvent;
import com.project.simulator.simulation.protocols.MessageTransmissionProtocol;

public class Simulation {
	private NodeGroup nodes;
	private MessageGroup messages;
	private EventQueue eventQueue;
	private MessageTransmissionProtocol messageTransmissionProtocol;
	private boolean simulationHappening = false;
	private double lastProgress = 0;
	
	public Simulation(
						MessageTransmissionProtocol messageTransmissionProtocol, 
						EventQueue eventQueue, 
						NodeGroup nodes, 
						MessageGroup messages) {
		this.messageTransmissionProtocol = messageTransmissionProtocol;
		this.eventQueue = eventQueue;
		this.nodes = nodes;
		this.messages = messages;
	}
	
	public void start(boolean stopOnEndOfArrivals) {
		this.simulationHappening = true;
		Message.idCounter = 0;
		while(this.simulationHappening) {
			this.showProgress();
			this.handle(eventQueue.nextEvent(), stopOnEndOfArrivals);
		}
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
		if(stopOnEndOfArrivals && this.messages.checkEndOfArrivals()) 
			this.handleEndOfArrivals(event.instant);
	}
	
	private void handleEndOfArrivals(double instant) {
		this.eventQueue.simulationGenerateEvent(new SimulationOverEvent(instant));
	}
	
	private void handleSimulationOver(SimulationOverEvent event) {
		this.simulationHappening = false;
	}
	
	private void handleMessageGeneration(MessageGenerationEvent event) {
		Message generatedMessage = this.messages.generateMessage(event.getOriginNodeId(), event.getDestinationNodeId(), event.instant);
//		 System.out.println("Message " + generatedMessage.getId() + " generated at " + event.instant + " with source in node " + generatedMessage.getSourceNode() + " and destination to node " + generatedMessage.getDestinationNode());
		this.nodes.getNode(generatedMessage.getSourceNode()).addMessage(generatedMessage);
	}
	
	public double reportMessageDelay() {
		double[] delays = new double[this.messages.getSize()];
		int i = 0;
		for(Message message : this.messages) {
			if(message.isDelivered()) {
//				System.out.println("Mensagem " + message.getId() + ": " + (message.getArrivalInstant() - message.getGenarationInstant()));
				delays[i] = message.getArrivalInstant() - message.getGenarationInstant();
				i++;
			} else {
				System.out.println("Mensagem " + message.getId() + ": não foi entregue");
			}
		}
		return Arrays.stream(delays).average().getAsDouble();
	}
	
	private void showProgress() {
		if(this.eventQueue.getProgress() > this.lastProgress + 0.1) {
			this.lastProgress = this.eventQueue.getProgress();
			// System.out.println("Progress: " + (this.lastProgress * 100) + "%");
		}
	}

}
