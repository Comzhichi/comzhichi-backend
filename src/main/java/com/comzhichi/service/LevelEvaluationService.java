package com.comzhichi.service;

import com.comzhichi.dto.request.LevelEvaluationRequestDTO;
import com.comzhichi.dto.response.LevelEvaluationResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.LevelEvaluationMapper;
import com.comzhichi.model.LevelEvaluation;
import com.comzhichi.repository.StudentParentRepository;
import com.comzhichi.repository.CourseRepository;
import com.comzhichi.repository.LevelEvaluationRepository;
import com.comzhichi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelEvaluationService {
    private final LevelEvaluationRepository evaluationRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LevelEvaluationMapper evaluationMapper;
    private final NotificationService notificationService;
    private final StudentParentRepository studentParentRepository;

    @Transactional(readOnly = true)
    public List<LevelEvaluationResponseDTO> findAll(Long studentId) {
        List<LevelEvaluation> list = studentId == null ? evaluationRepository.findAll()
                : evaluationRepository.findByStudentId(studentId);
        return list.stream().map(evaluationMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public LevelEvaluationResponseDTO findById(Long id) {
        return evaluationMapper.toResponse(evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación de nivel no encontrada con ID: " + id)));
    }

    @Transactional
    public LevelEvaluationResponseDTO create(LevelEvaluationRequestDTO dto) {
        LevelEvaluation evaluation = evaluationMapper.toEntity(dto);
        evaluation.setStudent(studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + dto.studentId())));
        evaluation.setCourse(courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + dto.courseId())));
        LevelEvaluation saved = evaluationRepository.save(evaluation);
        notificationService.notify(saved.getStudent().getUser(),
                "Resultado de evaluación: " + saved.getResult());
        studentParentRepository.findByStudentId(saved.getStudent().getId())
                .forEach(link -> notificationService.notify(link.getParent().getUser(),
                        "Resultado académico del estudiante: " + saved.getResult()));
        return evaluationMapper.toResponse(saved);
    }

    @Transactional
    public LevelEvaluationResponseDTO update(Long id, LevelEvaluationRequestDTO dto) {
        LevelEvaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación de nivel no encontrada con ID: " + id));
        evaluation.setDate(dto.date());
        evaluation.setResult(dto.result());
        return evaluationMapper.toResponse(evaluationRepository.save(evaluation));
    }

    @Transactional
    public void delete(Long id) {
        LevelEvaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación de nivel no encontrada con ID: " + id));
        evaluationRepository.delete(evaluation);
    }
}
