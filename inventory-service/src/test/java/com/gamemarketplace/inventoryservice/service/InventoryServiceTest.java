package com.gamemarketplace.inventoryservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gamemarketplace.inventoryservice.entity.OwnedGame;
import com.gamemarketplace.inventoryservice.event.PaymentProcessedEvent;
import com.gamemarketplace.inventoryservice.repository.OwnedGameRepository;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private OwnedGameRepository ownedGameRepository;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService(ownedGameRepository);
    }

    @Test
    void getGamesByUser_shouldReturnUserLibrary() {
        OwnedGame game1 = new OwnedGame("user1", "game1", "The Witcher 3", "2024-01-10");
        OwnedGame game2 = new OwnedGame("user1", "game2", "Portal 2", "2024-02-15");
        when(ownedGameRepository.findByUserId("user1")).thenReturn(Arrays.asList(game1, game2));

        List<OwnedGame> result = inventoryService.getGamesByUser("user1");

        assertEquals(2, result.size());
        verify(ownedGameRepository).findByUserId("user1");
    }

    @Test
    void addGameToLibrary_shouldSaveGame_whenNotAlreadyOwned() {
        OwnedGame game = new OwnedGame("user1", "game1", "The Witcher 3", "2024-01-10");
        when(ownedGameRepository.existsByUserIdAndGameId("user1", "game1")).thenReturn(false);
        when(ownedGameRepository.save(game)).thenReturn(game);

        OwnedGame result = inventoryService.addGameToLibrary(game);

        assertEquals("The Witcher 3", result.getGameTitle());
        verify(ownedGameRepository).save(game);
    }

    @Test
    void addGameToLibrary_shouldThrowException_whenAlreadyOwned() {
        OwnedGame game = new OwnedGame("user1", "game1", "The Witcher 3", "2024-01-10");
        when(ownedGameRepository.existsByUserIdAndGameId("user1", "game1")).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> inventoryService.addGameToLibrary(game));
    }

    @Test
    void userOwnsGame_shouldReturnTrue_whenUserOwnsGame() {
        when(ownedGameRepository.existsByUserIdAndGameId("user1", "game1")).thenReturn(true);

        boolean result = inventoryService.userOwnsGame("user1", "game1");

        assertTrue(result);
    }

    @Test
    void paymentProcessedConsumer_shouldAddGameToLibrary_whenStatusIsSuccess() {
        PaymentProcessedEvent event = new PaymentProcessedEvent(
                "order1", "user1", "game1", "The Witcher 3", "SUCCESS");
        when(ownedGameRepository.existsByUserIdAndGameId("user1", "game1")).thenReturn(false);

        Consumer<PaymentProcessedEvent> consumer = inventoryService.paymentProcessedConsumer();
        consumer.accept(event);

        verify(ownedGameRepository).save(any(OwnedGame.class));
    }

    @Test
    void paymentProcessedConsumer_shouldNotAddGame_whenStatusIsFailed() {
        PaymentProcessedEvent event = new PaymentProcessedEvent(
                "order1", "user1", "game1", "The Witcher 3", "FAILED");

        Consumer<PaymentProcessedEvent> consumer = inventoryService.paymentProcessedConsumer();
        consumer.accept(event);

        verify(ownedGameRepository, never()).save(any(OwnedGame.class));
    }
}