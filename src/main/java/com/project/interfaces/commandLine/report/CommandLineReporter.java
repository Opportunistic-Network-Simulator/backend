package com.project.interfaces.commandLine.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import com.project.interfaces.commandLine.parser.FileNamesParser;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.SimulationReport;
import org.apache.commons.cli.CommandLine;

public class CommandLineReporter {
	private FileNamesParser fileNamesParser;
	private static CommandLineReporter singleton;

	private CommandLineReporter(FileNamesParser fileNamesParser) {
		this.fileNamesParser = fileNamesParser;
	}
	
	public void report(FileNamesParser fileNamesParser, SimulationReport report) throws IOException {
		
		String reportMessage = "Delivery ratio: " + report.getDeliveryRatio()
								+ "\n" + "Average delay: " + report.getAverageDelay();
		File outputFile = fileNamesParser.outputReportFile();
	    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
	    writer.write(reportMessage);
	    writer.close();
	    System.out.println("Simulation Success!");
	    System.out.println("Report output wrote in file " + outputFile);
	}

	public void reportSingleSimulation(SimulationReport report) {
		try {
			File outputFile = this.fileNamesParser.outputReportFileAllSimulations();
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
			File outputFile = this.fileNamesParser.outputReportFileAllSimulations();
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true));

			writer.write("\n\nNew simulation: \n");

			writer.write("[");
			String reportMessage = "Delivery ratio: " + report.getDeliveryRatio()
					+ "\n" + "Average delay: " + report.getAverageDelay();
			writer.write(reportMessage);
			writer.write("[");
			writer.close();
		} catch(Exception e) {

		}
	}

	public static CommandLineReporter getReporter() {
		return CommandLineReporter.singleton;
	}

	public static void makeReporter(FileNamesParser fileNamesParser) {
		CommandLineReporter.singleton = new CommandLineReporter(fileNamesParser);
	}
}
