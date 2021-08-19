package com.project.simulator.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimulationConfiguration {
    MessageGenerationConfiguration messageGenerationConfiguration;
    MeetingTraceConfiguration meetingTraceConfiguration;
    ProtocolConfiguration protocolConfiguration;
    OutputConfiguration outputConfiguration;
    double totalSimulationTime;
    int numberOfRounds = 1;

    public SimulationConfiguration() {};

    public SimulationConfiguration(MessageGenerationConfiguration messageGenerationConfiguration,
                                   MeetingTraceConfiguration meetingTraceConfiguration,
                                   ProtocolConfiguration protocolConfiguration,
                                   Integer numberOfRounds) {
        this.messageGenerationConfiguration = messageGenerationConfiguration;
        this.meetingTraceConfiguration = meetingTraceConfiguration;
        this.protocolConfiguration = protocolConfiguration;
        this.numberOfRounds = (numberOfRounds == null || numberOfRounds == 0) ? 1 : numberOfRounds;
    }
}
