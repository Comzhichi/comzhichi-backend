package com.comzhichi.controller;

import com.comzhichi.dto.request.CourseRequestDTO;
import com.comzhichi.dto.response.CourseResponseDTO;
import com.comzhichi.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseResponseDTO> findAll(@RequestParam(required = false) String name) {
        return courseService.findAll(name);
    }

    @GetMapping("/{id}")
    public CourseResponseDTO findById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponseDTO create(@Valid @RequestBody CourseRequestDTO dto) {
        return courseService.create(dto);
    }

    @PutMapping("/{id}")
    public CourseResponseDTO update(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO dto) {
        return courseService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}