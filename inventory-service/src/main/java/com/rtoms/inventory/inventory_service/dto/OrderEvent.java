package com.rtoms.inventory.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:58
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private UUID orderId;
    private UUID productId;
    private int quantity;

}
