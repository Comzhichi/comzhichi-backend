package com.comzhichi.repository;

import com.comzhichi.model.LevelEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelEvaluationRepository extends JpaRepository<LevelEvaluation, Long> {
    List<LevelEvaluation> findByStudentId(Long studentId);
}
