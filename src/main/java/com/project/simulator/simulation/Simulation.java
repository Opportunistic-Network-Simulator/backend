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
	private double instant = 0;
	private double totalSimulationTime;
	private MessageTransmissionProtocol messageTransmissionProtocol;
	private boolean simulationHappening = false;
	private double lastProgress = 0;
	
	public Simulation(
						double totalSimulationTime, 
						MessageTransmissionProtocol messageTransmissionProtocol, 
						EventQueue eventQueue, 
						NodeGroup nodes, 
						MessageGroup messages) {
		this.totalSimulationTime = totalSimulationTime;
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
		this.reportMessageDelay();
	}
	
	private void handle(Event event) {
		this.instant = event.instant;
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
		this.nodes.getNode(generatedMessage.getSourceNode()).addMessage(generatedMessage);
	}
	
	public void reportMessageDelay() {
		for(Message message : this.messages) {
			System.out.println("Mensagem " + message.getId() + ": " + (message.getArrivalInstant() - message.getGenarationInstant()));
		}
	}
	
	private void showProgress() {
		if(this.eventQueue.getProgress() > this.lastProgress + 0.1) {
			this.lastProgress = this.eventQueue.getProgress();
			System.out.println("Progress: " + (this.lastProgress * 100) + "%");
		}
	}

}
