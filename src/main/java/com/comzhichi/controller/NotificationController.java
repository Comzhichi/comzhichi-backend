package com.comzhichi.controller;

import com.comzhichi.dto.response.NotificationResponseDTO;
import com.comzhichi.security.UserPrincipal;
import com.comzhichi.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/me")
    public List<NotificationResponseDTO> findByUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return notificationService.findByUser(userPrincipal.getUser().getId());
    }

    @PatchMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }
}