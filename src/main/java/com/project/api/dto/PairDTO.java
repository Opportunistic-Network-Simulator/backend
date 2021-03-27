package com.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PairDTO {

	private int node1;
	private int node2;
	private double rate;
	private double variabilityDegree;
	
}