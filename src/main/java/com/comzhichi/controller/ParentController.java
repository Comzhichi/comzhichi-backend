package com.comzhichi.controller;

import com.comzhichi.dto.request.ParentRequestDTO;
import com.comzhichi.dto.response.ParentResponseDTO;
import com.comzhichi.service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParentResponseDTO create(@Valid @RequestBody ParentRequestDTO dto) {
        return parentService.create(dto);
    }

    @PutMapping("/{id}")
    public ParentResponseDTO update(@PathVariable Long id, @Valid @RequestBody ParentRequestDTO dto) {
        return parentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        parentService.delete(id);
    }
}