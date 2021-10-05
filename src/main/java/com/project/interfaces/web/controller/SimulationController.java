package com.project.interfaces.web.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import com.project.interfaces.web.service.FileStorageService;
import com.project.interfaces.web.service.SimulationService;
import com.project.simulator.SimulationProcessor;
import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.SimulationReport;

import lombok.extern.slf4j.Slf4j;



@RestController
//@CrossOrigin //para habilitar cors
@RequestMapping("/api/simulation")
public class SimulationController {
	
	@Autowired
	private SimulationService simulationService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
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
	 
	 @GetMapping("/getSimulationReport")
	 public ResponseEntity<SimulationReport> getSimulationReport(@RequestParam(value = "key") String key) {
		return ResponseEntity.ok(this.simulationService.getSimulationReport(key));
		 
	 }
//	 
//	 @GetMapping(value = "/download", produces = "application/zip")
//	    public HttpEntity<byte[]> downloadFile() throws IOException {
//
//		 byte[] arquivo = Files.readAllBytes(Paths.get("D:\\Dev\\PFC\\Teste\\test.zip"));
//
//	        HttpHeaders httpHeaders = new HttpHeaders();
//
//	        httpHeaders.add("Content-Disposition", "attachment; filename=\"test.zip\"");
//
//	        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);
//
//	        return entity;
//	 }
	    
//    @GetMapping(value = "/download", produces = "application/zip")
//    public ResponseEntity<Resource> downloadFile() throws IOException {
//    	
//    	File file = new File("D:\\Dev\\PFC\\Teste\\test.zip");
//    	InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//    	
//    	HttpHeaders headers = new HttpHeaders();
//    	headers.add("Content-Disposition", "attachment; filename=\"test.zip\"");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(file.length())
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(resource);
//    }
	 
	 @GetMapping(value = "/download", produces = "application/zip")
	 public byte[] downloadFile() throws IOException {
//		setting headers
//	        response.setContentType("application/Zip");
//	        response.setStatus(HttpServletResponse.SC_OK);
//	        response.addHeader("Content-Disposition", "attachment; filename=\"test.Zip\"");

	        //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
	        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
	        

	        //simple file list, just for tests
	        ArrayList<File> files = new ArrayList<>(2);
	        files.add(new File("D:\\Dev\\PFC\\Teste\\test.txt"));

	        //packing files
	        for (File file : files) {
	            //new Zip entry and copying inputstream with file to zipOutputStream, after all closing streams
	            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
	            
	           
	            
	            FileInputStream fileInputStream = new FileInputStream(file);
	           
	            IOUtils.copy(fileInputStream, zipOutputStream);

	            fileInputStream.close();
	            zipOutputStream.closeEntry();
	        }

	        if (zipOutputStream != null) {
	            zipOutputStream.finish();
	            zipOutputStream.flush();
	            IOUtils.closeQuietly(zipOutputStream);
	            
	        }
	        IOUtils.closeQuietly(bufferedOutputStream);
	        IOUtils.closeQuietly(byteArrayOutputStream);
	        return byteArrayOutputStream.toByteArray();
	 }
}
