package com.gamemarketplace.storecompositeservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.gamemarketplace.storecompositeservice.dto.OwnedGameDto;

@Component
public class InventoryServiceClient {

    private static final String INVENTORY_SERVICE_URL = "http://localhost:8082/api/inventory";

    private final RestTemplate restTemplate;

    public InventoryServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean userOwnsGame(String userId, String gameId) {
        Boolean result = restTemplate.getForObject(
                INVENTORY_SERVICE_URL + "/user/" + userId + "/owns/" + gameId, Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    public OwnedGameDto[] getUserLibrary(String userId) {
        return restTemplate.getForObject(INVENTORY_SERVICE_URL + "/user/" + userId, OwnedGameDto[].class);
    }
}
