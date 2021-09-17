package com.project.simulator.configuration;

import com.project.simulator.entity.Pair;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MeetingTraceConfiguration {
    MeetingTraceConfigurationType type;
    String filename;
    double totalSimulationTime;
    List<Pair> pairs;

    public MeetingTraceConfiguration() {}

    public MeetingTraceConfiguration(MeetingTraceConfigurationType type, double totalSimulationTime, List<Pair> pairs) {
        this.type = type;
        this.totalSimulationTime = totalSimulationTime;
        this.pairs = pairs;
    }

    public MeetingTraceConfiguration(MeetingTraceConfigurationType type, double totalSimulationTime, String filename) {
        this.type = type;
        this.totalSimulationTime = totalSimulationTime;
        this.filename = filename;
    }

	@Override
	public String toString() {
		return "MeetingTraceConfiguration [type=" + type + ", totalSimulationTime=" + totalSimulationTime + "]";
	}
    
    
}
