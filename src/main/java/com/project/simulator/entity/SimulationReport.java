package com.project.simulator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimulationReport {
    private double averageDelay;
    private double deliveryRatio;
    private double variance;
}
