package com.comzhichi.repository;

import com.comzhichi.model.AcademicObservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicObservationRepository extends JpaRepository<AcademicObservation, Long> {
    List<AcademicObservation> findByEnrollmentId(Long enrollmentId);
}