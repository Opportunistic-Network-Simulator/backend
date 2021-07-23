package com.project.simulator.entity;

import java.util.Map;
import java.util.TreeMap;

public class NodeGroup {
	private Map<Long, Node> nodes;
	
	public NodeGroup(long size) {
		this.nodes = new TreeMap<Long, Node>();
		for(long i = 0; i < size; i++) {
			Node node = new Node(i);
			nodes.put(node.getId(), node);
		}
	}

	public Node getNode(long nodeId) {
		if(!nodes.containsKey(nodeId)) {
			//throw new Exception("Node id does not exist in this group: " + nodeId);
		}
		return this.nodes.get(nodeId);
	}
	
	public long getSize() {
		return nodes.size();
	}
}
