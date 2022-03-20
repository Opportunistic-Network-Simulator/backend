package com.project.simulator.entity.mixturedistribution;

import com.project.exception.InvalidParametersException;

import java.util.Collections;
import java.util.List;

public class Exponential extends MixtureDistribution {

    private final double lambda;
    private final double minLambda, maxLambda;

    public Exponential(double lambda, double variability) {
        super(DistributionType.EXPONENTIAL);

        if(lambda > 0) {
            this.lambda = lambda;
        } else {
            throw new InvalidParametersException("Rate should be strictly greater than 0.");
        }

        if(variability < 0 || variability >= 1) {
            throw new InvalidParametersException("Variability should be in the interval [0, 1).");
        }

        minLambda = (1 - variability) * lambda;
        maxLambda = (1 + variability) * lambda;
    }

    @Override
    List<Double> chooseDistribution() {
        Double newLambda = minLambda + Math.random() * (maxLambda - minLambda);
        return Collections.singletonList(newLambda);
    }

    @Override
    public double generateSample() {
        double u = Math.random();
        double newLambda = chooseDistribution().get(0);

        return -Math.log(1 - u) / newLambda;
    }

}
