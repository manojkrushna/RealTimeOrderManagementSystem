package com.rtoms.payment.payment_service.service;

import com.rtoms.payment.payment_service.entity.Payment;

import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:19
 */

public interface PaymentService {

    Payment processPayment(UUID orderId);
}
