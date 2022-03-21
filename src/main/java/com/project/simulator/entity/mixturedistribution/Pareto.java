package com.project.simulator.entity.mixturedistribution;

import com.project.exception.InvalidParametersException;

import java.util.ArrayList;
import java.util.List;

public class Pareto extends MixtureDistribution {
    private final double mu, sigma, gamma, alpha;

    public Pareto(double mu, double sigma, double gamma, double alpha) {
        super(DistributionType.PARETO);

        if (gamma > 0){
            this.gamma = gamma;
        } else {
            throw new InvalidParametersException("Gamma should be strictly greater than 0.");
        }

        this.mu = mu;
        this.sigma = sigma;
        this.alpha = alpha;
    }

    @Override
    List<Double> chooseDistribution() {
        return new ArrayList<Double>() {{
            add(mu);
            add(sigma);
            add(gamma);
            add(alpha);
        }};
    }

    @Override
    public double generateSample(){
        double u = Math.random();
        return mu + sigma*Math.pow((Math.pow(u, -1/alpha) - 1), sigma);
    }
}