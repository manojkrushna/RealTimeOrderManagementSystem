package com.rtoms.product.product_service.controller;

import com.rtoms.product.product_service.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:56
 */

@RequestMapping("/v1/products")
public interface ProductController {

    @GetMapping
    ResponseEntity<List<Product>> getAll();

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Product> create(@RequestBody Product product);
}