package com.comzhichi.controller;

import com.comzhichi.dto.request.EvaluationRequestDTO;
import com.comzhichi.dto.response.EvaluationResponseDTO;
import com.comzhichi.service.EvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @GetMapping
    public List<EvaluationResponseDTO> findAll(@RequestParam(required = false) Long enrollmentId) {
        return evaluationService.findAll(enrollmentId);
    }

    @GetMapping("/{id}")
    public EvaluationResponseDTO findById(@PathVariable Long id) {
        return evaluationService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EvaluationResponseDTO create(@Valid @RequestBody EvaluationRequestDTO dto) {
        return evaluationService.create(dto);
    }

    @PutMapping("/{id}")
    public EvaluationResponseDTO update(@PathVariable Long id, @Valid @RequestBody EvaluationRequestDTO dto) {
        return evaluationService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        evaluationService.delete(id);
    }
}