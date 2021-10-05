package com.project.simulator;

import com.project.exception.SimulatorException;
import com.project.interfaces.commandLine.report.CommandLineReporter;
import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.SimulationReport;
import com.project.simulator.threadHandler.SimulationThreadHandler;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SimulationProcessor {
	
    private SimulationConfiguration config;
    private List<SimulationThreadHandler> threads;
    private SimulationReport report;
    private List<String> logs;
    boolean done;
    CommandLineReporter reporter;
    
    public SimulationProcessor(SimulationConfiguration config) {
        this.config = config;
        this.threads = new ArrayList<SimulationThreadHandler>();
        this.reporter = CommandLineReporter.makeRoot();
        this.done = false;
    }

    public SimulationReport runSimulation() {
    	
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
    			
    			SimulationThreadHandler newThread = new SimulationThreadHandler(config, CommandLineReporter.make(String.valueOf(i + 1)));
    			threads.add(newThread);
    			newThread.start();
    			System.out.println("begin thread " + i);
	    		} catch(Exception e) {
    		}
    	}
    	
    	while(true) {
    		
    		wasAnyThreadInterrupted();
    		
    		if(this.checkAllThreadsDone()) {
    			SimulationReport averageReport = this.calculateSimulationReportAverage();
    			reporter.reportSingleSimulation(averageReport);
    			this.report = averageReport;
    			this.done = true;
    			return report;
    		}
    		
    		try {
				Thread.sleep(100); //intervalo de tempo entre as verificações de término das threads
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
        
    }
    
    private boolean canInitiateNewThread() {
    	if(this.runningThreads() > 20) {
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

    private boolean checkAllThreadsDone() {
    	for(SimulationThreadHandler thread : threads) {
    		if (thread.isRunning()) return false;
		}
    	return true;
	}

	public int runningThreads() {
		int count = 0;
    	for(SimulationThreadHandler thread : threads) {
			if (thread.isRunning()) count++;
		}
		return count;
	}
	
	public int finishedThreads() {
		int count = 0;
    	for(SimulationThreadHandler thread : threads) {
			if (thread.isDone()) count++;
		}
		return count;
	}

	private SimulationReport calculateSimulationReportAverage() {
		List<Double> delayList = new ArrayList<Double>();
		List<Double> deliveryRatioList = new ArrayList<Double>();

		List<SimulationReport> reports = this.getAllSimulationReports();

		for(SimulationReport simulationPartialReport : reports) {
			delayList.add(simulationPartialReport.getAverageDelay());
			deliveryRatioList.add(simulationPartialReport.getDeliveryRatio());
		}

		double finalAverageDelay = delayList.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
		double finalDeliveryRatio = deliveryRatioList.stream().mapToDouble(Double::doubleValue).average().getAsDouble();

		return new SimulationReport(finalAverageDelay, finalDeliveryRatio, 0);
	}

	private List<SimulationReport> getAllSimulationReports() {
    	List<SimulationReport> simulationReports = new ArrayList<SimulationReport>();

    	for (SimulationThreadHandler thread : threads) {
    		simulationReports.add(thread.getReport());
		}

    	return simulationReports;
	}

}
