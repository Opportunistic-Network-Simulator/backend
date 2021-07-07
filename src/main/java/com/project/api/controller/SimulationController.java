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
import com.project.service.SimulationService;
import com.project.simulator.entity.Meet;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.Pair;

@RestController
//@CrossOrigin //para habilitar cors
@RequestMapping("/api/simulation")
public class SimulationController {
	
	@Autowired
	private SimulationService simulationService;
	
	@PostMapping("/generateMeetingTrace")
	public ResponseEntity<MeetingTraceDTO> generateMeetingTrace(@RequestBody PairsDTO pairsDto) {
		MeetingTrace meetingTrace = simulationService.generateMeetingTrace(convertDtoToPair(pairsDto.getPairsList()), pairsDto.getTotalSimulationTime());
		return ResponseEntity.ok(convertMeetingTraceToDto(meetingTrace));
	}
	
	@PostMapping("/executeSimulation")
	public ResponseEntity<Double> executeSimulation(@RequestBody PairsDTO pairsDto) {
		MeetingTrace meetingTrace = simulationService
										.generateMeetingTrace(
												convertDtoToPair(pairsDto.getPairsList()), 
												pairsDto.getTotalSimulationTime());
		double avgDelay = simulationService.executeSimulation(
									pairsDto.getTotalSimulationTime(), 
									meetingTrace, 
									pairsDto.getNumberOfNodes(),
									pairsDto.isStopOnEndOfArrivals());
		
		return ResponseEntity.ok(avgDelay);
	}
	
	private List<Pair> convertDtoToPair(List<PairDTO> pairs) {
		List<Pair> pairsList = new ArrayList<Pair>();
		for(PairDTO pair : pairs) {
			pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), pair.getRate(), pair.getVariabilityDegree()));
		}
		return pairsList;
	}
	
	private MeetingTraceDTO convertMeetingTraceToDto(MeetingTrace meetingTrace) {
		MeetingTraceDTO meetingTraceDto = new MeetingTraceDTO();
		for(Meet meet : meetingTrace.getMeetingTrace()) {
			Pair pair = meet.getPair();
			PairDTO pairDTO = new PairDTO(pair.getNode1(), pair.getNode2());
			MeetDTO meetDto = new MeetDTO(pairDTO, meet.getInstant());
			meetingTraceDto.addMeet(meetDto);
		}
		return meetingTraceDto;
		
	}
}
