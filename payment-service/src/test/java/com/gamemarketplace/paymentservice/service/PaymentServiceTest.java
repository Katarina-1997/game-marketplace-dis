package com.gamemarketplace.paymentservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gamemarketplace.paymentservice.entity.Payment;
import com.gamemarketplace.paymentservice.entity.PaymentStatus;
import com.gamemarketplace.paymentservice.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(paymentRepository);
    }

    @Test
    void processPayment_shouldReturnSuccess_whenAmountIsPositive() {
        Payment payment = new Payment("order1", "user1", 29.99);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.processPayment(payment);

        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
    }

    @Test
    void processPayment_shouldReturnFailed_whenAmountIsZeroOrNegative() {
        Payment payment = new Payment("order2", "user1", 0.0);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.processPayment(payment);

        assertEquals(PaymentStatus.FAILED, result.getStatus());
    }

    @Test
    void getPaymentByOrderId_shouldReturnPayment_whenExists() {
        Payment payment = new Payment("order1", "user1", 29.99);
        payment.setStatus(PaymentStatus.SUCCESS);
        when(paymentRepository.findByOrderId("order1")).thenReturn(Optional.of(payment));

        Payment result = paymentService.getPaymentByOrderId("order1");

        assertEquals("order1", result.getOrderId());
    }

    @Test
    void getPaymentByOrderId_shouldThrowException_whenNotFound() {
        when(paymentRepository.findByOrderId("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paymentService.getPaymentByOrderId("unknown"));
    }
}