package com.anushka.payment_service.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.anushka.payment_service.dto.PaymentRequest;
import com.anushka.payment_service.dto.PaymentResponse;
import com.anushka.payment_service.dto.event.PaymentCreatedEvent;
import com.anushka.payment_service.entity.Payment;
import com.anushka.payment_service.entity.PaymentTransaction;
import com.anushka.payment_service.enums.PaymentStatus;
import com.anushka.payment_service.metrics.PaymentMetricsService;
import com.anushka.payment_service.producer.PaymentEventProducer;
import com.anushka.payment_service.repository.PaymentRepository;
import com.anushka.payment_service.repository.PaymentTransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    private final PaymentEventProducer producer;

    private final PaymentTransactionRepository transactionRepository;

    private final PaymentMetricsService metricsService;

    @Override
    @Transactional
    public PaymentResponse createPayment(String idempotencyKey, PaymentRequest request) {

        Optional<Payment> existing = repository.findByIdempotencyKey(idempotencyKey);

        if(existing.isPresent()){
                return new PaymentResponse(
                existing.get().getPaymentId(),
                "Record Already Present");
        }        
        Payment payment =
                Payment.builder()
                        .idempotencyKey(idempotencyKey)
                        .merchantId(request.merchantId())
                        .amount(request.amount())
                        .currency(request.currency())
                        .status(PaymentStatus.PROCESSING)
                        .createdAt(LocalDateTime.now())
                        .build();

        payment = repository.save(payment);

        transactionRepository.save(
        PaymentTransaction.builder()
                .paymentId(payment.getPaymentId())
                .eventType("PAYMENT_CREATED")
                .status(payment.getStatus().name())
                .createdAt(LocalDateTime.now())
                .build()
);

        metricsService.incrementCreated();
        PaymentCreatedEvent event =
        new PaymentCreatedEvent(
                payment.getPaymentId(),
                payment.getMerchantId(),
                payment.getAmount(),
                payment.getCurrency()
        );

        producer.publishPaymentCreated(event);
        
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getStatus().name()
        );
    }

        @Override
        public PaymentResponse getPayment(UUID paymentId) {

        Payment payment =
                repository.findById(paymentId)
                        .orElseThrow();

        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getStatus().name()
        );
        }
        

}