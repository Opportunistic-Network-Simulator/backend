package com.project.interfaces.commandLine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CLIPairDTO {

	private long node1;
	private long node2;
	// Exponential
	private double lambda;
	// VariableExponential
	private double variability;
	// HyperExponential
	private List<Double> lambdas, ps;
	// Pareto
	private double mu, sigma, gamma, alpha;

	// Exponential
	CLIPairDTO(long node1, long node2, double lambda) {
		this.node1  = node1;
		this.node2  = node2;
		this.lambda = lambda;
	}

	// VariableExponential
	CLIPairDTO(long node1, long node2, double lambda, double variability) {
		this.node1       = node1;
		this.node2       = node2;
		this.lambda      = lambda;
		this.variability = variability;
	}

	// HyperExponential
	CLIPairDTO(long node1, long node2, List<Double> lambdas, List<Double> ps) {
		this.node1   = node1;
		this.node2   = node2;
		this.lambdas = lambdas;
		this.ps      = ps;
	}

	// Pareto
	CLIPairDTO(long node1, long node2, double mu, double sigma, double gamma, double alpha) {
		this.node1 = node1;
		this.node2 = node2;
		this.mu    = mu;
		this.sigma = sigma;
		this.gamma = gamma;
		this.alpha = alpha;
	}
}
