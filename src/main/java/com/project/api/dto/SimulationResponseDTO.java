package com.project.api.dto;

import java.util.List;

import com.project.simulator.entity.event.MessageGenerationEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimulationResponseDTO {

	private MeetingTraceDTO meetingTrace;
	private List<MessageGenerationEvent> messages;
	
}
