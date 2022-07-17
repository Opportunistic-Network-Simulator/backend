package com.project.interfaces.commandLine.parser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileNamesParser {
	
	private Optional<File> inputFile;
	
	public FileNamesParser(Optional<File> optionalInputFile) {
		this.inputFile = optionalInputFile;
	}
	
	private String localPath() {
		String dir = System.getProperty("user.dir");
		return dir + File.separator;
	}
	
	private String absolutInputPath() {
		return localPath() + "input" + File.separator;
	}
	
	public String toAbsoluteInputConfigFile(String configFileName) {
		return absolutInputPath() + configFileName;
	}
	
	public String toAbsoluteInputPairDefinitionFile(String pairDefinitionFile) {
		return absolutInputPath() + "pairDefinitionFile" + File.separator + pairDefinitionFile;
	}

	public File outputReportFileAllSimulations() throws IOException {
		File outputFile = new File(localPath() + "output" + File.separator + "simulationsResults.txt");
		outputFile.getParentFile().mkdirs();
		if(!outputFile.exists())
			outputFile.createNewFile();
		return outputFile;
	}

	public File outputReportFileMeetingTraces() throws IOException {
		File outputFile = new File(localPath() + "output" + File.separator + "meetingTraces.txt");
		outputFile.getParentFile().mkdirs();
		if(!outputFile.exists())
			outputFile.createNewFile();
		return outputFile;
	}

}
