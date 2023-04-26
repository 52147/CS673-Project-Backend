package com.cs673.backend;

import jep.Jep;
import jep.JepException;
import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories(basePackages = "com.cs673.backend")
@EntityScan(basePackages = "com.cs673.backend")
@SpringBootApplication
@EnableScheduling
public class BackendApplication {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		SpringApplication.run(BackendApplication.class, args);
	}

}
