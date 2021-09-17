package com.project.interfaces.web.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.interfaces.web.dto.MeetDTO;
import com.project.interfaces.web.dto.MeetingTraceDTO;
import com.project.interfaces.web.dto.PairDTO;
import com.project.interfaces.web.dto.PairsDTO;
import com.project.interfaces.web.dto.ProtocolConfigurationDTO;
import com.project.interfaces.web.dto.SimulationConfigurationDTO;
import com.project.interfaces.web.parser.WebParser;
import com.project.interfaces.web.service.SimulationService;
import com.project.simulator.SimulationProcessor;
import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.Meet;
import com.project.simulator.entity.MeetingTrace;
import com.project.simulator.entity.Pair;
import com.project.simulator.entity.SimulationReport;

@RestController
//@CrossOrigin //para habilitar cors
@RequestMapping("/api/simulation")
public class SimulationController {
	
	@Autowired
	private SimulationService simulationService;
	
	@PostMapping("/runSimulation")
	public ResponseEntity<?> runSimulation(@RequestBody SimulationConfigurationDTO simulationConfigurationDTO) {
		SimulationConfiguration config = new WebParser().parser(simulationConfigurationDTO);
		SimulationProcessor processor = new SimulationProcessor(config);
	    SimulationReport report = processor.runSimulation();
		return ResponseEntity.ok(report);
	}
	
}
