package com.rtoms.payment.payment_service.controller;

import com.rtoms.payment.payment_service.entity.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:24
 */

@RequestMapping("/v1/payments")
public interface PaymentController {

    @GetMapping("/{orderId}")
    ResponseEntity<Payment> getPayment(@PathVariable UUID orderId);
}
