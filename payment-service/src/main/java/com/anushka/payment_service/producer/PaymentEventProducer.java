package com.anushka.payment_service.producer;

import com.anushka.payment_service.dto.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPaymentCreated(
            PaymentCreatedEvent event) {

        kafkaTemplate.send(
                "payment-created",
                event.paymentId().toString(),
                event
        );

        log.info(
                "Published PAYMENT_CREATED event for paymentId={}",
                event.paymentId()
        );
    }
}