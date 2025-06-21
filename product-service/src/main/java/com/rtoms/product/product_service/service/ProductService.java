package com.rtoms.product.product_service.service;

import com.rtoms.product.product_service.entity.Product;

import java.util.List;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:54
 */

public interface ProductService {

    List<Product> getAllProducts();

    Product createProduct(Product product);
}
