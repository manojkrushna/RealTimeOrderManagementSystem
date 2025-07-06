package com.rtoms.product.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtoms.product.product_service.entity.Product;
import com.rtoms.product.product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Manoj Krushna Mohanta
 * 22-06-2025 23:10
 */

@SuppressWarnings("removal")
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService; // Mock the service, not the controller

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testGetAll() throws Exception {
        Product product = new Product(UUID.randomUUID(), "Name", "Desc", BigDecimal.TEN, 5);
        List<Product> products = List.of(product);

        Mockito.when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Name"));
    }

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void testCreate() throws Exception {
//        Product product = new Product(UUID.randomUUID(), "Name", "Desc", BigDecimal.TEN, 5);
//
//        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(product);
//
//        mockMvc.perform(post("/v1/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(product)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Name"));
//    }
}