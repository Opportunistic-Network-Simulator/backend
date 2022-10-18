package com.project.simulator.entity.distribution;

import com.project.exception.InvalidParametersException;

public class Exponential implements Distribution {

    private final double lambda;

    public Exponential(double lambda) {
        if (lambda > 0) {
            this.lambda = lambda;
        } else {
           throw new InvalidParametersException("Lambda should be strictly greater than 0. Lambda selected: " + lambda);
        }
    }

    @Override
    public double generateSample() {
        double u = Math.random();
        return -Math.log(1 - u) / lambda;
    }
}
