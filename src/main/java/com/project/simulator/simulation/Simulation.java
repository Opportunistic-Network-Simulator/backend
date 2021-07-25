package com.project.simulator.simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	private long messageIdCounter;
	
	public Simulation(	MessageTransmissionProtocol messageTransmissionProtocol, 
						EventQueue eventQueue) {
		this.messageTransmissionProtocol = messageTransmissionProtocol;
		this.eventQueue = eventQueue;
		this.nodes = nodes;
		this.messages = messages;
		this.messageIdCounter = 0;
	}
	
	public void start(boolean stopOnEndOfArrivals) {
		this.simulationHappening = true;
		while(this.simulationHappening) {
			this.showProgress();
			this.handle(eventQueue.nextEvent(), stopOnEndOfArrivals);
		}
	}
	
	private void handle(Event event, boolean stopOnEndOfArrivals) {
		if(event instanceof MessageGenerationEvent) {
			this.handleMessageGeneration((MessageGenerationEvent) event);
//			System.out.println("MessageGenerationEvent");
		} else if(event instanceof MeetEvent) {
			this.handleMeet((MeetEvent) event, stopOnEndOfArrivals);
//			System.out.println("MeetEvent");
		} else if(event instanceof SimulationOverEvent) {
			this.handleSimulationOver((SimulationOverEvent) event);
//			System.out.println("SimulationOverEvent");
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
	/* List<MessageGenerationEvent> messages = SingleMessagesGenerator.generateMessages(config.messageGenerationConfig);
        List<MessageGenerationEvent> messages = SingleMessagesGenerator.generateMessages(config.messageGenerationConfig);
        List<MessageGenerationEvent> messages = SingleMessagesGenerator.generateMessages(config.messageGenerationConfig);
        MessageTransmissionProtocol protocol = MessageTransmissionProtocolFactory.getFromConfig(config.protocolConfig);
        Simulation simulation = sndoifa;
        result = simutaion.start();
        Output output = OutputFactory.generate(config.outputConfiguration);
        output.handle(result);*/
	private void handleSimulationOver(SimulationOverEvent event) {
		this.simulationHappening = false;
	}
	
	private void handleMessageGeneration(MessageGenerationEvent event) {
		Message generatedMessage = this.messages.generateMessage(this.messageIdCounter++, event.getOriginNodeId(), event.getDestinationNodeId(), event.instant);
		this.nodes.getNode(generatedMessage.getSourceNode()).addMessage(generatedMessage);
	}
	
	public double reportMessageDelay() {
		List<Double> delays = new ArrayList<Double>();
		int i = 0;
		for(Message message : this.messages) {
			if(message.isDelivered()) {
				delays.add(message.getArrivalInstant() - message.getGenarationInstant());
//				System.out.println("message " + message.getId() + " arrival at " + message.getArrivalInstant());
				i++;
			} else {
//				System.out.println("Mensagem " + message.getId() + ": não foi entregue");
			}
		}
		System.out.println("Delivery ratio: " + (double) i / this.messages.getSize());
		return delays.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
	}
	
	private void showProgress() {
		if(this.eventQueue.getProgress() > this.lastProgress + 0.1) {
			this.lastProgress = this.eventQueue.getProgress();
//			System.out.println("progress: " + this.lastProgress);
		}
	}

}
