package com.gamemarketplace.orderservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gamemarketplace.orderservice.entity.Order;
import com.gamemarketplace.orderservice.entity.OrderStatus;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);

    List<Order> findByStatus(OrderStatus status);
}
