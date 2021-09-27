package com.project.interfaces.commandLine.report;

import java.io.File;
import java.io.IOException;

public class FileNameManager {
    String subdir;

    private FileNameManager(String subdir) {
        this.subdir = subdir;
    }

    public File getMeetingTraceReportFile() throws IOException {
        return getOutputFile("meetingTrace.csv");
    }

    public File getMessageReportFile() throws IOException {
        return getOutputFile("messageReport.csv");
    }

    public File getSummaryReportFile() throws IOException {
        return getOutputFile("summary.txt");
    }

    private File getOutputFile(String filename) throws IOException {
        File outputFile = new File(getPath(filename));
        outputFile.getParentFile().mkdirs();
        outputFile.createNewFile();
        return outputFile;
    }

    private File getOutputFile(String filename, String subdir) throws IOException {
        File outputFile = new File(localPath() + "output" + File.separator + subdir + File.separator + filename + ".txt");
        outputFile.getParentFile().mkdirs();
        if (!outputFile.exists())
            outputFile.createNewFile();

        return outputFile;
    }

    private String localPath() {
        String dir = System.getProperty("user.dir");
        String localPath = dir + File.separator;
        return localPath;
    }

    private String getPath(String filename) {
        if (this.subdir != null)
            return localPath() + "output" + File.separator + this.subdir + File.separator + filename;

        return localPath() + "output" + File.separator + filename;
    }

    public static FileNameManager makeRoot() {
        return new FileNameManager(null);
    }

    public static FileNameManager make(String subdir) {
        return new FileNameManager(subdir);
    }
}
