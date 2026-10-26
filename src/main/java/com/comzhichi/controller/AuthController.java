package com.comzhichi.controller;

import com.comzhichi.dto.request.LoginRequestDTO;
import com.comzhichi.dto.request.UserRequestDTO;
import com.comzhichi.dto.response.AuthResponseDTO;
import com.comzhichi.dto.response.LoginResponseDTO;
import com.comzhichi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody UserRequestDTO dto) {

        AuthResponseDTO response = authService.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(authService.login(dto));
    }
}