package com.rtoms.product.product_service.service;

import com.rtoms.product.product_service.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Manoj Krushna Mohanta
 * 22-06-2025 22:46
 */

public interface ProductServiceTest {

    ProductService getProductService();

    @Test
    default void testGetAllProducts_NotNull() {
        List<Product> products = getProductService().getAllProducts();
        assertNotNull(products, "getAllProducts() should not return null");
    }

    @Test
    default void testCreateProduct_NotNull() {
        Product product = new Product();
        Product created = getProductService().createProduct(product);
        assertNotNull(created, "createProduct() should not return null");
    }
}