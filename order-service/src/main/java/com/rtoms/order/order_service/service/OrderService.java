package com.rtoms.order.order_service.service;

import com.rtoms.order.order_service.entity.Order;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:46
 */
public interface OrderService {

    Order getOrder(UUID id);

    Order placeOrder(Order order);
}
