package com.rtoms.payment.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:18
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateEvent {

    private UUID orderId;
    private String status;
}
