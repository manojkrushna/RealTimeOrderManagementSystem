package com.rtoms.order.order_service.service.impl;

import com.rtoms.order.order_service.entity.Order;
import com.rtoms.order.order_service.repository.OrderRepository;
import com.rtoms.order.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:48
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository repo;
    @Autowired private KafkaTemplate<String, Object> kafkaTemplate;

    @Cacheable(value = "orders", key = "#id")
    public Order getOrder(UUID id) {
        return repo.findById(id).orElseThrow();
    }

    public Order placeOrder(Order order) {
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        Order saved = repo.save(order);

        kafkaTemplate.send("order.created", saved);
        return saved;
    }
}
