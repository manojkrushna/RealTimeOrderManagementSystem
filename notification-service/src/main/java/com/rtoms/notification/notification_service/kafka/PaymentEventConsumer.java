package com.rtoms.notification.notification_service.kafka;

import com.rtoms.notification.notification_service.dto.PaymentEvent;
import com.rtoms.notification.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:19
 */

@Component
public class PaymentEventConsumer {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "payment.completed", groupId = "notification-group")
    public void handlePaymentCompleted(PaymentEvent event) {
        String msg = "Your order " + event.getOrderId() + " has been paid successfully.";
        notificationService.sendNotification(event.getOrderId(), msg);

    }
}