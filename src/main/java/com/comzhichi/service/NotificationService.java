package com.comzhichi.service;

import com.comzhichi.model.Notification;
import com.comzhichi.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public List<Notification> findByUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public void notify(com.comzhichi.model.User user, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }
}
