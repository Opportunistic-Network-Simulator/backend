package com.project.simulator;

import com.project.exception.SimulatorException;
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

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

public class SimulationProcessor {
	
    private SimulationConfiguration config;
    private List<SimulationThreadHandler> threads;
    private SimulationThreadReportHandler simulationThreadReportHandler;
    
    public SimulationProcessor(SimulationConfiguration config) {
        this.config = config;
        this.threads = new ArrayList<SimulationThreadHandler>();
    }

    public SimulationReport runSimulation() {
    	
    	this.simulationThreadReportHandler = new SimulationThreadReportHandler();
    	
    	for(int i = 0; i < this.config.getNumberOfRounds(); i++) {
    		
    		while(!canInitiateNewThread()) {
    			
    			wasAnyThreadInterrupted();
    			
    			try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		try {
    			
    			SimulationThreadHandler newThread = new SimulationThreadHandler(simulationThreadReportHandler, config);
    			threads.add(newThread);
    			newThread.start();
    			System.out.println("begin thread " + i);
    			//inicia a thread da simulação para essa rodada
    		} catch(Exception e) {
    		}
    	}
    	
    	while(true) {
    		
    		wasAnyThreadInterrupted();
    		
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
    
    private boolean canInitiateNewThread() {
    	if(threads.size() - simulationThreadReportHandler.getSizeOfSimulationReportList() > 20) {
    		//diferença entre qtd de threads iniciadas e qtd de threads com resultado final, ou seja, é a qtd de threads ainda em andamento
    		return false;	
    	}
    	return true;
    }
    
    private void wasAnyThreadInterrupted() {
    	for (SimulationThreadHandler thread : this.threads) {
    		if(thread.isError())
    			throw new SimulatorException(thread.getErrorMessage());
    	}
    	
    }

}
