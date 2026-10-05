package com.comzhichi.controller;

import com.comzhichi.dto.request.StudentParentRequestDTO;
import com.comzhichi.dto.response.StudentParentResponseDTO;
import com.comzhichi.service.StudentParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/student-parents")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class StudentParentController {

    private final StudentParentService studentParentService;

    @GetMapping
    public List<StudentParentResponseDTO> findAll() {
        return studentParentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentParentResponseDTO findById(@PathVariable Long id) {
        return studentParentService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentParentResponseDTO create(@Valid @RequestBody StudentParentRequestDTO dto) {
        return studentParentService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public StudentParentResponseDTO update(@PathVariable Long id, @Valid @RequestBody StudentParentRequestDTO dto) {
        return studentParentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentParentService.delete(id);
    }
}