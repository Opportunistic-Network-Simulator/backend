package com.project.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.project.simulator.entity.Meet;
import com.project.simulator.entity.Pair;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Test {
	
	private Node node;
	
	public static void main(String[] args) throws IOException{
		Node node = Node.builder().id(0).message(false).build();
		
		List<Node> nodes = new ArrayList<>();
		nodes.add(node);
		System.out.println(nodes.get(0).isMessage());
		Node node2 = new Node(nodes.get(0)); 
		node2.setMessage(true);
		System.out.println(nodes.get(0).isMessage());
	
}
}


