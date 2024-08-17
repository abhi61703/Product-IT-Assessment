
package com.example.AssessmentMicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AssessmentMicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentMicroApplication.class, args);
	}

}
