package com.project.simulator.generator;

import com.project.simulator.configuration.MeetingTraceConfiguration;
import com.project.simulator.entity.Meet;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.Pair;

public class MeetingTraceGenerator {
	private static void generatePairMeetings(MeetingTrace meetingTrace, Pair pair, double totalSimulationTime) {
		double lastMeetInstant = 0.0;

		while(true) {
			double dt = pair.generateDt(lastMeetInstant == 0);
			lastMeetInstant = lastMeetInstant + dt;
			if(lastMeetInstant > totalSimulationTime)
				break;
			meetingTrace.addMeet(new Meet(pair, lastMeetInstant));
		}
	}

	public static MeetingTrace generate(MeetingTraceConfiguration config) {
		MeetingTrace meetingTrace = new MeetingTrace();
		for(Pair pair : config.getPairs()) {
			MeetingTraceGenerator.generatePairMeetings(meetingTrace, pair, config.getTotalSimulationTime());
		}
		return meetingTrace;
	}
}
