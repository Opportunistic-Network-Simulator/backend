package com.project.simulator.configuration;

import lombok.Getter;

@Getter
public class ProtocolConfiguration {
    ProtocolType type;
    double p;
    double q;
    int l;

    public ProtocolConfiguration(ProtocolType type, double p, double q) {
        this.type = type;
        this.p = p;
        this.q = q;
    }

    public ProtocolConfiguration(ProtocolType type, int l) {
        this.type = type;
        this.l = l;
    }

    public ProtocolConfiguration(ProtocolType type) {
        this.type = type;
    }
}
