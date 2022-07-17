package com.project.simulator.entity;

import com.project.interfaces.commandLine.report.CommandLineReporter;

import java.util.Map;
import java.util.TreeMap;

public class NodeGroup {
	private final Map<Long, Node> nodes;
	private CommandLineReporter reporter;

	public NodeGroup(CommandLineReporter reporter) {
		this.nodes = new TreeMap<>();
		this.reporter = reporter;
	}

	public NodeGroup() {
		this.nodes = new TreeMap<>();
	}

	public Node getNode(long nodeId) {
		if(!nodes.containsKey(nodeId)) {
			Node newNode = new Node(nodeId, this.reporter);
			this.nodes.put(nodeId, newNode);
		}
		return this.nodes.get(nodeId);
	}
	
	public long getSize() {
		return nodes.size();
	}
}
