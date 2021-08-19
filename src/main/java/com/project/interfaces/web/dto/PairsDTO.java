package com.project.interfaces.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PairsDTO {

	private List<PairDTO> pairsList;
	private long numberOfNodes;
	private double totalSimulationTime;
	private boolean stopOnEndOfArrivals;
	
}