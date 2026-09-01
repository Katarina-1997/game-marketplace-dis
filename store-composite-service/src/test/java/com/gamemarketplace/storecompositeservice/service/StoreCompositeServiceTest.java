package com.gamemarketplace.storecompositeservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gamemarketplace.storecompositeservice.client.CatalogServiceClient;
import com.gamemarketplace.storecompositeservice.client.InventoryServiceClient;
import com.gamemarketplace.storecompositeservice.client.OrderServiceClient;
import com.gamemarketplace.storecompositeservice.dto.GameDetailsDto;
import com.gamemarketplace.storecompositeservice.dto.GameDto;
import com.gamemarketplace.storecompositeservice.dto.OrderDto;

@ExtendWith(MockitoExtension.class)
class StoreCompositeServiceTest {

    @Mock
    private CatalogServiceClient catalogServiceClient;

    @Mock
    private InventoryServiceClient inventoryServiceClient;

    @Mock
    private OrderServiceClient orderServiceClient;

    private StoreCompositeService storeCompositeService;

    @BeforeEach
    void setUp() {
        storeCompositeService = new StoreCompositeService(
                catalogServiceClient, inventoryServiceClient, orderServiceClient);
    }

    @Test
    void getGameDetails_shouldCombineGameAndOwnershipInfo() {
        GameDto game = new GameDto();
        game.setId("game1");
        game.setTitle("The Witcher 3");
        when(catalogServiceClient.getGameById("game1")).thenReturn(game);
        when(inventoryServiceClient.userOwnsGame("user1", "game1")).thenReturn(true);

        GameDetailsDto result = storeCompositeService.getGameDetails("game1", "user1");

        assertEquals("The Witcher 3", result.getGame().getTitle());
        assertTrue(result.isOwnedByUser());
    }

    @Test
    void purchaseGame_shouldCreateOrderWithCurrentGamePrice() {
        GameDto game = new GameDto();
        game.setId("game1");
        game.setTitle("Portal 2");
        game.setPrice(9.99);
        when(catalogServiceClient.getGameById("game1")).thenReturn(game);

        OrderDto createdOrder = new OrderDto();
        createdOrder.setGameTitle("Portal 2");
        createdOrder.setPrice(9.99);
        when(orderServiceClient.createOrder(any(OrderDto.class))).thenReturn(createdOrder);

        OrderDto result = storeCompositeService.purchaseGame("user1", "game1");

        assertEquals("Portal 2", result.getGameTitle());
        assertEquals(9.99, result.getPrice());
    }
}
