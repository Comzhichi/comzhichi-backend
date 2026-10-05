package com.comzhichi.controller;

import com.comzhichi.dto.request.AcademicObservationRequestDTO;
import com.comzhichi.dto.response.AcademicObservationResponseDTO;
import com.comzhichi.service.AcademicObservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-observations")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class AcademicObservationController {

    private final AcademicObservationService observationService;

    @GetMapping
    public List<AcademicObservationResponseDTO> findAll(
            @RequestParam(required = false) Long enrollmentId) {
        return observationService.findAll(enrollmentId);
    }

    @GetMapping("/{id}")
    public AcademicObservationResponseDTO findById(@PathVariable Long id) {
        return observationService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'COORDINATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public AcademicObservationResponseDTO create(
            @Valid @RequestBody AcademicObservationRequestDTO dto) {
        return observationService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'COORDINATOR')")
    public AcademicObservationResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody AcademicObservationRequestDTO dto) {
        return observationService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        observationService.delete(id);
    }
}
