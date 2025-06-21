package com.rtoms.order.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 15:40
 */

@Data
@Entity
@Table(name = "orders")
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private String status;
    private LocalDateTime createdAt;

}
