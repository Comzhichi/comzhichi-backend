package com.comzhichi.service;

import com.comzhichi.dto.request.AcademicObservationRequestDTO;
import com.comzhichi.dto.response.AcademicObservationResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.AcademicObservationMapper;
import com.comzhichi.model.AcademicObservation;
import com.comzhichi.model.Enrollment;
import com.comzhichi.repository.AcademicObservationRepository;
import com.comzhichi.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicObservationService {

    private final AcademicObservationRepository observationRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AcademicObservationMapper observationMapper;

    @Transactional(readOnly = true)
    public List<AcademicObservationResponseDTO> findAll(Long enrollmentId) {
        List<AcademicObservation> observations = enrollmentId == null
                ? observationRepository.findAll()
                : observationRepository.findByEnrollmentId(enrollmentId);
        return observations.stream().map(observationMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AcademicObservationResponseDTO findById(Long id) {
        return observationMapper.toResponse(observationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Observación no encontrada con ID: " + id)));
    }

    @Transactional
    public AcademicObservationResponseDTO create(AcademicObservationRequestDTO dto) {
        Enrollment enrollment = enrollmentRepository.findById(dto.enrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + dto.enrollmentId()));
        AcademicObservation observation = observationMapper.toEntity(dto);
        observation.setEnrollment(enrollment);
        return observationMapper.toResponse(observationRepository.save(observation));
    }

    @Transactional
    public AcademicObservationResponseDTO update(Long id, AcademicObservationRequestDTO dto) {
        AcademicObservation observation = observationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Observación no encontrada con ID: " + id));
        observation.setDate(dto.date());
        observation.setObservation(dto.observation());
        return observationMapper.toResponse(observationRepository.save(observation));
    }

    @Transactional
    public void delete(Long id) {
        AcademicObservation observation = observationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Observación no encontrada con ID: " + id));
        observationRepository.delete(observation);
    }
}
