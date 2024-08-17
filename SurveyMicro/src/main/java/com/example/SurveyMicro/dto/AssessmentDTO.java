package com.example.SurveyMicro.dto;

import com.example.SurveyMicro.Enitity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AssessmentDTO {
    private Long setId;
    private String setName;
    private String domain;
    private List<Question> questions;

}
