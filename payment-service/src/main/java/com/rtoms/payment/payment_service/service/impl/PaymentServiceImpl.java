package com.rtoms.payment.payment_service.service.impl;

import com.rtoms.payment.payment_service.dto.PaymentEvent;
import com.rtoms.payment.payment_service.entity.Payment;
import com.rtoms.payment.payment_service.repository.PaymentRepository;
import com.rtoms.payment.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:20
 */

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Payment processPayment(UUID orderId) {

        Payment payment = new Payment();

        payment.setOrderId(orderId);
        payment.setStatus("SUCCESS");
        payment.setPaidAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        kafkaTemplate.send("payment.completed", new PaymentEvent(orderId, "SUCCESS"));
        return saved;
    }

    @Override
    public ResponseEntity<Payment> getPaymentResponseEntity(UUID orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
