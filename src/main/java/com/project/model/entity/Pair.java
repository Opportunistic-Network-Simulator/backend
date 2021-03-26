package com.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class Pair {
	
	private Integer node1;
	private Integer node2;
	private double rate; //defined in s^-1
	private boolean variableRate;
	private double variabilityDegree;
}
