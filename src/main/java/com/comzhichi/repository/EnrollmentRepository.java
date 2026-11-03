package com.comzhichi.repository;

import com.comzhichi.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findBySectionId(Long sectionId);
    List<Enrollment> findByStudent_IdAndSection_IdAndStatus(Long studentId, Long sectionId, com.comzhichi.model.EnrollmentStatus status);
}