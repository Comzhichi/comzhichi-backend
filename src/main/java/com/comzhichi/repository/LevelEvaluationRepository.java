package com.comzhichi.repository;

import com.comzhichi.model.LevelEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelEvaluationRepository extends JpaRepository<LevelEvaluation, Long> {
    List<LevelEvaluation> findByEnrollmentId(Long enrollmentId);
}