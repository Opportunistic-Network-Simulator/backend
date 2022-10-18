package com.project.simulator.entity.distribution;

import java.util.List;

public abstract class MixtureDistribution implements Distribution {

    public abstract List<Double> chooseDistribution();
}
