package com.anushka.transactionprocessor.dto.event;

import java.util.UUID;

import com.anushka.transactionprocessor.enums.PaymentStatus;

public record PaymentProcessedEvent(
        UUID paymentId,
        PaymentStatus status,
        int retryCount
){}