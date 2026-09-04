package com.gamemarketplace.orderservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.gamemarketplace.orderservice.entity.Order;
import com.gamemarketplace.orderservice.entity.OrderStatus;
import com.gamemarketplace.orderservice.event.OrderCreatedEvent;
import com.gamemarketplace.orderservice.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StreamBridge streamBridge;

    public OrderService(OrderRepository orderRepository, StreamBridge streamBridge) {
        this.orderRepository = orderRepository;
        this.streamBridge = streamBridge;
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
        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getGameId(),
                savedOrder.getGameTitle(),
                savedOrder.getPrice());

        streamBridge.send("orderCreated-out-0", event);

        return savedOrder;
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