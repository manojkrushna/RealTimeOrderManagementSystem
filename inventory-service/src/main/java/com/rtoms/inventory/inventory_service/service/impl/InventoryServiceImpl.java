package com.rtoms.inventory.inventory_service.service.impl;

import com.rtoms.inventory.inventory_service.repository.InventoryRepository;
import com.rtoms.inventory.inventory_service.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:58
 */

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public boolean isInStock(UUID productId, int quantity) {
        return inventoryRepository.findByProductId(productId)
                .map(inv -> inv.getAvailableQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    public void deductStock(UUID productId, int quantity) {
        inventoryRepository.findByProductId(productId).ifPresent(inv -> {
            inv.setAvailableQuantity(inv.getAvailableQuantity() - quantity);
            inventoryRepository.save(inv);
        });
    }
}
