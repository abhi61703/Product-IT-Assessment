package com.ust.SurveyMicro.Repository;

import com.ust.SurveyMicro.Enitity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Optional<Survey> findBySetName(String setName);
}
