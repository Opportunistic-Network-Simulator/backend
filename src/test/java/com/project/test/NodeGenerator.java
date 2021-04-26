package com.project.test;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
public class NodeGenerator {
	
	static public List<Node> nodes;
	
	public NodeGenerator(int nodesQuantity) {
		nodes = new ArrayList<Node>();
		for(int i = 0; i < nodesQuantity; i++) {
			nodes.add(new Node(i, false));
		}
	}

}
