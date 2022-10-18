package com.project.simulator.configuration;

import lombok.Getter;
import lombok.Setter;

import static com.project.simulator.configuration.ProtocolType.K_COPY;

@Getter
@Setter
public class ProtocolConfiguration {

	ProtocolType type;
    double p;
    double q;
    int l;
    long K;

    double pInit, gamma, beta;

    public ProtocolConfiguration() {}

    public ProtocolConfiguration(ProtocolType type, double p, double q) {
        this.type = type;
        this.p = p;
        this.q = q;
    }

    public ProtocolConfiguration(ProtocolType type, int l) {
        this.type = type;
        this.l = l;
    }

    public ProtocolConfiguration(ProtocolType type, long K) {
        this.type = type;
        this.K = K;
    }

    public ProtocolConfiguration(ProtocolType type, double pInit, double gamma, double beta) {
        this.type = type;
        this.pInit = pInit;
        this.gamma = gamma;
        this.beta = beta;
    }

    public ProtocolConfiguration(ProtocolType type) {
        this.type = type;
    }
}
