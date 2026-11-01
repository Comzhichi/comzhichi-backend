package com.comzhichi.controller;

import com.comzhichi.dto.request.ParentRequestDTO;
import com.comzhichi.dto.response.ParentResponseDTO;
import com.comzhichi.dto.response.StudentParentResponseDTO;
import com.comzhichi.security.UserPrincipal;
import com.comzhichi.service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ParentController {

    private final ParentService parentService;

    @GetMapping
    public List<ParentResponseDTO> findAll() {
        return parentService.findAll();
    }

    @GetMapping("/{id}")
    public ParentResponseDTO findById(@PathVariable Long id) {
        return parentService.findById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('PARENT')")
    public ParentResponseDTO me(@AuthenticationPrincipal UserPrincipal principal) {
        return parentService.findByUserId(principal.getUser().getId());
    }

    @GetMapping("/me/students")
    @PreAuthorize("hasRole('PARENT')")
    public List<StudentParentResponseDTO> myStudents(@AuthenticationPrincipal UserPrincipal principal) {
        return parentService.findMyStudents(principal.getUser().getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public ParentResponseDTO create(@Valid @RequestBody ParentRequestDTO dto) {
        return parentService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ParentResponseDTO update(@PathVariable Long id, @Valid @RequestBody ParentRequestDTO dto) {
        return parentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        parentService.delete(id);
    }
}