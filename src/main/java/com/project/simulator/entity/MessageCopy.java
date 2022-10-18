package com.project.simulator.entity;

import com.project.interfaces.commandLine.report.CommandLineReporter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageCopy {

    private Message parent;
    private long hopCount;
    private long transferCount;

    public MessageCopy(Message parent){
        this.parent = parent;
        transferCount = 0;
        hopCount = 0;
    }

    public boolean canHop(long toNode) {
        if (toNode != parent.getDestinationNode())
            return hopCount < parent.getHopLimit() - 1;
        else
            return true;
    }

    public void notifyNewNode(long newNodeId, double instant) {
        this.transferCount = 0;
        parent.notifyNewNode(newNodeId, instant);
    }

    public long getDestinationNode() {
        return parent.getDestinationNode();
    }
    public long getSourceNode() {
        return parent.getSourceNode();
    }

    public CommandLineReporter getReporter() {
        return parent.getReporter();
    }

    public int getNumCopies() {
        return parent.getChildren().size();
    }

    public long getId() {
        return parent.getId();
    }

    public MessageCopy createCopy(){
        return parent.createCopy(this);
    }

    public String getStoredValue(String key) {
        return parent.getStoredProperties().get(key);
    }

    public boolean hasStoredElement(String key) {
        return parent.getStoredProperties().containsKey(key);
    }

    public void storeValue(String key, String value) {
        parent.getStoredProperties().put(key, value);
    }

    public void purge() { this.parent.removeChild(this); }

    public void incrementTransferCount() { this.transferCount++; }
}
