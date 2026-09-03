package com.gamemarketplace.storecompositeservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.gamemarketplace.storecompositeservice.dto.OrderDto;

@Component
public class OrderServiceClient {

	private static final String ORDER_SERVICE_URL = "http://order-service/api/orders";

    private final RestTemplate restTemplate;

    public OrderServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrderDto createOrder(OrderDto order) {
        return restTemplate.postForObject(ORDER_SERVICE_URL, order, OrderDto.class);
    }

    public OrderDto[] getOrdersByUser(String userId) {
        return restTemplate.getForObject(ORDER_SERVICE_URL + "/user/" + userId, OrderDto[].class);
    }
}
