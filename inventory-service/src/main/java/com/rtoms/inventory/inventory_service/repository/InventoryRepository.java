package com.rtoms.inventory.inventory_service.repository;

import com.rtoms.inventory.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:58
 */

public interface InventoryRepository  extends JpaRepository<Inventory, UUID> {

    Optional<Inventory> findByProductId(UUID productId);

}
