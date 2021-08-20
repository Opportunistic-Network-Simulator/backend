package com.project;

import java.io.IOException;
import java.util.Optional;

import com.project.interfaces.commandLine.ArgumentsHandler;

public class CommandLineApplication {
	
	public static void main(String args[]) throws IOException {
		
		Optional<String> optionalFileName = ArgumentsHandler.handleArgs(args);
		System.out.println(optionalFileName.isPresent());
		
	}	
	

}
