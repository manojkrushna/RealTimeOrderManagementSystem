package com.rtoms.inventory.inventory_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:58
 */

@RequestMapping("/v1/inventory")
public interface InventoryController {

    @GetMapping("/check")
    ResponseEntity<Boolean> checkStock(@RequestParam UUID productId, @RequestParam int quantity);
}
