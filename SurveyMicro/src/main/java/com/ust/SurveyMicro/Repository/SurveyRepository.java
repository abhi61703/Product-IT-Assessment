package com.ust.SurveyMicro.Repository;

import com.ust.SurveyMicro.Enitity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for accessing Survey entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    /**
     * Finds a Survey by its set name.
     *
     * @param setName the name of the survey set
     * @return Optional containing the Survey if found, otherwise empty
     */
    Optional<Survey> findBySetName(String setName);
}
