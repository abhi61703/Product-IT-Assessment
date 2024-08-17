package com.example.SurveyMicro.Enitity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * Represents a Survey Data Transfer Object (DTO) including survey details and a list of associated questions.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long surveyId; // Unique identifier for the survey

    private String domainName; // Domain of the survey
    private String companyName; // Name of the company conducting the survey
    private String email; // Email address for the survey
    private String setName; // Name of the survey set
    private String status; // Current status of the survey (e.g., Initiated, Accepted, Rejected)

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions; // List of questions associated with the survey

    // Getters and setters are auto-generated by @Data


}
