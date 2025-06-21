package com.rtoms.inventory.inventory_service.kafka;

import com.rtoms.inventory.inventory_service.dto.InventoryUpdateEvent;
import com.rtoms.inventory.inventory_service.dto.OrderEvent;
import com.rtoms.inventory.inventory_service.service.InventoryService;
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

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order.created", groupId = "inventory-group")
    public void handleOrderCreated(OrderEvent event) {
        boolean inStock = inventoryService.isInStock(event.getProductId(), event.getQuantity());

        if (inStock) {
            inventoryService.deductStock(event.getProductId(), event.getQuantity());
            kafkaTemplate.send("inventory.updated", new InventoryUpdateEvent(event.getOrderId(), "SUCCESS"));
        } else {
            kafkaTemplate.send("inventory.updated", new InventoryUpdateEvent(event.getOrderId(), "FAILED"));
        }
    }

}
