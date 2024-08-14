package com.ust.SurveyMicro.responses;

import com.ust.SurveyMicro.Enitity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullResponse {
    private Long surveyId;
    private String domain;
    public Status status;
    public List<String> email;
    public String companyName;
    public String setName;
    public List<SetDTO> setdata;
}