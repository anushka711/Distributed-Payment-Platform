package com.anushka.transactionprocessor.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.anushka.transactionprocessor.dto.event.PaymentRetryEvent;
import com.anushka.transactionprocessor.service.PaymentProcessingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentRetryConsumer {

    private final PaymentProcessingService paymentProcessingService;

    @KafkaListener(
            topics = "payment-retry",
            groupId = "transaction-processor-group"
    )
    public void retry(PaymentRetryEvent event) {

        log.info(
                "Retry attempt {} for payment {}",
                event.retryCount(),
                event.paymentId()
        );

        paymentProcessingService.processPayment(
                event.paymentId(),
                event.retryCount()
        );
    }
}