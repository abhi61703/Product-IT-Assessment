package com.example.SurveyMicro.FeignClient;

import com.example.SurveyMicro.Enitity.Assessment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign Client for interacting with the Assessment Service.
 * This interface defines the methods to communicate with the assessment-service.
 */
@FeignClient(name = "assessment-service", url = "http://localhost:8082/assessment")
public interface AssessmentClient {

    /**
     * Retrieves an assessment by its set name.
     *
     * @param setName the name of the assessment set
     * @return ResponseEntity containing the Assessment object
     */
    @GetMapping("/bysetname/{setname}")
    ResponseEntity<Assessment> getAssessmentBySetName(@PathVariable("setname") String setName);
}
