package com.project.simulator.entity.distribution;

import com.project.exception.InvalidParametersException;

import java.util.Collections;
import java.util.List;

public class HyperExponential extends MixtureDistribution {

    private final List<Double> lambdas;
    private final List<Double> ps;

    public HyperExponential(List<Double> lambdas, List<Double> ps) {
        if (lambdas.size() != ps.size()) {
            throw new InvalidParametersException("Rate vector and probability vector should have the same size.");
        }

        for (double lambda : lambdas) {
            if (lambda <= 0) {
                throw new InvalidParametersException("Lambda should be strictly greater than 0. Lambda value: " + lambda);
            }
        }

        double p_sum = 0.0;
        for(double p : ps) {
            if(p <= 0 || p >= 1) {
                throw new InvalidParametersException("Probability values should be in the interval (0, 1). Probability value: " + p);
            }
            p_sum += p;
        }

        if (p_sum != 1) {
            throw new InvalidParametersException("Probability values should sum to 1. Sum: " + p_sum);
        }

        this.lambdas = lambdas;
        this.ps      = ps;
    }

    @Override
    public List<Double> chooseDistribution() {
        double u = Math.random();
        double newLambda = 0;
        for (int i = 0; i < lambdas.size(); i++) {
            if (u >= ps.get(i)) {
                newLambda = lambdas.get(i);
                break;
            }
        }
        return Collections.singletonList(newLambda);
    }

    @Override
    public double generateSample() {
        double u = Math.random();
        double newLambda = chooseDistribution().get(0);
        return -Math.log(1 - u) / newLambda;
    }
}
