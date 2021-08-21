package com.project;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.project.interfaces.commandLine.parser.ArgumentsHandler;
import com.project.interfaces.commandLine.parser.FileParser;
import com.project.interfaces.commandLine.report.Reporter;
import com.project.simulator.SimulationProcessor;
import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.SimulationReport;

public class CommandLineApplication {
	
	public static void main(String args[]) throws IOException, ParseException {
		
		try {
			Optional<File> optionalFileName = ArgumentsHandler.handleArgs(args);
			if(!optionalFileName.isPresent()) return;
				
			SimulationConfiguration config = FileParser.parseConfig(optionalFileName.get());
			SimulationProcessor processor = new SimulationProcessor(config);
		    SimulationReport report = processor.runSimulation();
		    
		    Reporter.report(report);
		}
		catch (Exception e) {
			System.out.println(e.getMessage() == null ? "Internal Error" : e.getMessage());
		}
		
		
	}	
	

}
