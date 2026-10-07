package com.comzhichi.controller;

import com.comzhichi.dto.request.StudentRequestDTO;
import com.comzhichi.dto.response.StudentResponseDTO;
import com.comzhichi.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentResponseDTO> findAll(@RequestParam(required = false) String level) {
        return studentService.findAll(level);
    }

    @GetMapping("/{id}")
    public StudentResponseDTO findById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDTO create(@Valid @RequestBody StudentRequestDTO dto) {
        return studentService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public StudentResponseDTO update(@PathVariable Long id, @Valid @RequestBody StudentRequestDTO dto) {
        return studentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }
}