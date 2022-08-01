package com.project.simulator.generator;

import com.project.exception.InvalidParametersException;
import com.project.interfaces.commandLine.parser.FileNamesParser;
import com.project.simulator.configuration.MeetingTraceConfiguration;
import com.project.simulator.configuration.MeetingTraceConfigurationType;
import com.project.simulator.entity.Meet;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

		if (config.getType() == MeetingTraceConfigurationType.FILE) {
			String fileName = config.getFilename();
			FileNamesParser fileNamesParser = new FileNamesParser();
			String filePath = fileNamesParser.toAbsoluteMeetingTraceFile(fileName);

			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				String line;
				while ((line = br.readLine()) != null) {
					// TODO: fix the regex
//					if (!line.matches("(\\d+)(\\.\\d+)* \\d+ \\d+")) {
//						throw new InvalidParametersException("Invalid line in meeting trace file. Line: " + line);
//					}
					double instant = Double.parseDouble(line.split(" ")[0]);
					long nodeId1 = Long.parseLong(line.split(" ")[1]);
					long nodeId2 = Long.parseLong(line.split(" ")[2]);

					Pair pair = new Pair(Long.min(nodeId1, nodeId2), Long.max(nodeId1, nodeId2), null);
					meetingTrace.addMeet(new Meet(pair, instant));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			for(Pair pair : config.getPairs()) {
				MeetingTraceGenerator.generatePairMeetings(meetingTrace, pair, config.getTotalSimulationTime());
			}
		}

		return meetingTrace;
	}
}
