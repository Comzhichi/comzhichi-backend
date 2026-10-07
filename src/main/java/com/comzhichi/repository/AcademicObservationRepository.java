package com.comzhichi.repository;

import com.comzhichi.model.AcademicObservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicObservationRepository extends JpaRepository<AcademicObservation, Long> {
    List<AcademicObservation> findByEnrollmentId(Long enrollmentId);
}
