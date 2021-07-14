package com.project.test;

import java.util.List;

import com.project.simulator.entity.Pair;
import com.project.simulator.generator.messageGenerator.MessageGeneratorConfiguration;

public class SpecificPairThread extends Thread{

	private int id;
	private List<Double> results;
	private List<MessageGeneratorConfiguration> configList;
	private List<Pair> pairs;
	private double totalSimulationTime;
	
	public SpecificPairThread(int k, List<Double> results, List<MessageGeneratorConfiguration> configList, List<Pair> pairs,
			double totalSimulationTime) {
		this.id = k;
		this.results = results;
		this.configList = configList;
		this.pairs = pairs;
		this.totalSimulationTime = totalSimulationTime;
	}

	public void run() {
//		System.out.println("Iniciada thread " + id);
		SimulationTest simulationTest = new SimulationTest();
		results.add(simulationTest.specificPair(id, configList, pairs, totalSimulationTime));
	}
	
}
