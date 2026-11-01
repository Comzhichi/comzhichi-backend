package com.comzhichi.controller;

import com.comzhichi.dto.request.TeacherRequestDTO;
import com.comzhichi.dto.response.TeacherResponseDTO;
import com.comzhichi.security.UserPrincipal;
import com.comzhichi.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherResponseDTO> findAll(@RequestParam(required = false) String specialty) {
        return teacherService.findAll(specialty);
    }

    @GetMapping("/eligible")
    public List<TeacherResponseDTO> findEligible(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String dayOfWeek,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        return teacherService.findEligible(courseId, dayOfWeek, startTime, endTime);
    }

    @GetMapping("/{id}")
    public TeacherResponseDTO findById(@PathVariable Long id) {
        return teacherService.findById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('TEACHER')")
    public TeacherResponseDTO me(@AuthenticationPrincipal UserPrincipal principal) {
        return teacherService.findByUserId(principal.getUser().getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponseDTO create(@Valid @RequestBody TeacherRequestDTO dto) {
        return teacherService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public TeacherResponseDTO update(@PathVariable Long id, @Valid @RequestBody TeacherRequestDTO dto) {
        return teacherService.update(id, dto);
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('COORDINATOR')")
    public TeacherResponseDTO deactivate(@PathVariable Long id) {
        return teacherService.deactivate(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        teacherService.delete(id);
    }
}