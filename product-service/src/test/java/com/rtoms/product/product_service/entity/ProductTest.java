package com.rtoms.product.product_service.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Manoj Krushna Mohanta
 * 22-06-2025 23:01
 */

class ProductTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        Product product = new Product();
        UUID id = UUID.randomUUID();
        product.setId(id);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setStock(10);

        assertEquals(id, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(BigDecimal.valueOf(99.99), product.getPrice());
        assertEquals(10, product.getStock());
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        Product product = new Product(id, "Name", "Desc", BigDecimal.TEN, 5);

        assertEquals(id, product.getId());
        assertEquals("Name", product.getName());
        assertEquals("Desc", product.getDescription());
        assertEquals(BigDecimal.TEN, product.getPrice());
        assertEquals(5, product.getStock());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        Product p1 = new Product(id, "A", "B", BigDecimal.ONE, 1);
        Product p2 = new Product(id, "A", "B", BigDecimal.ONE, 1);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}