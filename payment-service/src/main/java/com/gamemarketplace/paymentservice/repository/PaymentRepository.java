package com.gamemarketplace.paymentservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gamemarketplace.paymentservice.entity.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {

    Optional<Payment> findByOrderId(String orderId);

    List<Payment> findByUserId(String userId);
}
