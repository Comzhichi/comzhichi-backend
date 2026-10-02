package com.comzhichi.controller;

import com.comzhichi.dto.request.TeacherRequestDTO;
import com.comzhichi.dto.response.TeacherResponseDTO;
import com.comzhichi.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherResponseDTO> findAll(@RequestParam(required = false) String specialty) {
        return teacherService.findAll(specialty);
    }

    @GetMapping("/{id}")
    public TeacherResponseDTO findById(@PathVariable Long id) {
        return teacherService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponseDTO create(@Valid @RequestBody TeacherRequestDTO dto) {
        return teacherService.create(dto);
    }

    @PutMapping("/{id}")
    public TeacherResponseDTO update(@PathVariable Long id, @Valid @RequestBody TeacherRequestDTO dto) {
        return teacherService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        teacherService.delete(id);
    }
}