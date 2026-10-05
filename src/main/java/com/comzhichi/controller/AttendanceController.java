package com.comzhichi.controller;

import com.comzhichi.dto.request.AttendanceRequestDTO;
import com.comzhichi.dto.response.AttendanceResponseDTO;
import com.comzhichi.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public List<AttendanceResponseDTO> findAll(@RequestParam(required = false) Long enrollmentId) {
        return attendanceService.findAll(enrollmentId);
    }

    @GetMapping("/{id}")
    public AttendanceResponseDTO findById(@PathVariable Long id) {
        return attendanceService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'COORDINATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceResponseDTO create(@Valid @RequestBody AttendanceRequestDTO dto) {
        return attendanceService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'COORDINATOR')")
    public AttendanceResponseDTO update(@PathVariable Long id, @Valid @RequestBody AttendanceRequestDTO dto) {
        return attendanceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        attendanceService.delete(id);
    }
}