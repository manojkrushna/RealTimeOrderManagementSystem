package com.rtoms.notification.notification_service.repository;

import com.rtoms.notification.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:08
 */
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByOrderId(UUID orderId);
}
