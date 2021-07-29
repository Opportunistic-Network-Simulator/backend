package com.project.simulator;

import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.SimulationReport;
import com.project.simulator.entity.event.EventQueue;
import com.project.simulator.entity.event.MessageGenerationEvent;
import com.project.simulator.generator.MeetingTraceGenerator;
import com.project.simulator.generator.messageGenerator.MessageGenerator;
import com.project.simulator.generator.messageGenerator.MessageTransmissionProtocolFactory;
import com.project.simulator.simulation.Simulation;
import com.project.simulator.simulation.protocols.MessageTransmissionProtocol;
import com.project.simulator.threadHandler.SimulationThreadHandler;
import com.project.simulator.threadHandler.SimulationThreadReportHandler;

import java.util.List;

public class SimulationProcessor {
	
    private SimulationConfiguration config;
    
    public SimulationProcessor(SimulationConfiguration config) {
        this.config = config;
    }

    public SimulationReport runSimulation() {
    	
    	SimulationThreadReportHandler simulationThreadReportHandler = new SimulationThreadReportHandler();
    	
    	for(int i = 0; i < this.config.getNumberOfRounds(); i++) {
    		new SimulationThreadHandler(simulationThreadReportHandler, config).start(); 
    		//inicia a thread da simulação para essa rodada
    	}
    	
    	while(true) {
    		if(simulationThreadReportHandler.getSizeOfSimulationReportList() == this.config.getNumberOfRounds()) {
    			//verifica se todas as threads já terminaram
    			return simulationThreadReportHandler.calculateSimulationReportAverage();
    		}
    		
    		try {
				Thread.sleep(100); //intervalo de tempo entre as verificações de término das threads
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
        
    }

}
