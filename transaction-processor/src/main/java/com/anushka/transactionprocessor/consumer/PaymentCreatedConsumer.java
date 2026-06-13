package com.anushka.transactionprocessor.consumer;

import com.anushka.transactionprocessor.dto.event.PaymentCreatedEvent;
import com.anushka.transactionprocessor.service.PaymentProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCreatedConsumer {

    private final PaymentProcessingService paymentProcessingService;

    @KafkaListener(
            topics = "payment-created",
            groupId = "transaction-processor-group"
    )
    public void consume(
            PaymentCreatedEvent event) {

        paymentProcessingService.processPayment(
                event.paymentId(),
                0
        );
    }
}