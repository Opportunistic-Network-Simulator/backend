package com.project.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.entity.Progress;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
	
	Optional<Progress> findByKey(String key);
}
