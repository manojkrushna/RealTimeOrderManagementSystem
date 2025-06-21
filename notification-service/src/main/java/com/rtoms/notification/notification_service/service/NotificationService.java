package com.rtoms.notification.notification_service.service;

import com.rtoms.notification.notification_service.entity.Notification;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:09
 */
public interface NotificationService {

    Notification sendNotification(UUID orderId, String message);
}
