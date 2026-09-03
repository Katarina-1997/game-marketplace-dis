package com.gamemarketplace.storecompositeservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.gamemarketplace.storecompositeservice.dto.GameDto;

@Component
public class CatalogServiceClient {

	private static final String CATALOG_SERVICE_URL = "http://catalog-service/api/games";

    private final RestTemplate restTemplate;

    public CatalogServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GameDto getGameById(String gameId) {
        return restTemplate.getForObject(CATALOG_SERVICE_URL + "/" + gameId, GameDto.class);
    }

    public GameDto[] getAllGames() {
        return restTemplate.getForObject(CATALOG_SERVICE_URL, GameDto[].class);
    }
}