package com.rtoms.product.product_service.service.impl;

import com.rtoms.product.product_service.entity.Product;
import com.rtoms.product.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

/**
 * @author Manoj Krushna Mohanta
 * 22-06-2025 22:46
 */

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        redisTemplate = mock(RedisTemplate.class);
        valueOperations = mock(ValueOperations.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        productService = new ProductServiceImpl();
        // Use reflection to inject mocks since fields are private and @Autowired
        inject(productService, "productRepository", productRepository);
        inject(productService, "redisTemplate", redisTemplate);
    }

    // Helper for reflection field injection
    private void inject(Object target, String field, Object value) {
        try {
            var f = target.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllProducts_CacheHit() {
        List<Product> cachedProducts = Arrays.asList(new Product());
        when(valueOperations.get("PRODUCTS")).thenReturn(cachedProducts);

        List<Product> result = productService.getAllProducts();

        assertSame(cachedProducts, result);
        verify(productRepository, never()).findAll();
        verify(valueOperations, never()).set(anyString(), any(), any(Duration.class));
    }

    @Test
    void testGetAllProducts_CacheMiss() {
        when(valueOperations.get("PRODUCTS")).thenReturn(null);
        List<Product> dbProducts = Arrays.asList(
                new Product(UUID.randomUUID(), "A", "B", BigDecimal.ONE, 1)
        );
        when(productRepository.findAll()).thenReturn(dbProducts);

        List<Product> result = productService.getAllProducts();

        assertEquals(dbProducts, result);
        verify(productRepository).findAll();
        verify(valueOperations).set(eq("PRODUCTS"), eq(dbProducts), eq(Duration.ofMinutes(10)));
    }

    @Test
    void testCreateProduct() {
        Product input = new Product();
        Product saved = new Product();
        when(productRepository.save(input)).thenReturn(saved);

        Product result = productService.createProduct(input);

        assertSame(saved, result);
        verify(productRepository).save(input);
        verify(redisTemplate).delete("PRODUCTS");
    }
}