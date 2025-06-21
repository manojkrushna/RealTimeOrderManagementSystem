package com.rtoms.notification.notification_service.service.impl;

import com.rtoms.notification.notification_service.entity.Notification;
import com.rtoms.notification.notification_service.repository.NotificationRepository;
import com.rtoms.notification.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:10
 */

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository repo;

    @Override
    public Notification sendNotification(UUID orderId, String message) {
        Notification notification = new Notification();
        notification.setOrderId(orderId);
        notification.setMessage(message);
        notification.setSentAt(LocalDateTime.now());

        // Simulate sending (log or email)
        System.out.println("📢 Notification sent: " + message);

        return repo.save(notification);

    }
}
