package com.comzhichi.controller;

import com.comzhichi.dto.request.LevelEvaluationRequestDTO;
import com.comzhichi.dto.response.LevelEvaluationResponseDTO;
import com.comzhichi.service.LevelEvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level-evaluations")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class LevelEvaluationController {
    private final LevelEvaluationService evaluationService;

    @GetMapping
    public List<LevelEvaluationResponseDTO> findAll(
            @RequestParam(required = false) Long studentId) {
        return evaluationService.findAll(studentId);
    }

    @GetMapping("/{id}")
    public LevelEvaluationResponseDTO findById(@PathVariable Long id) {
        return evaluationService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'COORDINATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public LevelEvaluationResponseDTO create(@Valid @RequestBody LevelEvaluationRequestDTO dto) {
        return evaluationService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'COORDINATOR')")
    public LevelEvaluationResponseDTO update(
            @PathVariable Long id, @Valid @RequestBody LevelEvaluationRequestDTO dto) {
        return evaluationService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        evaluationService.delete(id);
    }
}
