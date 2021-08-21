package com.project.interfaces.commandLine.parser;

import java.io.File;
import java.io.IOException;

public class FileNamesParser {
	
	private static String localPath() {
		String dir = System.getProperty("user.dir");
		String localPath = dir + File.separator;
		return localPath;
	}
	
	private static String absolutInputPath() {
		return localPath() + "input" + File.separator;
	}
	
	public static String toAbsoluteInputConfigFile(String configFileName) {
		return absolutInputPath() + configFileName;
	}
	
	public static String toAbsoluteInputPairDefinitionFile(String pairDefinitionFile) {
		return absolutInputPath() + "pairDefinitionFile" + File.separator + pairDefinitionFile;
	}
	
	public static File outputReportFile() throws IOException {
		File outputFile = new File(localPath() + "output" + File.separator + "report.txt");
		outputFile.getParentFile().mkdirs();
		outputFile.createNewFile();
		return outputFile;
	}

}
