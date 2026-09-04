package com.gamemarketplace.paymentservice.service;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.gamemarketplace.paymentservice.entity.Payment;
import com.gamemarketplace.paymentservice.entity.PaymentStatus;
import com.gamemarketplace.paymentservice.event.OrderCreatedEvent;
import com.gamemarketplace.paymentservice.event.PaymentProcessedEvent;
import com.gamemarketplace.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StreamBridge streamBridge;

    public PaymentService(PaymentRepository paymentRepository, StreamBridge streamBridge) {
        this.paymentRepository = paymentRepository;
        this.streamBridge = streamBridge;
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

    @Bean
    public Consumer<OrderCreatedEvent> orderCreatedConsumer() {
        return event -> {
            Payment payment = new Payment(event.getOrderId(), event.getUserId(), event.getAmount());
            Payment processedPayment = processPayment(payment);

            PaymentProcessedEvent processedEvent = new PaymentProcessedEvent(
                    event.getOrderId(),
                    event.getUserId(),
                    event.getGameId(),
                    event.getGameTitle(),
                    processedPayment.getStatus().name());

            streamBridge.send("paymentProcessed-out-0", processedEvent);
        };
    }
}