package com.comzhichi.controller;

import com.comzhichi.dto.request.SectionRequestDTO;
import com.comzhichi.dto.response.SectionResponseDTO;
import com.comzhichi.service.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @GetMapping
    public List<SectionResponseDTO> findAll(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Boolean available) {
        return sectionService.findAll(courseId, available);
    }

    @GetMapping("/{id}")
    public SectionResponseDTO findById(@PathVariable Long id) {
        return sectionService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SectionResponseDTO create(@Valid @RequestBody SectionRequestDTO dto) {
        return sectionService.create(dto);
    }

    @PutMapping("/{id}")
    public SectionResponseDTO update(@PathVariable Long id, @Valid @RequestBody SectionRequestDTO dto) {
        return sectionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        sectionService.delete(id);
    }
}