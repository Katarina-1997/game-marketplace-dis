package com.gamemarketplace.paymentservice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.gamemarketplace.paymentservice.entity.Payment;
import com.gamemarketplace.paymentservice.entity.PaymentStatus;
import com.gamemarketplace.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Payment payment) {
        boolean success = simulatePaymentProcessing(payment);

        payment.setStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        payment.setProcessedAt(LocalDateTime.now().toString());

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order: " + orderId));
    }

    private boolean simulatePaymentProcessing(Payment payment) {
        // Simulacija obrade placanja - u realnom sistemu bi ovde isla
        // integracija sa spoljnim payment gateway-om (Stripe, PayPal, itd.)
        // Za potrebe projekta, placanje uvek uspeva ako je iznos pozitivan.
        return payment.getAmount() > 0;
    }
}
