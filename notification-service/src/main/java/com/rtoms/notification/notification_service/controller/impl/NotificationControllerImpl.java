package com.rtoms.notification.notification_service.controller.impl;

import com.rtoms.notification.notification_service.controller.NotificationController;
import com.rtoms.notification.notification_service.entity.Notification;
import com.rtoms.notification.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:16
 */

@RestController
public class NotificationControllerImpl implements NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public ResponseEntity<List<Notification>> getByOrder(UUID orderId) {
        return ResponseEntity.ok(notificationRepository.findByOrderId(orderId));

    }
}
