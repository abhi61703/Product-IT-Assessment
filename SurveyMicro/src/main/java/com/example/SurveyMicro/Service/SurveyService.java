
package com.example.SurveyMicro.Service;

import com.example.SurveyMicro.Enitity.*;
import com.example.SurveyMicro.Repository.SurveyRepository;
import com.example.SurveyMicro.FeignClient.AssessmentClient;
import com.example.SurveyMicro.dto.AssessmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    private static final Logger logger = LoggerFactory.getLogger(SurveyService.class);

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private AssessmentClient assessmentClient;

    public Object getAssessmentBySetName(String setName) {
        try {
            // Retrieve the assessment details using Feign client
            ResponseEntity<Assessment> response = assessmentClient.getAssessmentBySetName(setName);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Successfully retrieved assessment
                Assessment assessment = response.getBody();

                // Return assessment details only
                return new AssessmentDTO(
                        assessment.getSetId(),
                        assessment.getSetName(),
                        assessment.getDomain(),
                        assessment.getQuestions()
                );
            } else {
                // Handle case where assessment is not found
                return "Assessment not found for set name: " + setName;
            }

        } catch (Exception e) {
            // Handle unexpected exceptions
            return "Error retrieving assessment: " + e.getMessage();
        }
    }


    public Object getSurveyById(Long surveyId) {
        try {
            Survey survey = surveyRepository.findById(surveyId)
                    .orElseThrow(() -> new NoSuchElementException("Survey not found with ID: " + surveyId));

            var assessment = assessmentClient.getAssessmentBySetName(survey.getSetName()).getBody();

            List<Question> allQuestions = assessment.getQuestions();

            List<Long> providedQuestionIds = Arrays.stream(survey.getQuestionIds().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            List<Question> filteredQuestions = allQuestions.stream()
                    .filter(question -> providedQuestionIds.contains(question.getQuestionId()))
                    .collect(Collectors.toList());

            return new SurveyDTO(
                    survey.getSurveyId(),
                    survey.getDomainName(),
                    survey.getCompanyName(),
                    survey.getEmail(),
                    survey.getSetName(),
                    survey.getStatus(),
                    filteredQuestions
            );

        } catch (NoSuchElementException e) {
            return "Survey not found with ID: " + surveyId;
        } catch (Exception e) {
            return "Error retrieving survey: " + e.getMessage();
        }
    }

    public Object getAllSurveys() {
        List<SurveyDTO> surveyDTOs = new ArrayList<>();
        try {
            List<Survey> surveys = surveyRepository.findAll();

            for (Survey survey : surveys) {
                try {
                    var assessment = assessmentClient.getAssessmentBySetName(survey.getSetName()).getBody();

                    List<Question> allQuestions = assessment.getQuestions();

                    List<Long> providedQuestionIds = Arrays.stream(survey.getQuestionIds().split(","))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());

                    List<Question> filteredQuestions = allQuestions.stream()
                            .filter(question -> providedQuestionIds.contains(question.getQuestionId()))
                            .collect(Collectors.toList());

                    surveyDTOs.add(new SurveyDTO(
                            survey.getSurveyId(),
                            survey.getDomainName(),
                            survey.getCompanyName(),
                            survey.getEmail(),
                            survey.getSetName(),
                            survey.getStatus(),
                            filteredQuestions
                    ));
                } catch (Exception e) {
                    surveyDTOs.add(new SurveyDTO(
                            null,
                            null,
                            null,
                            null,
                            null,
                            SurveyStatus.PENDING,
                            Collections.emptyList()
                    ));
                }
            }
            return surveyDTOs;

        } catch (Exception e) {
            return "Error retrieving surveys: " + e.getMessage();
        }
    }

    public Object createSurvey(Survey survey) {
        try {
            var assessment = assessmentClient.getAssessmentBySetName(survey.getSetName()).getBody();

            List<Question> allQuestions = assessment.getQuestions();

            List<Long> providedQuestionIds = Arrays.stream(survey.getQuestionIds().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            List<Question> selectedQuestions = allQuestions.stream()
                    .filter(question -> providedQuestionIds.contains(question.getQuestionId()))
                    .collect(Collectors.toList());

            if (selectedQuestions.size() != providedQuestionIds.size()) {
                return "One or more question IDs not found";
            }

            surveyRepository.save(survey);
            return new SurveyDTO(
                    survey.getSurveyId(),
                    survey.getDomainName(),
                    survey.getCompanyName(),
                    survey.getEmail(),
                    survey.getSetName(),
                    SurveyStatus.PENDING,
                    Collections.emptyList()
            );
        } catch (Exception e) {
            return "Error creating survey: " + e.getMessage();
        }
    }
}
