package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.MeetingTraceGeneratorInput;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.Pair;
import com.project.simulator.generator.MeetingTraceGenerator;
import com.project.simulator.generator.NodesGenerator;

	

@Service
public class SimulationService {
	
	public void generateNodes(long numberOfNodes) {
		NodesGenerator.generateNodes(numberOfNodes);
	}
	
	public Node findNodeById(long id) {
		for(Node node : NodesGenerator.nodes) {
			if(node.getId() == id) return node;
		}
		return null;
	}
	
	public MeetingTrace generateMeetingTrace(List<Pair> pairs, double totalSimulationTime) {
		return MeetingTraceGenerator.generateMeetingTrace(new MeetingTraceGeneratorInput(pairs, totalSimulationTime));
	}	
}
