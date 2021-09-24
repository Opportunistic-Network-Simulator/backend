package com.project;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.project.interfaces.commandLine.parser.ArgumentsHandler;
import com.project.interfaces.commandLine.parser.FileNamesParser;
import com.project.interfaces.commandLine.parser.FileParser;
import com.project.interfaces.commandLine.report.CommandLineReporter;
import com.project.simulator.SimulationProcessor;
import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.SimulationReport;

public class CommandLineApplication {
	
	public static void main(String args[]) throws IOException, ParseException {
		
		try {
			FileNamesParser fileNamesParser = ArgumentsHandler.handleArgs(args);
			if(!fileNamesParser.getInputFile().isPresent()) return;

			CommandLineReporter.makeReporter(fileNamesParser);
				
			SimulationConfiguration config = FileParser.parseConfig(fileNamesParser);
			SimulationProcessor processor = new SimulationProcessor(config);
			System.out.println("Simulation started");
		    SimulationReport report = processor.runSimulation();
		    
		    CommandLineReporter reporter = CommandLineReporter.getReporter();
		    reporter.report(fileNamesParser, report);
		}
		catch (Exception e) {
			System.out.println(e.getMessage() == null ? "Internal Error" : e.getMessage());
		}
		
		
	}	
	

}
