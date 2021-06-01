package com.project.simulator.simulation;

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
	
	public void start() {
		this.simulationHappening = true;
		while(this.simulationHappening) {
			this.showProgress();
			this.handle(eventQueue.nextEvent());
		}
	}
	
	private void handle(Event event) {
		if(event instanceof MessageGenerationEvent) {
			this.handleMessageGeneration((MessageGenerationEvent) event);
		} else if(event instanceof MeetEvent) {
			this.handleMeet((MeetEvent) event);
		} else if(event instanceof SimulationOverEvent) {
			this.handleSimulationOver((SimulationOverEvent) event);
		}
	}
	
	private void handleMeet(MeetEvent event) {
		this.messageTransmissionProtocol.handleMeet(event, this.nodes);
	}

	private void handleSimulationOver(SimulationOverEvent event) {
		this.simulationHappening = false;
	}
	
	private void handleMessageGeneration(MessageGenerationEvent event) {
		Message generatedMessage = this.messages.generateMessage(event.getOriginNodeId(), event.getDestinationNodeId(), event.instant);
		System.out.println("Message " + generatedMessage.getId() + " generated at " + event.instant + " with source in node " + generatedMessage.getSourceNode() + " and destination to node " + generatedMessage.getDestinationNode());
		this.nodes.getNode(generatedMessage.getSourceNode()).addMessage(generatedMessage);
	}
	
	public double reportMessageDelay() {
		double[] delays = new double[this.messages.getSize()];
		int i = 0;
		for(Message message : this.messages) {
			System.out.println("Mensagem " + message.getId() + ": " + (message.getArrivalInstant() - message.getGenarationInstant()));
			delays[i] = message.getArrivalInstant() - message.getGenarationInstant();
			i++;
		}
		return this.avarage(delays);
	}
	
	private double avarage(double[] delays) {
		double sum = 0;
		for(double delay : delays) {
			sum += delay;
		}
		return sum / delays.length;
	}
	
	private void showProgress() {
		if(this.eventQueue.getProgress() > this.lastProgress + 0.1) {
			this.lastProgress = this.eventQueue.getProgress();
			System.out.println("Progress: " + (this.lastProgress * 100) + "%");
		}
	}

}
