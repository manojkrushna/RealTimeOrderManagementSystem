package com.rtoms.product.product_service.repository;

import com.rtoms.product.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:53
 */

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByNameContainingIgnoreCase(String name);

}
