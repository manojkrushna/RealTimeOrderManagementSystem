package com.rtoms.inventory.inventory_service.kafka;

import com.rtoms.inventory.inventory_service.dto.InventoryUpdateEvent;
import com.rtoms.inventory.inventory_service.dto.OrderEvent;
import com.rtoms.inventory.inventory_service.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:58
 */

@Component
public class OrderEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order.created", groupId = "inventory-group")
    public void handleOrderCreated(OrderEvent event) {
        logger.info("Received order.created event for orderId: {}", event.getOrderId());

        try {
            boolean inStock = inventoryService.isInStock(event.getProductId(), event.getQuantity());

            if (inStock) {
                inventoryService.deductStock(event.getProductId(), event.getQuantity());
                kafkaTemplate.send("inventory.updated", new InventoryUpdateEvent(event.getOrderId(), "SUCCESS"));
                logger.info("Stock deducted and inventory.updated event sent for orderId: {}", event.getOrderId());
            } else {
                kafkaTemplate.send("inventory.updated", new InventoryUpdateEvent(event.getOrderId(), "FAILED"));
                logger.warn("Insufficient stock for productId: {} (orderId: {})", event.getProductId(), event.getOrderId());
            }
        } catch (Exception e) {
            logger.error("Exception while processing orderId {}: {}", event.getOrderId(), e.getMessage(), e);
            // Optional: Send to a dead-letter topic or notify downstream systems
            kafkaTemplate.send("inventory.updated", new InventoryUpdateEvent(event.getOrderId(), "ERROR"));
        }
    }

}
