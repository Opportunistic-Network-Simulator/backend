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

    public SimulationConfiguration(Integer numberOfRounds,
    							   ProtocolConfiguration protocolConfiguration,
    							   MeetingTraceConfiguration meetingTraceConfiguration,
    							   MessageGenerationConfiguration messageGenerationConfiguration){
    	
        this.messageGenerationConfiguration = messageGenerationConfiguration;
        this.meetingTraceConfiguration = meetingTraceConfiguration;
        this.protocolConfiguration = protocolConfiguration;
        this.numberOfRounds = (numberOfRounds == null || numberOfRounds == 0) ? 1 : numberOfRounds;
    }

	@Override
	public String toString() {
		return "SimulationConfiguration [messageGenerationConfiguration=" + messageGenerationConfiguration
				+ ", meetingTraceConfiguration=" + meetingTraceConfiguration + ", protocolConfiguration="
				+ protocolConfiguration + ", totalSimulationTime=" + totalSimulationTime + ", numberOfRounds="
				+ numberOfRounds + "]";
	}
    
    
}
