package com.anushka.payment_service.dto.event;

import java.util.UUID;

public record PaymentRetryEvent(
        UUID paymentId,
        int retryCount
) {
}