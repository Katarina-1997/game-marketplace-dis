package com.gamemarketplace.orderservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.gamemarketplace.orderservice.entity.Order;
import com.gamemarketplace.orderservice.entity.OrderStatus;

@SpringBootTest
@Testcontainers
class OrderRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldSaveOrderWithPendingStatus() {
        Order order = new Order("user1", "game1", "The Witcher 3", 29.99);

        Order savedOrder = orderRepository.save(order);

        assertTrue(savedOrder.getId() != null);
        assertEquals(OrderStatus.PENDING, savedOrder.getStatus());
    }

    @Test
    void shouldUpdateOrderStatusToPaid() {
        Order order = new Order("user1", "game1", "The Witcher 3", 29.99);
        Order savedOrder = orderRepository.save(order);

        savedOrder.setStatus(OrderStatus.PAID);
        orderRepository.save(savedOrder);

        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getId());

        assertTrue(foundOrder.isPresent());
        assertEquals(OrderStatus.PAID, foundOrder.get().getStatus());
    }

    @Test
    void shouldFindOrdersByUserId() {
        orderRepository.deleteAll();

        Order order1 = new Order("user1", "game1", "The Witcher 3", 29.99);
        Order order2 = new Order("user1", "game2", "Portal 2", 9.99);
        Order order3 = new Order("user2", "game3", "Cyberpunk 2077", 39.99);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        List<Order> user1Orders = orderRepository.findByUserId("user1");

        assertEquals(2, user1Orders.size());
    }

    @Test
    void shouldFindOrdersByStatus() {
        orderRepository.deleteAll();

        Order pendingOrder = new Order("user1", "game1", "The Witcher 3", 29.99);
        Order paidOrder = new Order("user1", "game2", "Portal 2", 9.99);
        paidOrder.setStatus(OrderStatus.PAID);
        orderRepository.save(pendingOrder);
        orderRepository.save(paidOrder);

        List<Order> paidOrders = orderRepository.findByStatus(OrderStatus.PAID);

        assertEquals(1, paidOrders.size());
        assertEquals("Portal 2", paidOrders.get(0).getGameTitle());
    }
}