package com.ust.SurveyMicro.Controller;

import com.ust.SurveyMicro.Enitity.Survey;
import com.ust.SurveyMicro.Enitity.SurveyDTO;
import com.ust.SurveyMicro.Service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;

    @GetMapping("/{setName}")
    public ResponseEntity<SurveyDTO> getSurveyBySetName(@PathVariable("setName") String setName) {
        try {
            SurveyDTO survey = surveyService.getSurveyBySetName(setName);
            return ResponseEntity.ok(survey);
        } catch (NoSuchElementException e) {
            // Returning an empty response body with status OK
            return ResponseEntity.ok(new SurveyDTO());
        }
    }

    @GetMapping
    public ResponseEntity<List<SurveyDTO>> getAllSurveys() {
        List<SurveyDTO> surveys = surveyService.getAllSurveys();
        return ResponseEntity.ok(surveys);
    }

    @PostMapping
    public ResponseEntity<String> createSurvey(@RequestBody Survey survey) {
        try {
            // Call the service to create a survey
            ResponseEntity<String> response = surveyService.createSurvey(survey);

            // Return the response from the service
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            // Return a generic message with status OK
            return ResponseEntity.ok("Error occurred while creating survey: " + e.getMessage());
        }
    }
}
