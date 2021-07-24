package com.project.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.project.service.SimulationService;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.MessageGroup;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.Pair;
import com.project.simulator.entity.SimulationOutput;
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
		this.simulationService.executeSimulation(3, meetingTrace, 4, true);
	}
	
//	@Test
	public void testCase() {
		List<Pair> pairs = generatePairs(); 
		List<Double> results = new ArrayList<Double>();
		int n = 15;
		int count = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(i == j) continue; //dps testar colcoar apenas para i<j
				List<MessageGeneratorConfiguration> configList = MessageGeneratorConfiguration.fixedNodes(i, j);
				List<Double> partial = executeNTimes(pairs, configList, 400, 100d);
				results.add(report(partial, i, j));
				count++;
//				System.out.printf("progress: %.2f%%%n", count*100/(double) (n*n) );
			}
		}
		System.out.println("média: " + this.average(results));
		System.out.println("desvio padrão: " + this.std(results));
	}
	
//	@Test
	public void testCaseInParallel() throws InterruptedException {
		List<Pair> pairs = generatePairs();
		List<Double> results = new ArrayList<Double>();
		int n = 15;
		int count = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(i == j) continue; //dps testar colcoar apenas para i<j
				List<MessageGeneratorConfiguration> configList = MessageGeneratorConfiguration.fixedNodes(i, j);
				List<Double> partial = executeNTimesInParallel(pairs, configList, 400, 100d);
				results.add(report(partial, i, j));
				count++;
//				System.out.printf("progress: %.2f%%%n", count*100/(double) (n*n) );
			}
		}
		System.out.println("média: " + this.average(results));
		System.out.println("desvio padrão: " + this.std(results));
	}
	
//	@Test
	public void testCaseAllPairs() {
		List<Pair> pairs = generatePairs();
		List<MessageGeneratorConfiguration> configList = MessageGeneratorConfiguration.allPairs(15);
		List<Double> results = executeNTimes(pairs, configList, 400, 100d);
		
		System.out.println("média: " + this.average(results));
	}
	
	private List<Double> executeNTimes(List<Pair> pairs, List<MessageGeneratorConfiguration> configList, int numberOfRounds,
			double totalSimulationTime) {
		List<Double> results = new ArrayList<Double>();
		for(int k = 0; k < numberOfRounds; k++) { //qtd de rodadas para essa config
			results.add(this.specificPair(k, configList, pairs, totalSimulationTime));
		}
		return results;
	}
	
	@Test
	public void testCaseAllPairsInParallel() throws InterruptedException {
		List<Pair> pairs = generatePairs();
		List<MessageGeneratorConfiguration> configList = MessageGeneratorConfiguration.allPairs(15);
		
		List<Double> results = executeNTimesInParallel(pairs, configList, 400, 100d);
		
		System.out.println("média: " + this.average(results));
	}
	
	private List<Double> executeNTimesInParallel(List<Pair> pairs, List<MessageGeneratorConfiguration> configList, int numberOfRounds,
			double totalSimulationTime) throws InterruptedException {
		SimulationOutput simulationOutput = new SimulationOutput();
 		for(int k = 0; k < numberOfRounds; k++) { //qtd de rodadas para essa config
			new SpecificPairThread(k, simulationOutput, configList, pairs, totalSimulationTime).start();
		}
		int size;
		while( (size = simulationOutput.getSizeOfDeliveryDelaySimulationResults()) != numberOfRounds) {
			System.out.println("current size: " + size);
			Thread.sleep(100);
		}
		System.out.println("current size: " + size);
		return simulationOutput.getAllDeliveryDelaySimulationResults();
	}
		
	private double report(List<Double> results, int from, int to) {
		double avg = average(results);
//		System.out.println("From " + from + " to " + to + ": " + avg);
		return avg;
	}
	
	private double average(List<Double> values) {
		return values.stream()
        .mapToDouble(d -> d)
        .average()
        .orElse(0.0);
	}
	
	private double std (List<Double> a){
        double sum = 0;
        double mean = this.average(a);
 
        for (Double i : a)
            sum += Math.pow((i - mean), 2);
        return Math.sqrt( sum / ( a.size() - 1 ) ); // sample
//        return Math.sqrt( sum / ( a.size() )); // population
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
