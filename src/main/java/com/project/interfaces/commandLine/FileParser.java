package com.project.interfaces.commandLine;

import com.moandjiezana.toml.Toml;
import com.project.simulator.configuration.*;

import java.io.File;

public class FileParser {
    public static SimulationConfiguration parseConfig(String filename) {
        File file = new File(filename);
        Toml parser = new Toml().read(file);

        SimulationConfiguration config = new SimulationConfiguration();

        config.setMessageGenerationConfiguration(parseMessageGenerationConfig(parser.getTable("messageGeneration")));
        config.setMeetingTraceConfiguration(parseMeetingTraceConfig(parser.getTable("meetingTrace")));
        config.setProtocolConfiguration(parseProtocolConfig(parser.getTable("protocol")));

        Long numberOfRounds = parser.getLong("numberOfRounds");

        if(numberOfRounds != null) {
            config.setNumberOfRounds(numberOfRounds.intValue());
        }

        return config;
    }

    private static MessageGenerationConfiguration parseMessageGenerationConfig(Toml parser) {
        MessageGenerationConfiguration config = new MessageGenerationConfiguration();

        MessageGenerationType type = MessageGenerationType.valueOf(parser.getString("type"));
        config.setType(type);

        String filename = parser.getString("filename");
        if (filename != null) {
            config.setFileName(filename);
        }

        Double generationInstant = parser.getDouble("generationInstant");
        if (generationInstant != null) {
            config.setGenerationInstant(generationInstant);
        } else {
            config.setGenerationInstant(0);
        }

        Long amountNodes = parser.getLong("amountNodes");
        if (amountNodes != null) {
            config.setAmountNodes(amountNodes.intValue());
        }

        Long sourceNodeId = parser.getLong("sourceNodeId");
        if (sourceNodeId != null) {
            config.setSourceNodeId(sourceNodeId.intValue());
        }

        Long destinationNodeId = parser.getLong("destinationNodeId");
        if (destinationNodeId != null) {
            config.setDestinationNodeId(destinationNodeId.intValue());
        }

        return config;
    }

    private static MeetingTraceConfiguration parseMeetingTraceConfig(Toml parser) {
        MeetingTraceConfigurationType type = MeetingTraceConfigurationType.valueOf(parser.getString("type"));
        String filename = parser.getString("filename");
        Double totalSimulationTime = parser.getDouble("totalSimulationTime");

        return new MeetingTraceConfiguration(
            MeetingTraceConfigurationType.valueOf(parser.getString("type")),
            parser.getDouble("totalSimulationTime"),
            parser.getString("filename")
        );
    }

    private static ProtocolConfiguration parseProtocolConfig(Toml parser) {
        ProtocolConfiguration config = new ProtocolConfiguration();

        ProtocolType type = ProtocolType.valueOf(parser.getString("type"));
        config.setType(type);

        Double p = parser.getDouble("p");
        if (p != null) {
            config.setP(p);
        }

        Double q = parser.getDouble("q");
        if (q != null) {
            config.setQ(q);
        }

        Long l = parser.getLong("l");
        if (l != null) {
            config.setL(l.intValue());
        }

        return config;
    }
}
