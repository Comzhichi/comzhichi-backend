package com.comzhichi.controller;

import com.comzhichi.dto.response.ImageResponseDTO;
import com.comzhichi.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final StorageService storageService;

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponseDTO> upload(
            @RequestParam("file") MultipartFile file) {

        String url = storageService.upload(file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ImageResponseDTO(url));
    }
}