package com.project.simulator.threadHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.project.simulator.entity.SimulationReport;

public class SimulationThreadReportHandler {
	
	private ReadWriteLock rwLock;
	private List<SimulationReport> simulationReportList;
	
	public SimulationThreadReportHandler() {
		this.rwLock = new ReentrantReadWriteLock();
		this.simulationReportList = new ArrayList<SimulationReport>();
	}
	
	public void addSimulationReport(SimulationReport report) {
		 Lock writeLock = rwLock.writeLock();
	        
		 writeLock.lock();
	 
       try {
           this.simulationReportList.add(report);
       } finally {
           writeLock.unlock();
       }
	}
	
	public int getSizeOfSimulationReportList() {
        Lock readLock = rwLock.readLock();
        
        readLock.lock();
 
        try {
            return this.simulationReportList.size();
        } finally {
            readLock.unlock();
        }
    }
	
	public SimulationReport calculateSimulationReportAverage() {
		List<Double> delayList = new ArrayList<Double>();
		List<Double> deliveryRatioList = new ArrayList<Double>();
		
		for(SimulationReport simulationPartialReport : this.simulationReportList) {
			delayList.add(simulationPartialReport.getAverageDelay());
			deliveryRatioList.add(simulationPartialReport.getDeliveryRatio());
		}
		
		double finalAverageDelay = delayList.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
		double finalDeliveryRatio = deliveryRatioList.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
		
		return new SimulationReport(finalAverageDelay, finalDeliveryRatio, 0);
	}

}
