package com.ust.SurveyMicro.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetNameDto {
    private String setName;
    private List<String> questionNames;
}
