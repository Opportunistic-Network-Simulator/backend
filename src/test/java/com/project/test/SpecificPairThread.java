package com.project.test;

import java.util.List;

import com.project.simulator.entity.Pair;
import com.project.simulator.entity.SimulationOutput;
import com.project.simulator.generator.messageGenerator.MessageGeneratorConfiguration;

public class SpecificPairThread extends Thread{

	private int id;
	private SimulationOutput simulationOutput;
	private List<MessageGeneratorConfiguration> configList;
	private List<Pair> pairs;
	private double totalSimulationTime;
	
	public SpecificPairThread(int k, SimulationOutput simulationOutput, List<MessageGeneratorConfiguration> configList, List<Pair> pairs,
			double totalSimulationTime) {
		this.id = k;
		this.simulationOutput = simulationOutput;
		this.configList = configList;
		this.pairs = pairs;
		this.totalSimulationTime = totalSimulationTime;
	}

	public void run() {
//		System.out.println("Iniciada thread " + id);
		SimulationTest simulationTest = new SimulationTest();
		double deliveryDelaySimulationResult = simulationTest.specificPair(id, configList, pairs, totalSimulationTime);
		this.simulationOutput.addDeliveryDelaySimulationResult(deliveryDelaySimulationResult); //operação bloqueante
	}
	
}
