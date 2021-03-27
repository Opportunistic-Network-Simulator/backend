package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.MeetingTraceGeneratorInput;
import com.project.simulator.entity.Pair;
import com.project.simulator.generator.MeetingTraceGenerator;

	

@Service
public class SimulationService {	
	public MeetingTrace generateMeetingTrace(List<Pair> pairs, double totalSimulationTime) {
		return MeetingTraceGenerator.generateMeetingTrace(new MeetingTraceGeneratorInput(pairs, totalSimulationTime));
	}	
}
