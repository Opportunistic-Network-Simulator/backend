package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.model.entity.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
@EnableAsync
public class WebApplication implements WebMvcConfigurer {
	
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
