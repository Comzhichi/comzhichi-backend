package com.comzhichi.controller;

import com.comzhichi.dto.request.EnrollmentRequestDTO;
import com.comzhichi.dto.response.EnrollmentResponseDTO;
import com.comzhichi.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    public List<EnrollmentResponseDTO> findAll(@RequestParam(required = false) Long studentId) {
        return enrollmentService.findAll(studentId);
    }

    @GetMapping("/{id}")
    public EnrollmentResponseDTO findById(@PathVariable Long id) {
        return enrollmentService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'COORDINATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponseDTO create(@Valid @RequestBody EnrollmentRequestDTO dto) {
        return enrollmentService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public EnrollmentResponseDTO update(@PathVariable Long id, @Valid @RequestBody EnrollmentRequestDTO dto) {
        return enrollmentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        enrollmentService.delete(id);
    }
}