package com.project.simulator.configuration;

import com.project.simulator.entity.Pair;
import lombok.Getter;

import java.util.List;

@Getter
public class MeetingTraceConfiguration {
    MeetingTraceConfigurationType type;
    String fileName;
    double totalSimulationTime;
    List<Pair> pairs;

    public MeetingTraceConfiguration(MeetingTraceConfigurationType type, double totalSimulationTime, List<Pair> pairs) {
        this.type = type;
        this.totalSimulationTime = totalSimulationTime;
        this.pairs = pairs;
    }
}
