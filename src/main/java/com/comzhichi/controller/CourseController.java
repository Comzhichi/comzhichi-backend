package com.comzhichi.controller;

import com.comzhichi.dto.request.CourseRequestDTO;
import com.comzhichi.dto.response.CourseResponseDTO;
import com.comzhichi.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseResponseDTO> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String schedule,
            @RequestParam(required = false) Boolean available) {
        return courseService.findAll(name, level, schedule, available);
    }

    @GetMapping("/{id}")
    public CourseResponseDTO findById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponseDTO create(@Valid @RequestBody CourseRequestDTO dto) {
        return courseService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public CourseResponseDTO update(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO dto) {
        return courseService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}