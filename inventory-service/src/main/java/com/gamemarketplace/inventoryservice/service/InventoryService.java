package com.gamemarketplace.inventoryservice.service;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.gamemarketplace.inventoryservice.entity.OwnedGame;
import com.gamemarketplace.inventoryservice.event.PaymentProcessedEvent;
import com.gamemarketplace.inventoryservice.repository.OwnedGameRepository;

@Service
public class InventoryService {

    private final OwnedGameRepository ownedGameRepository;

    public InventoryService(OwnedGameRepository ownedGameRepository) {
        this.ownedGameRepository = ownedGameRepository;
    }

    public List<OwnedGame> getGamesByUser(String userId) {
        return ownedGameRepository.findByUserId(userId);
    }

    public OwnedGame addGameToLibrary(OwnedGame ownedGame) {
        if (ownedGameRepository.existsByUserIdAndGameId(ownedGame.getUserId(), ownedGame.getGameId())) {
            throw new IllegalStateException(
                    "User " + ownedGame.getUserId() + " already owns game " + ownedGame.getGameId());
        }
        return ownedGameRepository.save(ownedGame);
    }

    public void removeGameFromLibrary(String id) {
        ownedGameRepository.deleteById(id);
    }

    public boolean userOwnsGame(String userId, String gameId) {
        return ownedGameRepository.existsByUserIdAndGameId(userId, gameId);
    }

    @Bean
    public Consumer<PaymentProcessedEvent> paymentProcessedConsumer() {
        return event -> {
            if ("SUCCESS".equals(event.getStatus())) {
                boolean alreadyOwned = ownedGameRepository.existsByUserIdAndGameId(
                        event.getUserId(), event.getGameId());

                if (!alreadyOwned) {
                    OwnedGame ownedGame = new OwnedGame(
                            event.getUserId(),
                            event.getGameId(),
                            event.getGameTitle(),
                            java.time.LocalDateTime.now().toString());
                    ownedGameRepository.save(ownedGame);
                }
            }
        };
    }
}