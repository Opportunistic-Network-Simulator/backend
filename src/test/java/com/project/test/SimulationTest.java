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
	
	// @Test
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
		List<Double> results = new ArrayList<Double>();
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				if(i == j) continue;
				MessageGeneratorConfiguration config = MessageGeneratorConfiguration.fixedNodes(i, j);
				List<Double> partial = executeTenTimes(pairs, config);
				results.add(report(partial, i, j));
			}
		}
		System.out.println(this.avarage(results));
	}

	private List<Double> executeTenTimes(List<Pair> pairs, MessageGeneratorConfiguration config) {
		List<Double> results = new ArrayList<Double>();
		for(int k = 0; k < 100; k++) {
			results.add(this.specificPair(config, pairs));
		}
		return results;
	}
	
	private double report(List<Double> results, int from, int to) {
		double avg = avarage(results);
		System.out.println("From " + from + " to " + to + ": " + avg);
		return avg;
	}
	
	private double avarage(List<Double> values) {
		Double total = 0d;
		for(Double value : values) {
			total += value;
		}
		return total / values.size();
	}
	
	private double specificPair(MessageGeneratorConfiguration config, List<Pair> pairs) {
		NodeGroup nodes = this.simulationService.generateNodes(15);
		List<MessageGenerationEvent> messageGenerationQueue = SingleMessagesGenerator.generateMessages(config, nodes);
		EventQueue eventQueue = new EventQueue(this.simulationService.generateMeetingTrace(pairs, 100d), messageGenerationQueue);
		MessageGroup messages = new MessageGroup();	
		Simulation simulation = new Simulation( 
				new SingleCopyEpidemicProtocol(), 
				eventQueue, 
				nodes, 
				messages);
		simulation.start();
		return simulation.reportMessageDelay();
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
				if (i == 0 || i == 1 )
					pairs.add(new Pair(i, j, 1.25));
				else
					pairs.add(new Pair(i, j, 2.5));
			}
		}
		return pairs;
	}
	
	private List<Pair> generateTraveling() {
		List<Pair> pairs = new ArrayList<Pair>();
		for (int i = 0; i < 2; i++) {
			for (int j = 5 * (i + 1); j < 5 * (i + 2); j++) {
				pairs.add(new Pair(i, j, 1.25));
			}
		}
		return pairs;
	}
}
