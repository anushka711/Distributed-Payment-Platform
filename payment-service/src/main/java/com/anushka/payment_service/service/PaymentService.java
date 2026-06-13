package com.anushka.payment_service.service;

import java.util.UUID;

import com.anushka.payment_service.dto.PaymentRequest;
import com.anushka.payment_service.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(String idempotencyKey, PaymentRequest request);

    PaymentResponse getPayment(UUID paymentId);

    
}