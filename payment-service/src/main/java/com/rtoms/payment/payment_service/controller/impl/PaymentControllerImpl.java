package com.rtoms.payment.payment_service.controller.impl;

import com.rtoms.payment.payment_service.controller.PaymentController;
import com.rtoms.payment.payment_service.entity.Payment;
import com.rtoms.payment.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:25
 */

@RestController
public class PaymentControllerImpl implements PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    public ResponseEntity<Payment> getPayment(@PathVariable UUID orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
