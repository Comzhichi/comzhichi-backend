package com.comzhichi.controller;

import com.comzhichi.dto.request.CoordinatorRequestDTO;
import com.comzhichi.dto.response.CoordinatorResponseDTO;
import com.comzhichi.service.CoordinatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordinators")
@RequiredArgsConstructor
public class CoordinatorController {

    private final CoordinatorService coordinatorService;

    @GetMapping
    public List<CoordinatorResponseDTO> findAll(@RequestParam(required = false) String department) {
        return coordinatorService.findAll(department);
    }

    @GetMapping("/{id}")
    public CoordinatorResponseDTO findById(@PathVariable Long id) {
        return coordinatorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CoordinatorResponseDTO create(@Valid @RequestBody CoordinatorRequestDTO dto) {
        return coordinatorService.create(dto);
    }

    @PutMapping("/{id}")
    public CoordinatorResponseDTO update(@PathVariable Long id, @Valid @RequestBody CoordinatorRequestDTO dto) {
        return coordinatorService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        coordinatorService.delete(id);
    }
}