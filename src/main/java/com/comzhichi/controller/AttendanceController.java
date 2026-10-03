package com.comzhichi.controller;

import com.comzhichi.dto.request.AttendanceRequestDTO;
import com.comzhichi.dto.response.AttendanceResponseDTO;
import com.comzhichi.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
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
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceResponseDTO create(@Valid @RequestBody AttendanceRequestDTO dto) {
        return attendanceService.create(dto);
    }

    @PutMapping("/{id}")
    public AttendanceResponseDTO update(@PathVariable Long id, @Valid @RequestBody AttendanceRequestDTO dto) {
        return attendanceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        attendanceService.delete(id);
    }
}