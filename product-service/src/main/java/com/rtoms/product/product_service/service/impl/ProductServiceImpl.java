package com.rtoms.product.product_service.service.impl;

import com.rtoms.product.product_service.entity.Product;
import com.rtoms.product.product_service.repository.ProductRepository;
import com.rtoms.product.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:55
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY = "PRODUCTS";

    public List<Product> getAllProducts() {
        List<Product> cached = (List<Product>) redisTemplate.opsForValue().get(CACHE_KEY);
        if (cached != null) return cached;

        List<Product> products = productRepository.findAll();
        redisTemplate.opsForValue().set(CACHE_KEY, products, Duration.ofMinutes(10));
        return products;
    }

    public Product createProduct(Product product) {
        Product saved = productRepository.save(product);
        redisTemplate.delete(CACHE_KEY);
        return saved;
    }

}
