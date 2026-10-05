package com.comzhichi.controller;

import com.comzhichi.dto.request.SectionRequestDTO;
import com.comzhichi.dto.response.SectionResponseDTO;
import com.comzhichi.service.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class SectionController {

    private final SectionService sectionService;

    @GetMapping
    public List<SectionResponseDTO> findAll(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String schedule) {
        return sectionService.findAll(courseId, available, schedule);
    }

    @GetMapping("/{id}")
    public SectionResponseDTO findById(@PathVariable Long id) {
        return sectionService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public SectionResponseDTO create(@Valid @RequestBody SectionRequestDTO dto) {
        return sectionService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public SectionResponseDTO update(@PathVariable Long id, @Valid @RequestBody SectionRequestDTO dto) {
        return sectionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        sectionService.delete(id);
    }
}