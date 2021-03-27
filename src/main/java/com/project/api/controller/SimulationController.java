package com.project.api.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.dto.PairDTO;
import com.project.api.dto.PairsDTO;
import com.project.service.SimulationService;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.Pair;

@RestController
//@CrossOrigin //para habilitar cors
@RequestMapping("/api/simulation")
public class SimulationController {
	
	@Autowired
	private SimulationService simulationService;
	
	@PostMapping("/generateMeetingTrace")
	public ResponseEntity<MeetingTrace> generateMeetingTrace(@RequestBody PairsDTO pairsDto) {
		return ResponseEntity.ok(simulationService.generateMeetingTrace(convert(pairsDto.getPairsList()), pairsDto.getTotalSimulationTime()));
	}
	
	private List<Pair> convert(List<PairDTO> pairs) {
		List<Pair> result = new ArrayList<Pair>();
		for(PairDTO pair : pairs) {
			result.add(new Pair(pair.getNode1(), pair.getNode2(), pair.getRate(), pair.getVariabilityDegree()));
		}
		return result;
	}
}
