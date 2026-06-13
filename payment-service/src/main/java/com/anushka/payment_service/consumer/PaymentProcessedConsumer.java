package com.anushka.payment_service.consumer;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.anushka.payment_service.dto.event.PaymentProcessedEvent;
import com.anushka.payment_service.dto.event.PaymentRetryEvent;
import com.anushka.payment_service.entity.Payment;
import com.anushka.payment_service.entity.PaymentTransaction;
import com.anushka.payment_service.enums.PaymentStatus;
import com.anushka.payment_service.metrics.PaymentMetricsService;
import com.anushka.payment_service.producer.PaymentRetryProducer;
import com.anushka.payment_service.repository.PaymentRepository;
import com.anushka.payment_service.repository.PaymentTransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProcessedConsumer {

    private final PaymentRepository repository;
    private final PaymentTransactionRepository transactionRepository;
    private final PaymentRetryProducer retryProducer;
    private final PaymentMetricsService metricsService;  

    @KafkaListener(
            topics = "payment-processed",
            groupId = "payment-service-group"
    )
    public void consume(PaymentProcessedEvent event) {

        Payment payment = repository.findById(event.paymentId())
                .orElseThrow();

        // Retry logic
        if (event.status() == PaymentStatus.FAILED
                && event.retryCount() < 3) {

            transactionRepository.save(
                    PaymentTransaction.builder()
                            .paymentId(event.paymentId())
                            .eventType("PAYMENT_RETRY")
                            .status("RETRY_" + (event.retryCount() + 1))
                            .createdAt(LocalDateTime.now())
                            .build()
            );
            
            retryProducer.publish(
                    new PaymentRetryEvent(
                          event.paymentId(),
                            event.retryCount() + 1
                    )
            );
            metricsService.incrementRetried();
            log.info(
                    "Retrying payment {} attempt {}",
                    event.paymentId(),
                    event.retryCount() + 1
            );

            return;
        }

        // Final status update
        payment.setStatus(event.status());
        repository.save(payment);

        transactionRepository.save(
                PaymentTransaction.builder()
                        .paymentId(payment.getPaymentId())
                        .eventType(
                                event.status() == PaymentStatus.SUCCESS
                                        ? "PAYMENT_PROCESSED"
                                        : "PAYMENT_FAILED"
                        )
                        .status(payment.getStatus().name())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        metricsService.incrementRetried();

        log.info(
                "Updated payment {} to {}",
                payment.getPaymentId(),
                payment.getStatus()
        );
    }
}