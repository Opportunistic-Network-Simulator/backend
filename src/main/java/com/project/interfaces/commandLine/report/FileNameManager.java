package com.project.interfaces.commandLine.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileNameManager {
    String subDirectory;

    private FileNameManager(String subDirectory) {
        this.subDirectory = subDirectory;
    }

    public File getMeetingTraceReportFile() throws IOException {
        return getOutputFile("meetingTrace.csv");
    }

    public File getMessageDeliveryReportFile() throws IOException {
        return getOutputFile("messageDelivery.csv");
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

    private File getOutputFile(String filename, String subDirectory) throws IOException {
        File outputFile = new File(localPath() + "output" + File.separator + subDirectory + File.separator + filename + ".txt");
        outputFile.getParentFile().mkdirs();
        if (!outputFile.exists())
            outputFile.createNewFile();

        return outputFile;
    }

    private String localPath() {
        String dir = System.getProperty("user.dir");
        return dir + File.separator;
    }

    private String getPath(String filename) {
        if (this.subDirectory != null)
            return localPath() + "output" + File.separator + this.subDirectory + File.separator + filename;

        return localPath() + "output" + File.separator + filename;
    }

    public static FileNameManager makeRoot() {
        return new FileNameManager(null);
    }

    public static FileNameManager make(String subDirectory) {
        return new FileNameManager(subDirectory);
    }

    public List<File> getAllReportFiles() {
        List<File> files = new ArrayList<>();

        File rootFolder = new File(this.getPath(""));

        for(File file : Objects.requireNonNull(rootFolder.listFiles())) {
            if(file.isDirectory()) {
                files.addAll(Arrays.asList(Objects.requireNonNull(file.listFiles())));
            } else {
                files.add(file);
            }
        }

        return files;
    }

    public File getRootFolder() {
        return new File(this.getPath(""));
    }
}
