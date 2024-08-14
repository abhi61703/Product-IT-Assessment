package com.ust.SurveyMicro.Controller;

import com.ust.SurveyMicro.Enitity.Survey;
import com.ust.SurveyMicro.Service.SurveyService;
import com.ust.SurveyMicro.responses.FullResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @PostMapping("/survey")
    public FullResponse addSurvey(@RequestBody Survey survey) {
        return surveyService.addSurvey(survey);
    }

    @GetMapping("/surveys")
    public List<FullResponse> getSurveys() {
        return surveyService.getSurveys();
    }


}