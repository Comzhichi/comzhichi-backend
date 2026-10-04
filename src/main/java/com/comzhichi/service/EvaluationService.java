package com.comzhichi.service;

import com.comzhichi.dto.request.EvaluationRequestDTO;
import com.comzhichi.dto.response.EvaluationResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.EvaluationMapper;
import com.comzhichi.model.Enrollment;
import com.comzhichi.model.Evaluation;
import com.comzhichi.repository.EnrollmentRepository;
import com.comzhichi.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EvaluationMapper evaluationMapper;

    @Transactional(readOnly = true)
    public List<EvaluationResponseDTO> findAll(Long enrollmentId) {
        List<Evaluation> list = (enrollmentId == null)
                ? evaluationRepository.findAll()
                : evaluationRepository.findByEnrollmentId(enrollmentId);

        return list.stream().map(evaluationMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EvaluationResponseDTO findById(Long id) {
        Evaluation eval = evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación no encontrada con ID: " + id));
        return evaluationMapper.toResponse(eval);
    }

    @Transactional
    public EvaluationResponseDTO create(EvaluationRequestDTO dto) {
        Enrollment enrollment = enrollmentRepository.findById(dto.enrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + dto.enrollmentId()));

        Evaluation eval = evaluationMapper.toEntity(dto);
        eval.setEnrollment(enrollment);

        return evaluationMapper.toResponse(evaluationRepository.save(eval));
    }

    @Transactional
    public EvaluationResponseDTO update(Long id, EvaluationRequestDTO dto) {
        Evaluation eval = evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación no encontrada con ID: " + id));

        eval.setScore(dto.score());
        eval.setFeedback(dto.feedback());

        return evaluationMapper.toResponse(evaluationRepository.save(eval));
    }

    @Transactional
    public void delete(Long id) {
        Evaluation eval = evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación no encontrada con ID: " + id));
        evaluationRepository.delete(eval);
    }
}