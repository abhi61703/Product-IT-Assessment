package com.example.SurveyMicro.Controller;

import com.example.SurveyMicro.Enitity.Survey;
import com.example.SurveyMicro.Enitity.SurveyDTO;
import com.example.SurveyMicro.Service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing surveys. Handles requests related to survey operations.
 */
@RestController
@RequestMapping("/survey")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    /**
     * Retrieves a survey by its set name.
     *
     * @param setName the name of the survey set
     * @return ResponseEntity containing SurveyDTO or error message
     */
    @GetMapping("/{setName}")
    public ResponseEntity<?> getSurveyBySetName(@PathVariable("setName") String setName) {
        Object response = surveyService.getSurveyBySetName(setName);
        if (response instanceof SurveyDTO) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Retrieves a survey by its survey ID.
     *
     * @param surveyId the ID of the survey
     * @return ResponseEntity containing SurveyDTO or error message
     */
    @GetMapping("/id/{surveyId}")
    public ResponseEntity<?> getSurveyById(@PathVariable("surveyId") Long surveyId) {
        Object response = surveyService.getSurveyById(surveyId);
        if (response instanceof SurveyDTO) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Retrieves a list of all surveys.
     *
     * @return ResponseEntity containing a list of SurveyDTOs or error message
     */
    @GetMapping
    public ResponseEntity<?> getAllSurveys() {
        Object response = surveyService.getAllSurveys();
        if (response instanceof List) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Creates a new survey.
     *
     * @param survey the Survey object to be created
     * @return ResponseEntity with a message indicating success or failure
     */
    @PostMapping
    public ResponseEntity<?> createSurvey(@RequestBody Survey survey) {
        Object response = surveyService.createSurvey(survey);
        if (response instanceof SurveyDTO) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
