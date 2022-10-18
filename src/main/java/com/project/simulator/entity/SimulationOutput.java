package com.project.simulator.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SimulationOutput {
	
	private final ReadWriteLock rwLock;
	private final List<Double> deliveryDelaySimulationResults;
	
	public SimulationOutput() {
		this.rwLock = new ReentrantReadWriteLock();
		this.deliveryDelaySimulationResults = new ArrayList<>();
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
		return new ArrayList<>(this.deliveryDelaySimulationResults);
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
