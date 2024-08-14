package com.ust.SurveyMicro.FeignClient;

import com.ust.SurveyMicro.responses.SetNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "asessment-service",url = "http://localhost:8081/")
public interface AssessmentClient {

    @GetMapping("/assessment/{setName}")
    public ResponseEntity<List<SetNameDto>> getSet(@PathVariable String setName);

}