package com.project.test.interfaces;

import com.project.interfaces.commandLine.parser.FileParser;
import com.project.simulator.configuration.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileParserTest {

//    @Test
    public void parseTest() throws FileNotFoundException, IOException, ParseException {
        SimulationConfiguration config = FileParser.parseConfig(new File("/home/bernardo/Documents/PFC/tests/configTest.toml"));
        Assertions.assertEquals(5, config.getNumberOfRounds());

        MessageGenerationConfiguration messageConfig = config.getMessageGenerationConfiguration();
        Assertions.assertEquals(MessageGenerationType.ALL_PAIRS, messageConfig.getType());
        Assertions.assertEquals(0d, messageConfig.getGenerationInstant());
        Assertions.assertEquals(15, messageConfig.getAmountNodes());

        ProtocolConfiguration protocolConfig = config.getProtocolConfiguration();
        Assertions.assertEquals(ProtocolType.BINARY_SPRAY_AND_WAIT, protocolConfig.getType());
        Assertions.assertEquals(5, protocolConfig.getL());

        MeetingTraceConfiguration meetingTraceConfig = config.getMeetingTraceConfiguration();
        Assertions.assertEquals(MeetingTraceConfigurationType.EXPONENTIAL, meetingTraceConfig.getType());
        Assertions.assertEquals(1800d, meetingTraceConfig.getTotalSimulationTime());
    }
}
