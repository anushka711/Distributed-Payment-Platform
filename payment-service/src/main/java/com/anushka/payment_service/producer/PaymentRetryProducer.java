package com.anushka.payment_service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.anushka.payment_service.dto.event.PaymentRetryEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentRetryProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(
            PaymentRetryEvent event) {

        kafkaTemplate.send(
                "payment-retry",
                event.paymentId().toString(),
                event
        );
    }
}