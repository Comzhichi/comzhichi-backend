package com.comzhichi.service;

import com.comzhichi.dto.request.AttendanceRequestDTO;
import com.comzhichi.dto.response.AttendanceResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.AttendanceMapper;
import com.comzhichi.model.Attendance;
import com.comzhichi.model.AttendanceStatus;
import com.comzhichi.model.Enrollment;
import com.comzhichi.repository.AttendanceRepository;
import com.comzhichi.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceMapper attendanceMapper;

    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> findAll(Long enrollmentId) {
        List<Attendance> list = (enrollmentId == null)
                ? attendanceRepository.findAll()
                : attendanceRepository.findByEnrollmentId(enrollmentId);

        return list.stream().map(attendanceMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AttendanceResponseDTO findById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asistencia no encontrada con ID: " + id));
        return attendanceMapper.toResponse(attendance);
    }

    @Transactional
    public AttendanceResponseDTO create(AttendanceRequestDTO dto) {
        Enrollment enrollment = enrollmentRepository.findById(dto.enrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + dto.enrollmentId()));

        Attendance attendance = attendanceMapper.toEntity(dto);
        attendance.setEnrollment(enrollment);

        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    @Transactional
    public AttendanceResponseDTO update(Long id, AttendanceRequestDTO dto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asistencia no encontrada con ID: " + id));

        attendance.setDate(dto.date());
        if (dto.status() != null && !dto.status().isBlank()) {
            try {
                attendance.setStatus(AttendanceStatus.valueOf(dto.status().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new BusinessRuleException("Estado de asistencia no válido: " + dto.status());
            }
        }
        attendance.setObservation(dto.observation());

        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    @Transactional
    public void delete(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asistencia no encontrada con ID: " + id));
        attendanceRepository.delete(attendance);
    }
}
