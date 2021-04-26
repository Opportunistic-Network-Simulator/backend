package com.project.simulator.generator;

import java.util.ArrayList;
import java.util.List;

import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;

public class NodesGenerator {
	
	static public List<Node> nodes;
	
	public static void generateNodes(long nodesQuantity) {
		nodes = new ArrayList<Node>();
		for(int i = 1; i <= nodesQuantity; i++) {
			nodes.add(new Node(Long.valueOf(i), new ArrayList<Message>()));
		}
	}
}
