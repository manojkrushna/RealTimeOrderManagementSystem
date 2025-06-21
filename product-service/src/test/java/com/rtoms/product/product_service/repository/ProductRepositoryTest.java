package com.rtoms.product.product_service.repository;

import com.rtoms.product.product_service.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Manoj Krushna Mohanta
 * 22-06-2025 22:56
 */

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("findByNameContainingIgnoreCase returns matching products")
    void testFindByNameContainingIgnoreCase() {
        Product p1 = new Product();
        p1.setName("Apple iPhone");
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Samsung Galaxy");
        productRepository.save(p2);

        List<Product> result = productRepository.findByNameContainingIgnoreCase("iphone");
        assertEquals(1, result.size());
        assertEquals("Apple iPhone", result.get(0).getName());
    }
}