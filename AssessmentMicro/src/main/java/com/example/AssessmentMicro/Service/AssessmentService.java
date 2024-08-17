package com.example.AssessmentMicro.Service;

import com.example.AssessmentMicro.dto.AnswerDTO;
import com.example.AssessmentMicro.dto.AssessmentDTO;
import com.example.AssessmentMicro.dto.QuestionDTO;
import com.example.AssessmentMicro.Entity.Answer;
import com.example.AssessmentMicro.Entity.Assessment;
import com.example.AssessmentMicro.Entity.Question;
import com.example.AssessmentMicro.Repository.AnswerRepo;
import com.example.AssessmentMicro.Repository.AssessmentRepository;
import com.example.AssessmentMicro.Repository.QuestionRepo;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final AnswerRepo answerRepo;
    private final QuestionRepo questionRepo;

    // Constructor-based injection
    public AssessmentService(AssessmentRepository assessmentRepository, AnswerRepo answerRepo, QuestionRepo questionRepo) {
        this.assessmentRepository = assessmentRepository;
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
    }

    // Method to get all assessments
    public List<Assessment> getAllAssessments() {
        return new ArrayList<>(assessmentRepository.findAll());
    }

    // Method to get an assessment by its set ID
    public Optional<AssessmentDTO> getAssessmentBySetId(Long setId) {
        return assessmentRepository.findById(setId).map(this::convertToDTO);
    }

//    public List<String> getQuestionNamesBySetName(String setName) {
//        Assessment assessment = assessmentRepository.findBySetName(setName)
//                .orElseThrow(() -> new NoSuchElementException("Assessment not found with set name: " + setName));
//        return assessment.getQuestions().stream()
//                .map(Question::getQuestionName)
//                .collect(Collectors.toList());
//    }

    // Method to create a new assessment
    public AssessmentDTO createAssessment(AssessmentDTO assessmentDTO) {
        // Check if the setName already exists
        Optional<Assessment> existingAssessment = assessmentRepository.findBySetName(assessmentDTO.getSetName());
        if (existingAssessment.isPresent()) {
            throw new NoSuchElementException("SetName already exists");
        }

        // Convert DTO to Entity and save
        Assessment assessment = convertToEntity(assessmentDTO);
        assessment = assessmentRepository.save(assessment);

        // Save associated questions
        for (Question question : assessment.getQuestions()) {
            question.setSetId(assessment.getSetId()); // Set the assessment setId in each question
            questionRepo.save(question);
        }

        return convertToDTO(assessment);
    }


    @Transactional
    public void updateQuestionOptions(Long setId, Long questionId, List<AnswerDTO> answerDTOs) {
        Assessment assessment = assessmentRepository.findById(setId)
                .orElseThrow(() -> new NoSuchElementException("Assessment not found with set ID: " + setId));

        Question question = assessment.getQuestions().stream()
                .filter(q -> q.getQuestionId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Question not found with ID: " + questionId));

        // Iterate through the list of new answerDTOs
        for (AnswerDTO answerDTO : answerDTOs) {
            Answer answer = new Answer();
            answer.setOptionText(answerDTO.getOptionText());
            answer.setSuggestion(answerDTO.getSuggestion());
            answer.setQuestionId(question.getQuestionId());
            answerRepo.save(answer); // Save each new answer
            question.getOptions().add(answer); // Add to the existing options list
        }

        // Save the updated question with new options added
        questionRepo.save(question);
    }


    @Transactional
    public void deleteQuestion(Long setId, Long questionId) {
        Assessment assessment = assessmentRepository.findById(setId)
                .orElseThrow(() -> new NoSuchElementException("Assessment not found with set ID: " + setId));

        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("Question not found with ID: " + questionId));

        // Delete all options associated with the question
        answerRepo.deleteByQuestionId(questionId);

        // Remove the question from the assessment
        assessment.getQuestions().removeIf(q -> q.getQuestionId().equals(questionId));

        // Delete the question from the repository
        questionRepo.deleteById(questionId);

        // Save the updated assessment without the deleted question
        assessmentRepository.save(assessment);
    }



    // Helper method to convert Assessment to AssessmentDTO
    private AssessmentDTO convertToDTO(Assessment assessment) {
        List<QuestionDTO> questionDTOs = assessment.getQuestions().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new AssessmentDTO(

                assessment.getSetName(),

                assessment.getDomain(),
                questionDTOs
        );
    }

    // Helper method to convert Question to QuestionDTO
    private QuestionDTO convertToDTO(Question question) {
        List<AnswerDTO> answerDTOs = question.getOptions().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new QuestionDTO(
                question.getQuestionName(),

                answerDTOs
        );
    }

    // Helper method to convert Answer to AnswerDTO
    private AnswerDTO convertToDTO(Answer answer) {
        return new AnswerDTO(
                answer.getOptionText(),
                answer.getSuggestion()
        );
    }

    // Helper method to convert AssessmentDTO to Assessment
    private Assessment convertToEntity(AssessmentDTO assessmentDTO) {
        Assessment assessment = new Assessment();
        assessment.setSetName(assessmentDTO.getSetName());
        assessment.setDomain(assessmentDTO.getDomain());

        List<Question> questions = assessmentDTO.getQuestions().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        assessment.setQuestions(questions);

        return assessment;
    }

    // Helper method to convert QuestionDTO to Question
    private Question convertToEntity(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionName(questionDTO.getQuestionName());

        List<Answer> options = questionDTO.getOptions().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        question.setOptions(options);

        return question;
    }

    // Helper method to convert AnswerDTO to Answer
    private Answer convertToEntity(AnswerDTO answerDTO) {
        Answer option = new Answer();
        option.setOptionText(answerDTO.getOptionText());
        option.setSuggestion(answerDTO.getSuggestion());

        return option;
    }

    public Optional<Assessment> getAssessmentBySetName(String setName) {
        return assessmentRepository.findBySetName(setName);
    }
}
