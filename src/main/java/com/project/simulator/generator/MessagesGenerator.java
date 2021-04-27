package com.project.simulator.generator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.service.SimulationService;
import com.project.simulator.entity.Message;
import com.project.simulator.entity.Node;

public class MessagesGenerator {
	
	public static List<Message> messages;
	
	public static void generateMessages() { //posteriormente, deve pegar dados da requisição para gerar as msg
		messages = new ArrayList<Message>();
		Message message = new Message(0, 100, 1, 2, 0);
		messages.add(message);
		SimulationService simulationService = new SimulationService(); //temporário
		Node sourceNode = simulationService.findNodeById(message.getSourceNode()); //o nó origem vai ser recebido como parâmetro
		sourceNode.addMessage(message);
	}

}
