package com.project.interfaces.commandLine.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CLIPairDTO {

	private long node1;
	private long node2;
	private double rate;
	boolean variableRate;
	private double variabilityDegree;
	
}
