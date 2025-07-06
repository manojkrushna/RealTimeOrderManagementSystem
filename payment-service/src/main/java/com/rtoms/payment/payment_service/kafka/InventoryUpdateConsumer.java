package com.rtoms.payment.payment_service.kafka;

import com.rtoms.payment.payment_service.dto.InventoryUpdateEvent;
import com.rtoms.payment.payment_service.service.PaymentService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:27
 */

@Component
public class InventoryUpdateConsumer {

    private static final Logger logger = LoggerFactory.getLogger(InventoryUpdateConsumer.class);

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "inventory.updated", groupId = "payment-group")
    public void handleInventoryUpdate(@Payload InventoryUpdateEvent event, ConsumerRecord<String, InventoryUpdateEvent> record) {
        logger.info("Received inventory update: {}", event);

        try {
            if ("SUCCESS".equalsIgnoreCase(event.getStatus())) {
                paymentService.processPayment(event.getOrderId());
                logger.info("Processed payment for orderId: {}", event.getOrderId());
            } else {
                logger.warn("Inventory update was not successful for orderId: {} (status: {})", event.getOrderId(), event.getStatus());
                // TODO: Optionally publish failure event or trigger rollback
            }
        } catch (Exception e) {
            logger.error("Error processing inventory update for orderId: {} — {}", event.getOrderId(), e.getMessage(), e);
            // Optionally send to dead-letter topic or retry logic
        }

        logger.debug("Kafka metadata — partition: {}, offset: {}", record.partition(), record.offset());

    }
}
