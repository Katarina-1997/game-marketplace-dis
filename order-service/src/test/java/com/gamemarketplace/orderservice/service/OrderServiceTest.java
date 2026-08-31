package com.gamemarketplace.orderservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gamemarketplace.orderservice.entity.Order;
import com.gamemarketplace.orderservice.entity.OrderStatus;
import com.gamemarketplace.orderservice.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository);
    }

    @Test
    void getOrdersByUser_shouldReturnUserOrders() {
        Order order1 = new Order("user1", "game1", "The Witcher 3", 29.99);
        Order order2 = new Order("user1", "game2", "Portal 2", 9.99);
        when(orderRepository.findByUserId("user1")).thenReturn(Arrays.asList(order1, order2));

        List<Order> result = orderService.getOrdersByUser("user1");

        assertEquals(2, result.size());
        verify(orderRepository).findByUserId("user1");
    }

    @Test
    void createOrder_shouldSetStatusToPending() {
        Order order = new Order("user1", "game1", "The Witcher 3", 29.99);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.createOrder(order);

        assertEquals(OrderStatus.PENDING, result.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    void markAsPaid_shouldUpdateStatusToPaid() {
        Order order = new Order("user1", "game1", "The Witcher 3", 29.99);
        order.setId("1");
        when(orderRepository.findById("1")).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.markAsPaid("1");

        assertEquals(OrderStatus.PAID, result.getStatus());
    }

    @Test
    void cancelOrder_shouldUpdateStatusToCancelled() {
        Order order = new Order("user1", "game1", "The Witcher 3", 29.99);
        order.setId("1");
        when(orderRepository.findById("1")).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.cancelOrder("1");

        assertEquals(OrderStatus.CANCELLED, result.getStatus());
    }
}