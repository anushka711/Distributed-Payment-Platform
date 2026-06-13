package com.anushka.payment_service.dto;

import java.util.UUID;

public record PaymentResponse(
        UUID paymentId,
        String status
) {
}