package com.project.simulator.generator;

import com.project.simulator.configuration.MeetingTraceConfiguration;
import com.project.simulator.entity.Meet;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.MeetingTraceGeneratorInput;
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
	
	public static MeetingTrace generateMeetingTrace(MeetingTraceGeneratorInput input) {
		MeetingTrace meetingTrace = new MeetingTrace();
		for(Pair pair : input.getPairs()) {
			if(pair.pairMeets()) MeetingTraceGenerator.generatePairMeetings(meetingTrace, pair, input.getTotalSimulationTime());
		}
		meetingTrace.orderTrace();
		return meetingTrace;
	}

	public static MeetingTrace generate(MeetingTraceConfiguration config) {
		switch (config.getType()) {
			case FILE:
				throw new IllegalStateException("Message generation type not implemented: file");
			case EXPONENTIAL:
				return generateMeetingTraceExponencial(config);
			default:
				throw new IllegalStateException("Message generation type not implemented: " + config.getType());
		}
	}

	public static MeetingTrace generateMeetingTraceExponencial(MeetingTraceConfiguration config) {
		MeetingTrace meetingTrace = new MeetingTrace();
		for(Pair pair : config.getPairs()) {
			if(pair.pairMeets()) MeetingTraceGenerator.generatePairMeetings(meetingTrace, pair, config.getTotalSimulationTime());
		}
		meetingTrace.orderTrace();
		return meetingTrace;
	}
}
