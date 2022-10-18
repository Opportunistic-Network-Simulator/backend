package com.project.simulator.entity;

import com.project.simulator.entity.distribution.Distribution;
import com.project.simulator.exception.ValueInputException;

import lombok.Getter;

@Getter
public class Pair {
	
	private final long node1;
	private final long node2;
	private final Distribution distribution;
	
	public Pair(long node1, long node2, Distribution distribution) throws ValueInputException {
		if(node1 >= node2) {
			throw new ValueInputException("Node 1 value is greater or equal to node 2. Node 1: " + node1 + ". Node 2: " + node2);
		}
		this.node1 = node1;
		this.node2 = node2;
		this.distribution = distribution;
	}
	
	public double generateDt(boolean firstMeet) {
		return distribution.generateSample();
	}
}
