package com.comzhichi.repository;

import com.comzhichi.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByCourseId(Long courseId);
    List<Section> findByTeacherId(Long teacherId);
    List<Section> findByAvailableVacanciesGreaterThan(Integer vacancies);
}