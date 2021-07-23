package com.project.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.project.service.SimulationService;
import com.project.simulator.entity.MessageGroup;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.Pair;
import com.project.simulator.entity.event.EventQueue;
import com.project.simulator.entity.event.MessageGenerationEvent;
import com.project.simulator.generator.messageGenerator.MessageGeneratorConfiguration;
import com.project.simulator.generator.messageGenerator.SingleMessagesGenerator;
import com.project.simulator.simulation.Simulation;
import com.project.simulator.simulation.protocols.DirectDeliveryProtocol;
import com.project.simulator.simulation.protocols.SingleCopyEpidemicProtocol;

public class SimulationRandomWaypointTest {
	SimulationService simulationService = new SimulationService();
	
	private double average(List<Double> values) {
		return values.stream()
        .mapToDouble(d -> d)
        .average()
        .orElse(0.0);
	}
	
	public double std(List<Double> a){
        double sum = 0;
        double mean = this.average(a);
 
        for (Double i : a)
            sum += Math.pow((i - mean), 2);
        return Math.sqrt( sum / ( a.size() - 1 ) ); // sample
//        return Math.sqrt( sum / ( a.size() )); // population
    }
	
	@Test
	public void testCase() {
		double totalSimulationTime = 1800;
		int numberOfNodes = 50;
		List<Pair> pairs = generatePairs(18);
		NodeGroup nodes = this.simulationService.generateNodes(numberOfNodes);
		List<MessageGenerationEvent> messageGenerationQueue = 
				SingleMessagesGenerator.generateMessages(MessageGeneratorConfiguration.allPairs(numberOfNodes), nodes);
		EventQueue eventQueue = new EventQueue(this.simulationService.generateMeetingTrace(pairs, totalSimulationTime), messageGenerationQueue);
		MessageGroup messages = new MessageGroup();
		Simulation simulation = new Simulation(
				new DirectDeliveryProtocol(), 
				eventQueue, 
				nodes, 
				messages);
		simulation.start(true);
		System.out.println("Delay: " + simulation.reportMessageDelay());
	}
	
	
	public double specificPair(int id, List<MessageGeneratorConfiguration> configList, List<Pair> pairs, double totalSimulationTime) {
		NodeGroup nodes = this.simulationService.generateNodes(15);
		List<MessageGenerationEvent> messageGenerationQueue = SingleMessagesGenerator.generateMessages(configList, nodes);
		EventQueue eventQueue = new EventQueue(this.simulationService.generateMeetingTrace(pairs, totalSimulationTime), messageGenerationQueue);
		MessageGroup messages = new MessageGroup();
		Simulation simulation = new Simulation(
				new SingleCopyEpidemicProtocol(), 
				eventQueue, 
				nodes, 
				messages);
		simulation.start(true);
		return simulation.reportMessageDelay();
	}
	
	private List<Pair> generatePairs(double speed) {
		List<Pair> pairs = new ArrayList<Pair>();
		double lambda = this.testeLambda(speed);
		for(int i = 0; i < 50; i++) {
			for(int j = i + 1; j < 50; j++) {
				pairs.add(new Pair(i, j, lambda));
			}
		}
		
		return pairs;
	}
	
	public double testeLambda(double speed) {	
		double omega = 1.3683;
		double radius = 50;
		double pi = Math.PI;
		double side = 2000;
		double lambda = (8*omega*radius*speed)/(pi*side*side);
		return lambda;
	}
}
