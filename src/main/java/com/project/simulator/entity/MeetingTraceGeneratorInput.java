package com.project.simulator.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingTraceGeneratorInput {
	private List<Pair> pairs;
	private double totalSimulationTime;
}
