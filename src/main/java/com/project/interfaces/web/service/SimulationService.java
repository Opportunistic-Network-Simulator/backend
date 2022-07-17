package com.project.interfaces.web.service;

import java.io.File;
import java.io.IOException;
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
import com.project.simulator.entity.SimulationReport;

@Service
public class SimulationService {
	
	private final HashMap<String, SimulationProcessor> simulationMap;
	private final ReadWriteLock rwLock;

	@Autowired
	private FileStorageService fileStorageService;

	public SimulationService() {
		this.simulationMap = new HashMap<>();
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
		return processor.getProgress();
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
