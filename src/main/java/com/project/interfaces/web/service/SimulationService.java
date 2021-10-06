package com.project.interfaces.web.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.project.simulator.threadHandler.SimulationThreadHandler;

	

@Service
public class SimulationService {
	
	private HashMap<String, SimulationProcessor> simulationMap;
	private ReadWriteLock rwLock;

	@Autowired
	private FileStorageService fileStorageService;

	public SimulationService() {
		this.simulationMap = new HashMap<String, SimulationProcessor>();
		this.rwLock = new ReentrantReadWriteLock();
	}
	
	@Async
    public void runSimulation(SimulationConfigurationDTO simulationConfigurationDTO, String key) {
		
		SimulationConfiguration config = new WebParser().parser(simulationConfigurationDTO);
		SimulationProcessor processor = new SimulationProcessor(config, key);
		System.out.println("key: " + key);
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
		double progress = 0;
		for(SimulationThreadHandler thread : processor.getThreads()) {
			progress+=thread.getProgress();
		}
		progress *= 100/processor.getConfig().getNumberOfRounds();
		return BigDecimal.valueOf(progress)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		}
	
	public SimulationReport getSimulationReport(String key) {
		SimulationProcessor processor = this.simulationMap.get(key);
		return processor.getReport();
	}

	public String getZippedReport(String key) throws IOException {
		SimulationProcessor processor = this.simulationMap.get(key);
		List<File> files = processor.getAllSimulationFiles();
		return this.fileStorageService.zipB64(files, processor.getReporter().getFileNameManager().getRootFolder());
	}

}
