package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.interfaces.web.model.entity.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class WebApplication implements WebMvcConfigurer {
	
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
