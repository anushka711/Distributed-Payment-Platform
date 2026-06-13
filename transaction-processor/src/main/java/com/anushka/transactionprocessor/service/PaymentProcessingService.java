package com.anushka.transactionprocessor.service;

import com.anushka.transactionprocessor.dto.event.PaymentProcessedEvent;
import com.anushka.transactionprocessor.enums.PaymentStatus;
import com.anushka.transactionprocessor.producer.PaymentProcessedProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProcessingService {

    private final PaymentProcessedProducer producer;

    public void processPayment(
            UUID paymentId,
            int retryCount) {

        Random random = new Random();

        PaymentStatus status =
                random.nextInt(100) < 70
                        ? PaymentStatus.SUCCESS
                        : PaymentStatus.FAILED;

        producer.publish(
                new PaymentProcessedEvent(
                        paymentId,
                        status,
                        retryCount
                )
        );

        log.info(
                "Processed payment {} with status {} retryCount={}",
                paymentId,
                status,
                retryCount
        );
    }
}