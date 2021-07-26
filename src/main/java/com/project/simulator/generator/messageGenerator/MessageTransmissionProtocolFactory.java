package com.project.simulator.generator.messageGenerator;

import com.project.simulator.configuration.ProtocolConfiguration;
import com.project.simulator.simulation.protocols.*;

public class MessageTransmissionProtocolFactory {
    public static MessageTransmissionProtocol make(ProtocolConfiguration config) {
        switch (config.getType()) {
            case DIRECT_DELIVERY:
                return new DirectDeliveryProtocol();
            case PROPHET:
                throw new IllegalStateException("Message generation type not implemented: PROPHET");
            case TWO_HOP:
                return new TwoHopProtocol();
            case EPIDEMIC:
                return new EpidemicProtocol();
            case EPIDEMIC_P_Q:
                return new EpidemicPQProtocol(config.getP(), config.getQ());
            case SPRAY_AND_WAIT:
                return new SprayAndWaitProtocol(config.getL());
            case SINGLE_COPY_EPIDEMIC:
                return new SingleCopyEpidemicProtocol();
            default:
                throw new IllegalStateException("Message generation type not implemented: " + config.getType());
        }
    }
}
