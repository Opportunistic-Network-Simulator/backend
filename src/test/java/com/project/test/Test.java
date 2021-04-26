package com.project.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.project.simulator.entity.Meet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Test {
	
	private Node node;
	
	public static void main(String[] args) throws IOException{
		
		NodeGenerator nodeGenerator = new NodeGenerator(3);
		List<Node> nodes = nodeGenerator.nodes;
		Pair pair01 = new Pair(nodes.get(0), nodes.get(1));
		Pair pair02 = new Pair(nodes.get(0), nodes.get(2));
		Node node0 = pair01.getNode1();
		System.out.println("NodeGen: " + nodes.get(0).isMessage());
		System.out.println("pair01: " + pair01.getNode1().isMessage());
		System.out.println("pair02: " + pair02.getNode1().isMessage());
		node0.setMessage(true);
		System.out.println("NodeGen: " + nodes.get(0).isMessage());
		System.out.println("pair01: " + pair01.getNode1().isMessage());
		System.out.println("pair02: " + pair02.getNode1().isMessage());
	
	}
}


