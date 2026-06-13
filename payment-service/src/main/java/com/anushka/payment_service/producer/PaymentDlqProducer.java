package com.anushka.payment_service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.anushka.payment_service.dto.event.PaymentProcessedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentDlqProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(
            PaymentProcessedEvent event) {

        kafkaTemplate.send(
                "payment-dlq",
                event.paymentId().toString(),
                event
        );

        log.error(
                "Moved payment {} to DLQ",
                event.paymentId()
        );
    }
}