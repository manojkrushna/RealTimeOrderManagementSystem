package com.rtoms.payment.payment_service.kafka;

import com.rtoms.payment.payment_service.dto.InventoryUpdateEvent;
import com.rtoms.payment.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:27
 */

@Component
public class InventoryUpdateConsumer {

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "inventory.updated", groupId = "payment-group")
    public void handleInventoryUpdate(InventoryUpdateEvent event) {
        if ("SUCCESS".equals(event.getStatus())) {
            paymentService.processPayment(event.getOrderId());
        } else {
            // Optionally publish a failure event
        }
    }
}
