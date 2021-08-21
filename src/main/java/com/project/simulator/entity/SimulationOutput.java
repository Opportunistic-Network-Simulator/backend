package com.project.simulator.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SimulationOutput {
	
	private ReadWriteLock rwLock;
	private List<Double> deliveryDelaySimulationResults;
	
	public SimulationOutput() {
		this.rwLock = new ReentrantReadWriteLock();
		this.deliveryDelaySimulationResults = new ArrayList<Double>();
	}
	
	public void addDeliveryDelaySimulationResult(Double result) {
		 Lock writeLock = rwLock.writeLock();
	        
		 writeLock.lock();
	 
        try {
            this.deliveryDelaySimulationResults.add(result);
        } finally {
            writeLock.unlock();
        }
	}
	
	public Double getOneDeliveryDelaySimulationResult(int index) {
        Lock readLock = rwLock.readLock();
        readLock.lock();
 
        try {
            return this.deliveryDelaySimulationResults.get(index);
        } finally {
            readLock.unlock();
        }
    }
	
	public List<Double> getAllDeliveryDelaySimulationResults(){
		return new ArrayList<Double>(this.deliveryDelaySimulationResults); 
		//Por segurança, retorna uma cópia do deliveryDelaySimulationResults, e não a referência em memória. 
	}													
	
	public int getSizeOfDeliveryDelaySimulationResults() {
        Lock readLock = rwLock.readLock();
        
        readLock.lock();
 
        try {
            return this.deliveryDelaySimulationResults.size();
        } finally {
            readLock.unlock();
        }
    }

}
