package com.example.AssessmentMicro.Controller;

import com.example.AssessmentMicro.Entity.Assessment;
import com.example.AssessmentMicro.Service.AssessmentService;
import com.example.AssessmentMicro.dto.AnswerDTO;
import com.example.AssessmentMicro.dto.AssessmentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/assessment")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAssessments() {
        try {
            List<Assessment> assessments = assessmentService.getAllAssessments();
            return ResponseEntity.ok(assessments);
        } catch (Exception e) {
            return ResponseEntity.ok("Error retrieving assessments: " + e.getMessage());
        }
    }

//    @GetMapping("/{setName}/questions")
//    public ResponseEntity<?> getQuestionNamesBySetName(@PathVariable String setName) {
//        try {
//            List<String> questionNames = assessmentService.getQuestionNamesBySetName(setName);
//            return ResponseEntity.ok(questionNames);
//        } catch (Exception e) {
//            return ResponseEntity.ok("Error retrieving question names: " + e.getMessage());
//        }
//    }

    @GetMapping("/{setId}")
    public ResponseEntity<?> getAssessmentBySetId(@PathVariable Long setId) {
        try {
            Optional<AssessmentDTO> assessment = assessmentService.getAssessmentBySetId(setId);
            return assessment.isPresent()
                    ? ResponseEntity.ok(assessment.get())
                    : ResponseEntity.ok("Assessment not found with set ID: " + setId);
        } catch (Exception e) {
            return ResponseEntity.ok("Error retrieving assessment: " + e.getMessage());
        }
    }

    @GetMapping("/bysetname/{setName}")
    public ResponseEntity<?> getAssessmentBySetName(@PathVariable String setName) {
        try {
            Optional<Assessment> assessment = assessmentService.getAssessmentBySetName(setName);
            return assessment.isPresent()
                    ? ResponseEntity.ok(assessment.get())
                    : ResponseEntity.ok("Assessment not found with set Name: " + setName);
        } catch (Exception e) {
            return ResponseEntity.ok("Error retrieving assessment: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        try {
            AssessmentDTO createdAssessment = assessmentService.createAssessment(assessmentDTO);
            return ResponseEntity.ok(createdAssessment);
        } catch (Exception e) {
            return ResponseEntity.ok("Error creating assessment:Setname already exists");
        }
    }

    @PutMapping("/{setId}/questions/{questionId}")
    public ResponseEntity<?> updateQuestionOptions(@PathVariable Long setId, @PathVariable Long questionId,
                                                   @RequestBody List<AnswerDTO> answerDTOs) {
        try {
            assessmentService.updateQuestionOptions(setId, questionId, answerDTOs);
            return ResponseEntity.ok("Question options updated successfully");
        } catch (Exception e) {
            return ResponseEntity.ok("Error updating question options: " + e.getMessage());
        }
    }

    @DeleteMapping("/{setId}/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long setId, @PathVariable Long questionId) {
        try {
            assessmentService.deleteQuestion(setId, questionId);
            return ResponseEntity.ok("Question deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.ok("Error deleting question: " + e.getMessage());
        }
    }
}