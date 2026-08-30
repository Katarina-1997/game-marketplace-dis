package com.gamemarketplace.inventoryservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gamemarketplace.inventoryservice.entity.OwnedGame;

public interface OwnedGameRepository extends MongoRepository<OwnedGame, String> {

    List<OwnedGame> findByUserId(String userId);

    boolean existsByUserIdAndGameId(String userId, String gameId);
}
