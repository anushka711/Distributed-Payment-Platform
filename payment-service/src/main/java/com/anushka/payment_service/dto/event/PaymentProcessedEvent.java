package com.anushka.payment_service.dto.event;

import com.anushka.payment_service.enums.PaymentStatus;

import java.util.UUID;

public record PaymentProcessedEvent(
        UUID paymentId,
    PaymentStatus status,
    int retryCount
) {
}