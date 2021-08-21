package com.project.simulator.entity;

import java.util.Map;
import java.util.TreeMap;

public class NodeGroup {
	private Map<Long, Node> nodes;
	
	public NodeGroup() {
		this.nodes = new TreeMap<Long, Node>();
	}

	public Node getNode(long nodeId) {
		if(!nodes.containsKey(nodeId)) {
			Node newNode = new Node(nodeId);
			this.nodes.put(nodeId, newNode);
		}
		return this.nodes.get(nodeId);
	}
	
	public long getSize() {
		return nodes.size();
	}
}
