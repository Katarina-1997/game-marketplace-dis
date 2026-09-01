package com.gamemarketplace.storecompositeservice.service;

import org.springframework.stereotype.Service;

import com.gamemarketplace.storecompositeservice.client.CatalogServiceClient;
import com.gamemarketplace.storecompositeservice.client.InventoryServiceClient;
import com.gamemarketplace.storecompositeservice.client.OrderServiceClient;
import com.gamemarketplace.storecompositeservice.dto.GameDetailsDto;
import com.gamemarketplace.storecompositeservice.dto.GameDto;
import com.gamemarketplace.storecompositeservice.dto.OrderDto;
import com.gamemarketplace.storecompositeservice.dto.OwnedGameDto;

@Service
public class StoreCompositeService {

    private final CatalogServiceClient catalogServiceClient;
    private final InventoryServiceClient inventoryServiceClient;
    private final OrderServiceClient orderServiceClient;

    public StoreCompositeService(CatalogServiceClient catalogServiceClient,
            InventoryServiceClient inventoryServiceClient,
            OrderServiceClient orderServiceClient) {
        this.catalogServiceClient = catalogServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
        this.orderServiceClient = orderServiceClient;
    }

    public GameDetailsDto getGameDetails(String gameId, String userId) {
        GameDto game = catalogServiceClient.getGameById(gameId);
        boolean owned = inventoryServiceClient.userOwnsGame(userId, gameId);
        return new GameDetailsDto(game, owned);
    }

    public OwnedGameDto[] getUserLibrary(String userId) {
        return inventoryServiceClient.getUserLibrary(userId);
    }

    public OrderDto purchaseGame(String userId, String gameId) {
        GameDto game = catalogServiceClient.getGameById(gameId);

        OrderDto order = new OrderDto();
        order.setUserId(userId);
        order.setGameId(gameId);
        order.setGameTitle(game.getTitle());
        order.setPrice(game.getPrice());

        return orderServiceClient.createOrder(order);
    }
}
