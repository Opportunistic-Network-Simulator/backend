package com.project.interfaces.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.interfaces.web.dto.ProtocolConfigurationDTO;
import com.project.interfaces.web.dto.SimulationConfigurationDTO;
import com.project.interfaces.web.parser.WebParser;
import com.project.interfaces.web.service.SimulationService;
import com.project.simulator.SimulationProcessor;
import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.SimulationReport;

@RestController
//@CrossOrigin //para habilitar cors
@RequestMapping("/api/simulation")
public class SimulationController {
	
	@Autowired
	private SimulationService simulationService;
	
	@PostMapping("/runSimulation")
	public ResponseEntity<?> runSimulation(@RequestBody SimulationConfigurationDTO simulationConfigurationDTO) {
		try {
			SimulationConfiguration config = new WebParser().parser(simulationConfigurationDTO);
			SimulationProcessor processor = new SimulationProcessor(config);
			SimulationReport report = processor.runSimulation();
			return ResponseEntity.ok(report);
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/download")
	public ResponseEntity<Resource> download() throws IOException {
		
		System.out.println("entrou no download");

		File file = new File("D:\\Dev\\PFC\\Teste\\test.txt");
		
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
//	            .headers(headers)
	            .contentLength(file.length())
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(resource);
	} 
	
}
