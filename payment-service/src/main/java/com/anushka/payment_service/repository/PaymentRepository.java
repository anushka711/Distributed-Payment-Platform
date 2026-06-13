package com.anushka.payment_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anushka.payment_service.entity.Payment;

public interface PaymentRepository
        extends JpaRepository<Payment, UUID> {

        Optional<Payment> findByIdempotencyKey(
        String idempotencyKey);
}