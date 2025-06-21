package com.rtoms.order.order_service.repository;

import com.rtoms.order.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:43
 */

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByStatus(String status);
}
