package com.rtoms.order.order_service.controller.impl;

import com.rtoms.order.order_service.controller.OrderController;
import com.rtoms.order.order_service.entity.Order;
import com.rtoms.order.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:59
 */

@RestController
public class OrderControllerImpl implements OrderController {

    @Autowired
    private OrderService service;

    @Override
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.placeOrder(order));
    }

    @Override
    public ResponseEntity<Order> getOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrder(id));
    }

}
