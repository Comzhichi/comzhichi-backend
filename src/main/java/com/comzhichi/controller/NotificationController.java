package com.comzhichi.controller;

import com.comzhichi.model.Notification;
import com.comzhichi.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public List<Notification> findByUser(@PathVariable Long userId) {
        return notificationService.findByUser(userId);
    }
}
