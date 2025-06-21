package com.rtoms.product.product_service.controller.impl;

import com.rtoms.product.product_service.controller.ProductController;
import com.rtoms.product.product_service.entity.Product;
import com.rtoms.product.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:58
 */

@RestController
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Override
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

}
