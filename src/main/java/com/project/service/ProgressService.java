//package com.project.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.project.exception.BusinessRuleException;
//import com.project.model.entity.Progress;
//import com.project.model.repository.ProgressRepository;
//
//@Service
//public class ProgressService {
//	
//	@Autowired
//	ProgressRepository progressRepository;
//	
//	public boolean beginProgress(String key, String description) {
//		Optional<Progress> optionalProgress = progressRepository.findByKey(key);
//		if(optionalProgress.isPresent()) {
//			return false;
//		}
//		else {
//			Progress deleteProgress = Progress.builder().key(key).description(description).progress((double) 0).build();
//			deleteProgress = progressRepository.save(deleteProgress);
//		}
//		return true;
//	}
//	
//	public Double getProgressByKey(String key) {
//    	Optional<Progress> optionalProgress = progressRepository.findByKey(key);
//    	if(!optionalProgress.isPresent()) {
//    		return 0.0;
//    	} else {
//    		Progress progress = optionalProgress.get();
//    		if(progress.getProgress() == -1) {
//    			throw new BusinessRuleException(progress.getFailMessage());
//    		}
//    	}
//    	return optionalProgress.get().getProgress();
//    }
//	
//	public void completeProgress(String key) {
//		Optional<Progress> optionalProgress = progressRepository.findByKey(key);
//		if(optionalProgress.isPresent()) {
//			Progress progress = optionalProgress.get();
//    		progress.setProgress((double) 100);
//    		progressRepository.save(progress);
//    	}
//	}
//	
//	public void failProgress(String key, String message) {
//		Optional<Progress> optionalProgress = progressRepository.findByKey(key);
//		if(optionalProgress.isPresent()) {
//			Progress progress = optionalProgress.get();
//			progress.setProgress((double) -1);
//			progress.setFailMessage(message);
//			progressRepository.save(progress);
//		}
//	}
//	
//	public Progress deleteProgress(String key) {
//		Optional<Progress> optionalProgress = progressRepository.findByKey(key);
//		if(!optionalProgress.isPresent()) {
//			throw new BusinessRuleException("Chave de progresso inexistente");
//		}
//		Progress progress = optionalProgress.get();
//		progressRepository.delete(progress);
//		return progress;
//		
//	}
//
//}
