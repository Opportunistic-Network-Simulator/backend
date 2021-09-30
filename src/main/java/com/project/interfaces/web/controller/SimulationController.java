package com.project.interfaces.web.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import com.project.interfaces.web.dto.ProtocolConfigurationDTO;
import com.project.interfaces.web.dto.SimulationConfigurationDTO;
import com.project.interfaces.web.parser.WebParser;
import com.project.interfaces.web.service.SimulationService;
import com.project.simulator.SimulationProcessor;
import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.SimulationReport;

import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
//@CrossOrigin //para habilitar cors
@RequestMapping("/api/simulation")
public class SimulationController {
	
	@Autowired
	private SimulationService simulationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulationController.class);
	
	@PostMapping("/runSimulation")
	public ResponseEntity<?> runSimulation(@RequestBody SimulationConfigurationDTO simulationConfigurationDTO) {
		try {
			LOGGER.info("Start send.");
		 	String key = new Timestamp(System.currentTimeMillis()).toString();
		 	simulationService.runSimulation(simulationConfigurationDTO, key);
	        LOGGER.info("Send processing.");
	        return ResponseEntity.ok(key);
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	 
	 @GetMapping("/getSimulationProgress")
	 public ResponseEntity<Double> getSimulationProgress(@RequestParam(value = "key") String key) {
		return ResponseEntity.ok(this.simulationService.getSimulationProgress(key));
		 
	 }
	
}
