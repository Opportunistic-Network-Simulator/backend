package com.project.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.dto.PairsDTO;
import com.project.model.entity.Meet;
import com.project.model.entity.Pair;
import com.project.service.SimulationService;

@RestController
//@CrossOrigin //para habilitar cors
@RequestMapping("/api/simulation")
public class SimulationController {
	
	@Autowired
	private SimulationService simulationService;
	
	@PostMapping("/generateMeetingTrace")
	public ResponseEntity generateMeetingTrace(@RequestBody PairsDTO pairsDto) {
		List<Meet> fel = simulationService.generateMeetingTrace(pairsDto.getPairsList());
		return ResponseEntity.ok(fel);
	}
}
