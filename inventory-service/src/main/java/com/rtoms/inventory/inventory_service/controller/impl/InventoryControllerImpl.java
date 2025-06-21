package com.rtoms.inventory.inventory_service.controller.impl;

import com.rtoms.inventory.inventory_service.controller.InventoryController;
import com.rtoms.inventory.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:58
 */

@RestController
public class InventoryControllerImpl implements InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Override
    public ResponseEntity<Boolean> checkStock(UUID productId, int quantity) {
        return ResponseEntity.ok(inventoryService.isInStock(productId, quantity));
    }
}
