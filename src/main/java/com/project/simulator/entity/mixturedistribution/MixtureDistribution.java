package com.project.simulator.entity.mixturedistribution;

import java.util.List;

public abstract class MixtureDistribution {

    // Maybe unnecessary
    private final DistributionType type;

    MixtureDistribution(DistributionType type) { this.type = type; }

    abstract List<Double> chooseDistribution();

    public abstract double generateSample();

}
