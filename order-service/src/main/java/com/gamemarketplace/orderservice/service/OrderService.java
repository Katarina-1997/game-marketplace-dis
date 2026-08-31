package com.gamemarketplace.orderservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gamemarketplace.orderservice.entity.Order;
import com.gamemarketplace.orderservice.entity.OrderStatus;
import com.gamemarketplace.orderservice.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now().toString());
        return orderRepository.save(order);
    }

    public Order markAsPaid(String id) {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.PAID);
        return orderRepository.save(order);
    }

    public Order cancelOrder(String id) {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
