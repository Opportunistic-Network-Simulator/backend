package com.project.api.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.dto.MeetDTO;
import com.project.api.dto.MeetingTraceDTO;
import com.project.api.dto.PairDTO;
import com.project.api.dto.PairsDTO;
import com.project.api.dto.SimulationResponseDTO;
import com.project.service.SimulationService;
import com.project.simulator.entity.Meet;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.Node;
import com.project.simulator.entity.Pair;
import com.project.simulator.generator.MessagesGenerator;
import com.project.simulator.generator.NodesGenerator;

@RestController
//@CrossOrigin //para habilitar cors
@RequestMapping("/api/simulation")
public class SimulationController {
	
	@Autowired
	private SimulationService simulationService;
	
	@PostMapping("/executeSimulation")
	public ResponseEntity<SimulationResponseDTO> generateMeetingTrace(@RequestBody PairsDTO pairsDto) {
		simulationService.generateNodes(pairsDto.getNumberOfNodes());
		simulationService.generateMessages();
		MeetingTrace meetingTrace = simulationService.generateMeetingTrace(convertDtoToPair(pairsDto.getPairsList()), pairsDto.getTotalSimulationTime());
		simulationService.executeSimulation(meetingTrace);
		return ResponseEntity.ok(new SimulationResponseDTO(convertMeetingTraceToDto(meetingTrace), MessagesGenerator.messages));
	}
	
	private List<Pair> convertDtoToPair(List<PairDTO> pairs) {
		List<Pair> pairsList = new ArrayList<Pair>();
		for(PairDTO pair : pairs) {
			Node node1 = simulationService.findNodeById(pair.getNode1());
			Node node2 = simulationService.findNodeById(pair.getNode2());
			pairsList.add(new Pair(node1, node2, pair.getRate(), pair.getVariabilityDegree()));
		}
		return pairsList;
	}
	
	private MeetingTraceDTO convertMeetingTraceToDto(MeetingTrace meetingTrace) {
		MeetingTraceDTO meetingTraceDto = new MeetingTraceDTO();
		for(Meet meet : meetingTrace.getMeetingTrace()) {
			Pair pair = meet.getPair();
			PairDTO pairDTO = new PairDTO(pair.getNode1().getId(), pair.getNode2().getId());
			MeetDTO meetDto = new MeetDTO(pairDTO, meet.getInstant());
			meetingTraceDto.addMeet(meetDto);
		}
		return meetingTraceDto;
		
	}
}
