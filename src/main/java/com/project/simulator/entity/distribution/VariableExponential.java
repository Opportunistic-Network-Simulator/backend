package com.project.simulator.entity.distribution;

import com.project.exception.InvalidParametersException;

import java.util.Collections;
import java.util.List;

public class VariableExponential extends MixtureDistribution {

    private final double lambda, minLambda, maxLambda;

    public VariableExponential(double lambda, double variability) {
        if(lambda > 0) {
            this.lambda = lambda;
        } else {
            throw new InvalidParametersException("Lambda should be strictly greater than 0. Lambda value: " + lambda);
        }

        if(variability < 0 || variability >= 1) {
            throw new InvalidParametersException("Variability should be in the interval [0, 1). Variability: " + variability);
        }

        minLambda = (1 - variability) * lambda;
        maxLambda = (1 + variability) * lambda;
    }

    @Override
    public List<Double> chooseDistribution() {
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
