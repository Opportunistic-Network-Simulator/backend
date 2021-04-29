package com.project.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.project.service.SimulationService;
import com.project.simulator.entity.MeetingTrace;

public class SimulationTest {
	SimulationService simulationService = new SimulationService();
	
	@Test
	public void testSimulation() {
		List<com.project.simulator.entity.Pair> pairs = new ArrayList<com.project.simulator.entity.Pair>();
		pairs.add(new com.project.simulator.entity.Pair(0, 1, 2.5));
		pairs.add(new com.project.simulator.entity.Pair(0, 2, 2.5));
		pairs.add(new com.project.simulator.entity.Pair(0, 3, 2.5));
		pairs.add(new com.project.simulator.entity.Pair(1, 2, 2.5));
		pairs.add(new com.project.simulator.entity.Pair(1, 3, 2.5));
		pairs.add(new com.project.simulator.entity.Pair(2, 3, 2.5));
		MeetingTrace meetingTrace = this.simulationService.generateMeetingTrace(pairs, 3d);
		this.simulationService.executeSimulation(3, meetingTrace, 4);
	}
}
