package com.project.interfaces.commandLine.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import com.project.simulator.entity.Meet;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.Message;
import com.project.simulator.entity.SimulationReport;

public class CommandLineReporter {
	FileNameManager fileNameManager;

	private CommandLineReporter(String id) {
		this.fileNameManager = FileNameManager.make(id);
	}


	public void reportSimulationSummary(SimulationReport report) {
		try {
			File outputFile = this.fileNameManager.getSummaryReportFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true));

			String reportMessage = "Delivery ratio: " + report.getDeliveryRatio()
					+ "\n" + "Average delay: " + report.getAverageDelay()
					+ "\n" + "Variance: " + report.getVariance()
					+ "\n" + "Standard deviation: " + report.getStdDeviation();
			writer.write(reportMessage);
			writer.close();
		} catch(Exception e) {

		}
	}

	public void reportMessageCreated(Message message) {
		try {
			File file = this.fileNameManager.getMessageReportFile();
			BufferedWriter writer = makeBufferedWriter(file);
			writer.write(String.valueOf(message.getGenarationInstant()) + ","
					+ String.valueOf(message.getId()) + ",GENERATE,"
					+ String.valueOf(message.getSourceNode()) + ","
					+ String.valueOf(message.getDestinationNode()) + "\n");
			writer.close();
		} catch (Exception e) {

		}
	}

	public void reportMessageTransmitted(long id, double instant, long from, long to) {
		try {
			File file = this.fileNameManager.getMessageReportFile();
			BufferedWriter writer = makeBufferedWriter(file);
			writer.write(instant + ","
					+ id + ",TRANSMIT,"
					+ from + ","
					+ to + "\n");
			writer.close();
		} catch (Exception e) {

		}
	}

	public void reportMessageArrived(long id, double instant) {
		try {
			File file = this.fileNameManager.getMessageReportFile();
			BufferedWriter writer = makeBufferedWriter(file);
			writer.write(instant + ","
					+ id + ",ARRIVAL,,\n");
			writer.close();
		} catch (Exception e) {

		}
	}

	public void writeMessageReportHeader() {
		try {
			File file = this.fileNameManager.getMessageReportFile();
			BufferedWriter writer = makeBufferedWriter(file);
			writer.write(this.makeMessageReportHeader());
			writer.close();
		} catch (Exception e) {

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

	private String makeMessageReportHeader() {
		return "instant,messageId,eventType,sourceNodeId,destinationNodeId\n";
	}

	private String makeMeetingTraceHeader() {
		return "instant,node1,node2\n";
	}

	private String makeLineForMeetingTraceReport(Meet meet) {
		return meet.getInstant() + "," + meet.getPair().getNode1() + "," +
				meet.getPair().getNode2() + "\n";
	}

	public void reportMessageDelivery(List<Message> messages) {
		try {
			File outputFile = this.fileNameManager.getMessageDeliveryReportFile();
			BufferedWriter writer = makeBufferedWriter(outputFile);

			writer.write(makeMessageDeliveryHeader());
			messages.sort((message1, message2) -> ((message1.getId() > message2.getId()) ? 1 : -1));

			for(Message message : messages) {
				writer.write(makeLineForMessageDeliveryReport(message));
			}
			writer.close();
		} catch(Exception e) {

		}

	}

	private String makeMessageDeliveryHeader() {
		return "messageId,deliveryStatus,sourceNodeId,destinationNodeId,createTime,deliveryTime\n";
	}

	private String makeLineForMessageDeliveryReport(Message message) {
		return message.getId()+","+(message.isDelivered() ? "DELIVERED" : "NOT_DELIVERED")+
				","+message.getSourceNode()+","+message.getDestinationNode()+","+message.getGenarationInstant()+","+
				(message.isDelivered() ? message.getArrivalInstant() : "-") + "\n";
	}

	private BufferedWriter makeBufferedWriter(File file) throws IOException {
		return new BufferedWriter(new FileWriter(file, true));
	}

	public FileNameManager getFileNameManager() {
		return this.fileNameManager;
	}

	public static CommandLineReporter make(String id) {
		return new CommandLineReporter(id);
	}

	public static CommandLineReporter makeRoot() { return new CommandLineReporter(null); }

}
