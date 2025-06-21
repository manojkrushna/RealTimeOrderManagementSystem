package com.rtoms.notification.notification_service.controller;

import com.rtoms.notification.notification_service.entity.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:12
 */

@RequestMapping("/v1/notifications")
public interface NotificationController {

    @GetMapping("/{orderId}")
    ResponseEntity<List<Notification>> getByOrder(@PathVariable UUID orderId);

}
