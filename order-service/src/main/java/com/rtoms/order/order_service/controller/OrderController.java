package com.rtoms.order.order_service.controller;

import com.rtoms.order.order_service.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:57
 */

@RequestMapping("/v1/orders")
public interface OrderController {

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order);

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID id);

}
