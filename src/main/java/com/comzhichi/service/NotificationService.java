package com.comzhichi.service;

import com.comzhichi.dto.response.NotificationResponseDTO;
import com.comzhichi.mapper.NotificationMapper;
import com.comzhichi.model.Notification;
import com.comzhichi.model.User;
import com.comzhichi.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.comzhichi.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> findByUser(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Transactional
    public void notify(User user, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada con ID: " + id));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}