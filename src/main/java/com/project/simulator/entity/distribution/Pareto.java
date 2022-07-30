package com.project.simulator.entity.distribution;

import com.project.exception.InvalidParametersException;

public class Pareto implements Distribution {

    private final double mu, sigma, gamma, alpha;

    public Pareto(double mu, double sigma, double gamma, double alpha) {
        if (gamma > 0) {
            this.gamma = gamma;
        } else {
            throw new InvalidParametersException("Gamma should be strictly greater than 0. Gamma selected: " + gamma);
        }
        this.mu    = mu;
        this.sigma = sigma;
        this.alpha = alpha;
    }

    @Override
    public double generateSample() {
        double u = Math.random();
        return mu + sigma * Math.pow((Math.pow(u, -1/alpha) - 1), sigma);
    }
}
