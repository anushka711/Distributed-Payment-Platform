package com.anushka.transactionprocessor.dto.event;

import java.util.UUID;

public record PaymentRetryEvent(
        UUID paymentId,
        int retryCount
) {
}