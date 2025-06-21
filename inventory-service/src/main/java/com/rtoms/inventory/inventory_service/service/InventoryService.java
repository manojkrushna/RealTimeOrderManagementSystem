package com.rtoms.inventory.inventory_service.service;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:58
 */

public interface InventoryService {

    boolean isInStock(UUID productId, int quantity);

    void deductStock(UUID productId, int quantity);
}
