package com.project.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.api.dto.StoragedFileDTO;
import com.project.api.dto.UploadFileResponse;
import com.project.api.dto.UploadMultipleFilesResponseDTO;
import com.project.api.dto.ProgressResponseDTO;
import com.project.exception.BusinessRuleException;
import com.project.model.entity.Progress;
import com.project.service.FileStorageService;
import com.project.service.ProgressService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;

@RestController
@CrossOrigin //para habilitar cors
@RequestMapping("/api/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
	ProgressService progressService;
    
//    @PostMapping("/uploadFile")
//    public UploadFileResponse uploadFile(	@RequestParam(value = "file") MultipartFile file,
//    										String formattedDate) throws JAXBException {
//    	StoragedFileDTO storagedFileDTO = fileStorageService.storeFile(file, formattedDate);
//    	String fileName = storagedFileDTO.getFileName();
//    	Integer numberOfRegistredProducts = storagedFileDTO.getNumberOfRegistredProducts();
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/files/downloadFile/")
//                .path(fileName)
//                .toUriString();
//
//        return new UploadFileResponse(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize(), numberOfRegistredProducts);
//    }

    
    @PostMapping("/uploadMultipleFiles")
    public ResponseEntity<?> newuploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
    												@RequestParam("key") String key,
    												@RequestParam("description") String description
    												) throws FileNotFoundException, JAXBException {
    	if(!progressService.beginProgress(key, description)) {
			System.out.println("blocking new uploadMultipleFilesRequest to same key");
			return null;
		}
    	long begin = new Timestamp(System.currentTimeMillis()).getTime();
    	String formattedDate = fileStorageService.formattedDate();
    	UploadMultipleFilesResponseDTO uploadMultipleFilesResponseDTO = new UploadMultipleFilesResponseDTO();
    	List<UploadFileResponse> uploadFileResponseList = new ArrayList<UploadFileResponse>();
    	Integer numberOfRegistredProducts;
    	try {
    		//process the file
    		long end = new Timestamp(System.currentTimeMillis()).getTime();
    		System.out.println("tempo total: " + (end - begin));
    		return ResponseEntity.ok(new UploadMultipleFilesResponseDTO(uploadFileResponseList));
    	} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    	
    }
    
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}