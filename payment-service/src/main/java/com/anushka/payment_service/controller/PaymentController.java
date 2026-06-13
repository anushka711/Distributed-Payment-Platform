package com.anushka.payment_service.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anushka.payment_service.dto.PaymentRequest;
import com.anushka.payment_service.dto.PaymentResponse;
import com.anushka.payment_service.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/health")
    public String health() {
        return "UP";
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse>
    createPayment(
        @RequestHeader String idempotencyKey,
        @Valid @RequestBody PaymentRequest request){
        return ResponseEntity.ok(paymentService.createPayment(idempotencyKey,request));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse>
    getPayment(
        @PathVariable UUID paymentId) {

        return ResponseEntity.ok(
                paymentService.getPayment(paymentId)
        );
    }

    @GetMapping("/test")
    public String test() {
        return "working";
    }
}