package com.project.interfaces.web.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.interfaces.web.dto.SimulationConfigurationDTO;
import com.project.interfaces.web.service.FileStorageService;
import com.project.interfaces.web.service.SimulationService;
import com.project.simulator.entity.SimulationReport;




@RestController
@RequestMapping("/api/simulation")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class SimulationController {
	
	@Autowired
	private SimulationService simulationService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	
	@PostMapping("/runSimulation")
	public ResponseEntity<?> runSimulation(@RequestBody SimulationConfigurationDTO simulationConfigurationDTO) {
		try {
		 	String key = String.valueOf(System.currentTimeMillis());
		 	simulationService.runSimulation(simulationConfigurationDTO, key);
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
	 
	 @GetMapping("/getSimulationReport")
	 public ResponseEntity<SimulationReport> getSimulationReport(@RequestParam(value = "key") String key) {
		return ResponseEntity.ok(this.simulationService.getSimulationReport(key));
		 
	 }

	 
    @GetMapping(value = "/download", produces = "application/zip")
    public ResponseEntity<String> downloadFile(@RequestParam(value = "key") String key) throws IOException {
    	HttpHeaders headers = new HttpHeaders();
    	headers.set("Content-Disposition", "attachment; filename=" + "report_" + key + ".zip");
    	
    	return ResponseEntity.ok()
    			.headers(headers)
    			.body(this.simulationService.getZippedReport(key));
    }
	 
}
