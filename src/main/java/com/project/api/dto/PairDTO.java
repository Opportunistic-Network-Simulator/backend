package com.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PairDTO {

	private long node1;
	private long node2;
	private double rate;
	private double variabilityDegree;
	
	public PairDTO(long node1, long node2){
		this.node1 = node1;
		this.node2 = node2;
	}
	
}