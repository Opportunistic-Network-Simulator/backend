//package com.project.api.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.project.api.dto.ProgressResponseDTO;
//import com.project.exception.BusinessRuleException;
//import com.project.model.entity.Progress;
//import com.project.service.ProgressService;
//
//@RestController
//@CrossOrigin //para habilitar cors
//@RequestMapping("/api/progress")
//public class ProgressController {
//	
//	@Autowired
//	ProgressService progressService;
//	
//	@GetMapping("getProgress/{key}")
//    public ResponseEntity<?> getUploadProgress(@PathVariable String key){
//    	try {
//	    	Double progress = progressService.getProgressByKey(key);
//	    	return ResponseEntity.ok(new ProgressResponseDTO(progress));
//    	} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//    }
//	
//	@DeleteMapping("deleteProgress/{key}")
//    public ResponseEntity<?> deleteProgress(@PathVariable String key){
//    	try {
//    		Progress deletedProgress = progressService.deleteProgress(key);
//    		return ResponseEntity.ok(deletedProgress);
//    	} catch(BusinessRuleException e) {
//    		return ResponseEntity.badRequest().body(e.getMessage());
//    	}
//    }
//
//}
