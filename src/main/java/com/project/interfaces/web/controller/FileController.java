package com.project.interfaces.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.interfaces.web.dto.UploadFileResponse;
import com.project.interfaces.web.dto.UploadMultipleFilesResponseDTO;
import com.project.interfaces.web.service.FileStorageService;

import javax.xml.bind.JAXBException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

@RestController
@CrossOrigin //para habilitar cors
@RequestMapping("/api/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    
    @PostMapping("/uploadMultipleFiles")
    public ResponseEntity<?> newuploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
    												@RequestParam("key") String key,
    												@RequestParam("description") String description
    												) throws FileNotFoundException, JAXBException {

    	long begin = new Timestamp(System.currentTimeMillis()).getTime();
    	String formattedDate = fileStorageService.formattedDate();
    	UploadMultipleFilesResponseDTO uploadMultipleFilesResponseDTO = new UploadMultipleFilesResponseDTO();
    	List<UploadFileResponse> uploadFileResponseList = new ArrayList<UploadFileResponse>();
    	Integer numberOfRegistredProducts;
    	try {
    		//code to process the file
    		long end = new Timestamp(System.currentTimeMillis()).getTime();
    		System.out.println("tempo total: " + (end - begin));
    		return ResponseEntity.ok(new UploadMultipleFilesResponseDTO(uploadFileResponseList));
    	} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    	
    }
}