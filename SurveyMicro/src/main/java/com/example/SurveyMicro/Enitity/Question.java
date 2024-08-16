package com.example.SurveyMicro.Enitity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a Question in a survey, including its options.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId; // Unique identifier for the question

    private String questionName; // Text of the question

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> options; // List of possible answers for the question

    // Getters and setters are auto-generated by @Data
}