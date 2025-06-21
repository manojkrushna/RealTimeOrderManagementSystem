package com.rtoms.payment.payment_service.repository;

import com.rtoms.payment.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Manoj Krushna Mohanta
 * 21-06-2025 16:17
 */

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Optional<Payment> findByOrderId(UUID orderId);

}
