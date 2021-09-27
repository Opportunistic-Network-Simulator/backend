package com.project.interfaces.commandLine.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import com.project.simulator.entity.Meet;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.SimulationReport;

public class CommandLineReporter {
	private String id;
	FileNameManager fileNameManager;

	private CommandLineReporter(String id) {
		this.id = id;
		this.fileNameManager = FileNameManager.make(id);
	}

	public void reportSingleSimulation(SimulationReport report) {
		try {
			File outputFile = this.fileNameManager.getSummaryReportFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true));

			writer.write("\n\nNew simulation: \n");
			String reportMessage = "Delivery ratio: " + report.getDeliveryRatio()
					+ "\n" + "Average delay: " + report.getAverageDelay();
			writer.write(reportMessage);
			writer.close();
		} catch(Exception e) {

		}

	}

	public void reportMeetingTrace(MeetingTrace trace) {
		try {
			File outputFile = this.fileNameManager.getMeetingTraceReportFile();
			BufferedWriter writer = makeBufferedWriter(outputFile);

			writer.write(makeMeetingTraceHeader());
			trace.getMeetingTrace().sort((meet1, meet2) -> ((meet1.getInstant() > meet2.getInstant()) ? 1 : -1));

			for(Meet meet : trace.getMeetingTrace()) {
				writer.write(makeLineForMeetingTraceReport(meet));
			}
			writer.close();
		} catch(Exception e) {

		}
	}

	private String makeMeetingTraceHeader() {
		return "instant,node1,node2\n";
	}

	private String makeLineForMeetingTraceReport(Meet meet) {
		return String.valueOf(meet.getInstant()) + "," +
				String.valueOf(meet.getPair().getNode1()) + "," +
				String.valueOf(meet.getPair().getNode2()) + "\n";
	}

	private BufferedWriter makeBufferedWriter(File file) throws IOException {
		return new BufferedWriter(new FileWriter(file, true));
	}

	public static CommandLineReporter make(String id) {
		return new CommandLineReporter(id);
	}

	public static CommandLineReporter makeRoot() { return new CommandLineReporter(null); }

}
