package com.project.interfaces.commandLine;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.hibernate.loader.plan.exec.process.internal.AbstractRowReader;

import com.project.exception.SimulatorException;

public class ArgumentsHandler {
	
	public static Optional<String> handleArgs(String args[]) throws IOException {
		
		Options options = new Options();

        Option input = new Option("i", "input", true, "input file name (wihtout spaces)");
        input.setRequired(true);
        options.addOption(input);
        
        Option help = new Option("h", "help", false, "Displays the help");
        help.setRequired(false);
        options.addOption(help);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;//not a good practice, it serves it purpose 
        
        try {
            cmd = parser.parse(options, args);
            if(cmd.hasOption(help.getOpt())) {
            	formatter.printHelp("simulator", options);
            	return Optional.of(null);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("simulator", options);

            System.exit(1);
        }
                
        Optional<String> optionalFileName = verifyFileInput(cmd.getOptionValue(input.getOpt()));
        return optionalFileName;

	}
	
	private static Optional<String> verifyFileInput(String inputFileName) throws IOException {
	
		File inputFile = new File(inputFileName); //trying get realtive filename
		
		if(inputFile.exists()) {
			return Optional.of(inputFileName);
		}
		
		else {
			String dir = System.getProperty("user.dir");
			String absoluteInputFileName = dir + File.separator + inputFileName;
			inputFile = new File(absoluteInputFileName); //trying get absolute filename
		
			if(inputFile.exists()) {
				return Optional.of(absoluteInputFileName);
			} else {
				throw new SimulatorException("Could not find neither " + inputFileName + " or " + absoluteInputFileName);
			}
		}
	}

}
