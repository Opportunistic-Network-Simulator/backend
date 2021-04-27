package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.simulator.entity.Meet;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.MeetingTraceGeneratorInput;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.Pair;
import com.project.simulator.generator.MeetingTraceGenerator;
import com.project.simulator.generator.MessagesGenerator;
import com.project.simulator.generator.NodesGenerator;

	

@Service
public class SimulationService {
	
	public void generateNodes(long numberOfNodes) {
		NodesGenerator.generateNodes(numberOfNodes);
	}
	
	public void generateMessages() {
		MessagesGenerator.generateMessages();
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

	public void executeSimulation(MeetingTrace meetingTrace) {
		for(Meet meet : meetingTrace.getMeetingTrace()) {
			this.handleMeet(meet);
		}
	}

	private void handleMeet(Meet meet) { //futuramente, vai receber protocolo de encaminhamento como parâmetro
		//Protocolo Epidêmico
		Pair pair = meet.getPair();
		double instant = meet.getInstant();
		Node node1 = pair.getNode1();
		Node node2 = pair.getNode2();
		node1.sendMessages(node2, instant);
		node2.sendMessages(node1, instant);
		
	}	
}
