package com.anushka.transactionprocessor.producer;

import com.anushka.transactionprocessor.dto.event.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProcessedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(
            PaymentProcessedEvent event) {

        kafkaTemplate.send(
                "payment-processed",
                event.paymentId().toString(),
                event
        );

        log.info(
                "Published PAYMENT_PROCESSED for paymentId={}",
                event.paymentId()
        );
    }
}