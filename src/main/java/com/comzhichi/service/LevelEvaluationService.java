package com.comzhichi.service;

import com.comzhichi.dto.request.LevelEvaluationRequestDTO;
import com.comzhichi.dto.response.LevelEvaluationResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.LevelEvaluationMapper;
import com.comzhichi.model.Enrollment;
import com.comzhichi.model.LevelEvaluation;
import com.comzhichi.repository.EnrollmentRepository;
import com.comzhichi.repository.LevelEvaluationRepository;
import com.comzhichi.repository.StudentParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelEvaluationService {

    private final LevelEvaluationRepository evaluationRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LevelEvaluationMapper evaluationMapper;
    private final NotificationService notificationService;
    private final StudentParentRepository studentParentRepository;

    @Transactional(readOnly = true)
    public List<LevelEvaluationResponseDTO> findAll(Long enrollmentId) {
        List<LevelEvaluation> list = (enrollmentId == null)
                ? evaluationRepository.findAll()
                : evaluationRepository.findByEnrollmentId(enrollmentId);
        return list.stream().map(evaluationMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public LevelEvaluationResponseDTO findById(Long id) {
        return evaluationMapper.toResponse(evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación de nivel no encontrada con ID: " + id)));
    }

    @Transactional
    public LevelEvaluationResponseDTO create(LevelEvaluationRequestDTO dto) {
        Enrollment enrollment = enrollmentRepository.findById(dto.enrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + dto.enrollmentId()));

        LevelEvaluation evaluation = evaluationMapper.toEntity(dto);
        evaluation.setEnrollment(enrollment);
        LevelEvaluation saved = evaluationRepository.save(evaluation);

        // Notificar al estudiante
        notificationService.notify(
                saved.getEnrollment().getStudent().getUser(),
                "Resultado de evaluación de nivel: " + saved.getResult());

        // Notificar a los familiares vinculados
        studentParentRepository.findByStudentId(saved.getEnrollment().getStudent().getId())
                .forEach(link -> notificationService.notify(
                        link.getParent().getUser(),
                        "Resultado académico de su familiar: " + saved.getResult()));

        return evaluationMapper.toResponse(saved);
    }

    @Transactional
    public LevelEvaluationResponseDTO update(Long id, LevelEvaluationRequestDTO dto) {
        LevelEvaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación de nivel no encontrada con ID: " + id));

        evaluation.setDate(dto.date());
        evaluation.setResult(dto.result());
        evaluation.setComments(dto.comments());
        return evaluationMapper.toResponse(evaluationRepository.save(evaluation));
    }

    @Transactional
    public void delete(Long id) {
        LevelEvaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación de nivel no encontrada con ID: " + id));
        evaluationRepository.delete(evaluation);
    }
}