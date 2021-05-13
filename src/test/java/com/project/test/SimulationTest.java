package com.project.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.project.service.SimulationService;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.MessageGroup;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.Pair;
import com.project.simulator.entity.event.EventQueue;
import com.project.simulator.entity.event.MessageGenerationEvent;
import com.project.simulator.generator.messageGenerator.MessageGeneratorConfiguration;
import com.project.simulator.generator.messageGenerator.SingleMessagesGenerator;
import com.project.simulator.simulation.Simulation;
import com.project.simulator.simulation.protocols.SingleCopyEpidemicProtocol;

public class SimulationTest {
	SimulationService simulationService = new SimulationService();
	
	@Test
	public void testSimulation() {
		List<Pair> pairs = new ArrayList<Pair>();
		pairs.add(new com.project.simulator.entity.Pair(0, 1, 2.5));
		pairs.add(new com.project.simulator.entity.Pair(0, 2, 2.5));
		pairs.add(new com.project.simulator.entity.Pair(0, 3, 2.5));
		pairs.add(new com.project.simulator.entity.Pair(1, 2, 2.5));
		pairs.add(new com.project.simulator.entity.Pair(1, 3, 2.5));
		pairs.add(new com.project.simulator.entity.Pair(2, 3, 2.5));
		MeetingTrace meetingTrace = this.simulationService.generateMeetingTrace(pairs, 3d);
		this.simulationService.executeSimulation(3, meetingTrace, 4);
	}
	
	@Test
	public void testCase() {
		List<Pair> pairs = generatePairs();
		NodeGroup nodes = this.simulationService.generateNodes(15);
		MessageGeneratorConfiguration config = MessageGeneratorConfiguration.randomNodes();
		List<MessageGenerationEvent> messageGenerationQueue = SingleMessagesGenerator.generateMessages(config, nodes);
		EventQueue eventQueue = new EventQueue(this.simulationService.generateMeetingTrace(pairs, 500d), messageGenerationQueue);
		MessageGroup messages = new MessageGroup();
		
		Simulation simulation = new Simulation( 
										new SingleCopyEpidemicProtocol(), 
										eventQueue, 
										nodes, 
										messages);
		
		simulation.start();
	}
	
	private List<Pair> generatePairs() {
		List<Pair> pairs = new ArrayList<Pair>();
		
		for(int i = 0; i < 3; i++)
			pairs.addAll(generateComunity(i));
		
		pairs.addAll(generateTraveling());
		
		return pairs;
	}
	
	private List<Pair> generateComunity(int comunityNumber) {
		List<Pair> pairs = new ArrayList<Pair>();
		for (int i = comunityNumber * 5; i < comunityNumber * 5 + 5; i++) {
			for (int j = i + 1; j < comunityNumber * 5 + 5; j++) {
				pairs.add(new Pair(i, j, 2.5));				
			}
		}
		return pairs;
	}
	
	private List<Pair> generateTraveling() {
		List<Pair> pairs = new ArrayList<Pair>();
		for (int i = 0; i < 2; i++) {
			for (int j = 5; j < 15; j++) {
				pairs.add(new Pair(i, j, 1.25));
			}
		}
		return pairs;
	}
}
