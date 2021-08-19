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
        return new MessageGenerationConfiguration(
                MessageGenerationType.valueOf(parser.getString("type")),
                parser.getDouble("generationInstant"),
                parser.getLong("amountNodes").intValue()
            );
    }

    private static MeetingTraceConfiguration parseMeetingTraceConfig(Toml parser) {
        return new MeetingTraceConfiguration(
                MeetingTraceConfigurationType.valueOf(parser.getString("type")),
                parser.getDouble("totalSimulationTime"),
                parser.getString("filename")
        );
    }

    private static ProtocolConfiguration parseProtocolConfig(Toml parser) {
        return new ProtocolConfiguration(
                ProtocolType.valueOf(parser.getString("type")),
                parser.getLong("l").intValue()
        );
    }
}
