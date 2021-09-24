package com.project.interfaces.web.model.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
	
	private String uploadDir;


}
