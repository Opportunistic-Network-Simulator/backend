package com.project.simulator.simulation.protocols;

import com.project.simulator.entity.MessageCopy;
import com.project.simulator.entity.Node;
import com.project.simulator.exception.ValueInputException;

import java.util.ArrayList;
import java.util.List;

public class KCopyEpidemicProtocol extends MessageTransmissionProtocol{

    private long K;

    public KCopyEpidemicProtocol(Long K){
        if(K > 0) {
            this.K = K;
        } else {
            throw new ValueInputException("K value should be greater than 0. Provided value: " + K);
        }
    }

    @Override
    protected boolean shouldTransfer(Node fromNode, Node toNode, MessageCopy message) {
        if (fromNode.getId() != message.getSourceNode()) {
            return message.getTransferCount() == 0;
        }
        else return message.getTransferCount() + message.getNumCopies() < this.K;
    }

    protected void postTransfer(MessageCopy message, Node fromNode, Node toNode) {
        if (fromNode.getId() != message.getSourceNode() | message.getNumCopies() > this.K) {
            fromNode.removeMessage(message.getId());
            message.purge();
        }
    }
}
