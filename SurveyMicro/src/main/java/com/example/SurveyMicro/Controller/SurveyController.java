package com.example.SurveyMicro.Controller;

import com.example.SurveyMicro.Enitity.Survey;
import com.example.SurveyMicro.Enitity.SurveyDTO;
import com.example.SurveyMicro.Service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller for managing surveys. Handles requests related to survey operations.
 */
@RestController
@RequestMapping("/survey")
public class SurveyController {

    @Autowired
    private SurveyService surveyService; // Service for handling survey business logic

    /**
     * Retrieves a survey by its set name.
     *
     * @param setName the name of the survey set
     * @return ResponseEntity containing SurveyDTO or an empty SurveyDTO if not found
     */
    @GetMapping("/{setName}")
    public ResponseEntity<SurveyDTO> getSurveyBySetName(@PathVariable("setName") String setName) {
        try {
            SurveyDTO survey = surveyService.getSurveyBySetName(setName); // Fetch survey from service
            return ResponseEntity.ok(survey); // Return the survey with status OK
        } catch (NoSuchElementException e) {
            // Returning an empty response body with status OK if survey is not found
            return ResponseEntity.ok(new SurveyDTO());
        }
    }

    /**
     * Retrieves a list of all surveys.
     *
     * @return ResponseEntity containing a list of SurveyDTOs
     */
    @GetMapping
    public ResponseEntity<List<SurveyDTO>> getAllSurveys() {
        List<SurveyDTO> surveys = surveyService.getAllSurveys(); // Fetch all surveys from service
        return ResponseEntity.ok(surveys); // Return the list of surveys with status OK
    }

    /**
     * Creates a new survey.
     *
     * @param survey the Survey object to be created
     * @return ResponseEntity with a message indicating success or failure
     */
    @PostMapping
    public ResponseEntity<String> createSurvey(@RequestBody Survey survey) {
        try {
            // Call the service to create a survey and get the response
            ResponseEntity<String> response = surveyService.createSurvey(survey);
            return ResponseEntity.ok(response.getBody()); // Return the response from the service
        } catch (Exception e) {
            // Return a generic message with status OK if an error occurs
            return ResponseEntity.ok("Error occurred while creating survey: " + e.getMessage());
        }
    }
}
