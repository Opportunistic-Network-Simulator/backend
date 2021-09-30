package com.project.interfaces.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.project.interfaces.web.dto.SimulationConfigurationDTO;
import com.project.interfaces.web.parser.WebParser;
import com.project.simulator.SimulationProcessor;
import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.MeetingTraceGeneratorInput;
import com.project.simulator.entity.MessageGroup;
import com.project.simulator.entity.NodeGroup;
import com.project.simulator.entity.Pair;
import com.project.simulator.entity.SimulationReport;
import com.project.simulator.entity.event.EventQueue;
import com.project.simulator.entity.event.MessageGenerationEvent;
import com.project.simulator.generator.MeetingTraceGenerator;
import com.project.simulator.generator.NodesGenerator;
import com.project.simulator.generator.messageGenerator.SingleMessagesGenerator;
import com.project.simulator.simulation.Simulation;
import com.project.simulator.simulation.protocols.SingleCopyEpidemicProtocol;

	

@Service
public class SimulationService {
	
	private HashMap<String, SimulationProcessor> simulationMap;
	private ReadWriteLock rwLock;

	public SimulationService() {
		this.simulationMap = new HashMap<String, SimulationProcessor>();
		this.rwLock = new ReentrantReadWriteLock();
	}
	
	@Async
    public void runSimulation(SimulationConfigurationDTO simulationConfigurationDTO, String key) {
		
		SimulationConfiguration config = new WebParser().parser(simulationConfigurationDTO);
		SimulationProcessor processor = new SimulationProcessor(config);
		
		Lock writeLock = rwLock.writeLock();
        
		writeLock.lock();
	 
      try {
    	  this.simulationMap.put(key, processor);
      } finally {
          writeLock.unlock();
      }
		
		processor.runSimulation();
	
	}

	public double getSimulationProgress(String key) {
		SimulationProcessor processor = this.simulationMap.get(key);
		double progress = 100*((double) processor.finishedThreads()) / processor.getConfig().getNumberOfRounds();
		return progress;
	}
}
