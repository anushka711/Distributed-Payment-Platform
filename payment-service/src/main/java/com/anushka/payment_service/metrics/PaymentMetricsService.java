package com.anushka.payment_service.metrics;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class PaymentMetricsService {

    private final Counter paymentsCreated;
    private final Counter paymentsSuccess;
    private final Counter paymentsFailed;
    private final Counter paymentsRetried;

    public PaymentMetricsService(
            MeterRegistry meterRegistry) {

        this.paymentsCreated =
                meterRegistry.counter(
                        "payments.created");

        this.paymentsSuccess =
                meterRegistry.counter(
                        "payments.success");

        this.paymentsFailed =
                meterRegistry.counter(
                        "payments.failed");

        this.paymentsRetried =
                meterRegistry.counter(
                        "payments.retried");
    }

    public void incrementCreated() {
        paymentsCreated.increment();
         System.out.println("Payment created metric incremented");
    }

    public void incrementSuccess() {
        paymentsSuccess.increment();
         System.out.println("Payment success metric incremented");
    }

    public void incrementFailed() {
        paymentsFailed.increment();
         System.out.println("Payment failed metric incremented");
    }

    public void incrementRetried() {
        paymentsRetried.increment();
         System.out.println("Payment retried metric incremented");
    }
}