package com.project.simulator.entity;

import com.project.simulator.exception.ValueInputException;

import lombok.Getter;

@Getter
public class Pair {
	
	private final long node1;
	private final long node2;
	private final double minRate;
	private final double maxRate;
	
	public Pair(long node1, long node2, double rate, double variabilityDegree) throws ValueInputException {
		if(node1 >= node2) {
			throw new ValueInputException("Node 1 value is bigger than node 2");
		}
		
		if(variabilityDegree >= 1 || variabilityDegree < 0) {
			throw new ValueInputException("Variability degree is invalid: " + variabilityDegree);
		}
		
		this.node1 = node1;
		this.node2 = node2;
		this.minRate = rate*(1 - variabilityDegree);
		this.maxRate = rate*(1 + variabilityDegree);
	}
	
	public Pair(long node1, long node2, double rate) throws ValueInputException {
		this(node1, node2, rate, 0);
	}
	
	public double generateDt(boolean firstMeet) {
		double rate;
		if(firstMeet) {
			rate = (maxRate + minRate)/2.0; //in first meet, must be use the informed rate
		} else {
			rate = Math.random() * (maxRate - minRate) + minRate; // random rate by uniform distribution in specified interval	
		}
		double u = Math.random();
		return -Math.log(1 - u)/rate; // inverse transformation method
	}
	
	public boolean pairMeets() {
		return minRate > 0 && maxRate > 0;
	}
}
