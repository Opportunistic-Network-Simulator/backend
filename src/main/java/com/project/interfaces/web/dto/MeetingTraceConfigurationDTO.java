package com.project.interfaces.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingTraceConfigurationDTO {
	
	private String type;
	private double totalSimulationTime;
	private List<PairDTO> pairList;
	

}
