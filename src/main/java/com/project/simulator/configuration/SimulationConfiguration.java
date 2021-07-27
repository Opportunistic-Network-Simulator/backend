package com.project.simulator.configuration;

import lombok.Getter;

@Getter
public class SimulationConfiguration {
    MessageGenerationConfiguration messageGenerationConfiguration;
    MeetingTraceConfiguration meetingTraceConfiguration;
    ProtocolConfiguration protocolConfiguration;
    OutputConfiguration outputConfiguration;
    double totalSimulationTime;
    int numberOfRounds;

    public SimulationConfiguration(MessageGenerationConfiguration messageGenerationConfiguration,
                                   MeetingTraceConfiguration meetingTraceConfiguration,
                                   ProtocolConfiguration protocolConfiguration) {
        this.messageGenerationConfiguration = messageGenerationConfiguration;
        this.meetingTraceConfiguration = meetingTraceConfiguration;
        this.protocolConfiguration = protocolConfiguration;
    }
}
