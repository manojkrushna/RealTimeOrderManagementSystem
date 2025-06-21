package com.rtoms.notification.notification_service.kafka;

import com.rtoms.notification.notification_service.dto.PaymentEvent;
import com.rtoms.notification.notification_service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:19
 */

@Component
public class PaymentEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentEventConsumer.class);

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "payment.completed", groupId = "notification-group")
    public void handlePaymentCompleted(PaymentEvent event) {
        logger.info("Received payment.completed event for orderId: {}", event.getOrderId());

        try {
            String msg = "Your order " + event.getOrderId() + " has been paid successfully.";
            notificationService.sendNotification(event.getOrderId(), msg);
            logger.info("Notification sent for orderId: {}", event.getOrderId());
        } catch (Exception e) {
            logger.error("Failed to send notification for orderId {}: {}", event.getOrderId(), e.getMessage(), e);
            // Optional: send to a dead-letter topic, trigger alert, etc.
        }
    }
}