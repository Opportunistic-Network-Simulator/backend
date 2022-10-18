package com.project;

import com.project.interfaces.commandLine.parser.ArgumentsHandler;
import com.project.interfaces.commandLine.parser.FileNamesParser;
import com.project.interfaces.commandLine.parser.FileParser;
import com.project.simulator.SimulationProcessor;
import com.project.simulator.configuration.SimulationConfiguration;

public class CommandLineApplication {
	
	public static void main(String[] args) {
		
		try {
			FileNamesParser fileNamesParser = ArgumentsHandler.handleArgs(args);
			if(!fileNamesParser.getInputFile().isPresent()) return;

			SimulationConfiguration config = FileParser.parseConfig(fileNamesParser);
			String key = String.valueOf(System.currentTimeMillis());
			SimulationProcessor processor = new SimulationProcessor(config, key);
			System.out.println("Simulation started");
		    processor.runSimulation();
		    System.out.println("Simulation Completed. Report was generated in " + key + " folder, inside output folder");
		}
		catch (Exception e) {
			System.out.println(e.getMessage() == null ? "Internal Error" : e.getMessage());
		}
		
		
	}	

}
