package com.anushka.transactionprocessor.dto.event;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreatedEvent(
        UUID paymentId,
        String merchantId,
        BigDecimal amount,
        String currency) {
}