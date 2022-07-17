package com.project.interfaces.commandLine.parser;

import java.io.File;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.project.exception.SimulatorException;

public class ArgumentsHandler {
	
	public static FileNamesParser handleArgs(String[] args) {
		
		Options options = new Options();

        Option input = new Option("i", "input", true, "input file name (just name with extension, without spaces or parent path)");
        input.setRequired(true);
        options.addOption(input);
        
        Option help = new Option("h", "help", false, "Displays the help");
        help.setRequired(false);
        options.addOption(help);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        
        try {
            cmd = parser.parse(options, args);
            if(cmd.hasOption(help.getOpt())) {
            	formatter.printHelp("simulator", options);
            	return new FileNamesParser(Optional.empty());
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("simulator", options);

            System.exit(1);
        }
        FileNamesParser fileNamesParser = new FileNamesParser();  
        fileNamesParser.setInputFile(verifyFileInput(fileNamesParser, cmd.getOptionValue(input.getOpt())));
        return fileNamesParser;

	}
	
	private static Optional<File> verifyFileInput(FileNamesParser fileNamesParser, String inputFileName) throws SimulatorException {
		String absoluteInputFileName = fileNamesParser.toAbsoluteInputConfigFile(inputFileName);
		File inputFile = new File(absoluteInputFileName);
		
		if(inputFile.exists()) {
			return Optional.of(inputFile);
		}
		else {
			throw new SimulatorException("Could not find " + absoluteInputFileName);
		}
		
	}

}
