package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.MeetingTraceGeneratorInput;
import com.project.simulator.entity.MessageGroup;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.Pair;
import com.project.simulator.entity.event.EventQueue;
import com.project.simulator.entity.event.MessageGenerationEvent;
import com.project.simulator.generator.MeetingTraceGenerator;
import com.project.simulator.generator.NodesGenerator;
import com.project.simulator.generator.messageGenerator.SingleMessagesGenerator;
import com.project.simulator.simulation.Simulation;
import com.project.simulator.simulation.protocols.SingleCopyEpidemicProtocol;

	

@Service
public class SimulationService {
	
	public NodeGroup generateNodes(long numberOfNodes) {
		return NodesGenerator.generateNodes(numberOfNodes);
	}

	public List<MessageGenerationEvent> generateMessages(NodeGroup nodes) {
		return SingleMessagesGenerator.generateMessages(nodes);
	}
	
	public MeetingTrace generateMeetingTrace(List<Pair> pairs, double totalSimulationTime) {
		return MeetingTraceGenerator.generateMeetingTrace(new MeetingTraceGeneratorInput(pairs, totalSimulationTime));
	}

	public double executeSimulation(	double totalSimulationTime,
									MeetingTrace meetingTrace,
									long nodeQuantity,
									boolean stopOnEndOfArrivals) {
		NodeGroup nodes = this.generateNodes(nodeQuantity);
		List<MessageGenerationEvent> messageGenerationQueue = this.generateMessages(nodes);
		EventQueue eventQueue = new EventQueue(meetingTrace, messageGenerationQueue);
		MessageGroup messages = new MessageGroup();
		
		Simulation simulation = new Simulation( 
										new SingleCopyEpidemicProtocol(), 
										eventQueue, 
										nodes, 
										messages);
		
		simulation.start(stopOnEndOfArrivals);
		return simulation.reportMessageDelay();
	}
}
