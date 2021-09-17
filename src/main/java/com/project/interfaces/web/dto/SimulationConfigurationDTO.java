package com.project.interfaces.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimulationConfigurationDTO {
	
	int numberOfRounds;
	private ProtocolConfigurationDTO proConfigurationDTO;
	private MeetingTraceConfigurationDTO meetingTraceConfigurationDTO;
	private MessageGenerationConfigurationDTO MessageConfigurationDTO;	

}
