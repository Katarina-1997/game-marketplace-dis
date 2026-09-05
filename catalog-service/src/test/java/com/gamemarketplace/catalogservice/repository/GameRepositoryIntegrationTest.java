package com.gamemarketplace.catalogservice.repository;

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

import com.gamemarketplace.catalogservice.entity.Game;

@SpringBootTest
@Testcontainers
class GameRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

    @Autowired
    private GameRepository gameRepository;

    @Test
    void shouldSaveAndRetrieveGame() {
        Game game = new Game("Cyberpunk 2077", "RPG", "CD Projekt", 39.99, "2020-12-10");

        Game savedGame = gameRepository.save(game);

        assertTrue(savedGame.getId() != null);

        Optional<Game> foundGame = gameRepository.findById(savedGame.getId());

        assertTrue(foundGame.isPresent());
        assertEquals("Cyberpunk 2077", foundGame.get().getTitle());
    }

    @Test
    void shouldFindGamesByGenre() {
        gameRepository.deleteAll();

        Game game1 = new Game("The Witcher 3", "RPG", "CD Projekt", 29.99, "2015-05-19");
        Game game2 = new Game("Portal 2", "Puzzle", "Valve", 9.99, "2011-04-19");
        gameRepository.save(game1);
        gameRepository.save(game2);

        List<Game> rpgGames = gameRepository.findByGenre("RPG");

        assertEquals(1, rpgGames.size());
        assertEquals("The Witcher 3", rpgGames.get(0).getTitle());
    }
}