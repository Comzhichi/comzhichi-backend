package com.comzhichi.controller;

import com.comzhichi.dto.request.ChangePasswordRequestDTO;
import com.comzhichi.dto.request.LoginRequestDTO;
import com.comzhichi.dto.request.UserRequestDTO;
import com.comzhichi.dto.response.AuthResponseDTO;
import com.comzhichi.security.UserPrincipal;
import com.comzhichi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@AuthenticationPrincipal UserPrincipal principal,
                               @Valid @RequestBody ChangePasswordRequestDTO dto) {
        authService.changePassword(principal, dto);
    }

}