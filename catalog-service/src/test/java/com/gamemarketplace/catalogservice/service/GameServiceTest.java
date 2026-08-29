package com.gamemarketplace.catalogservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gamemarketplace.catalogservice.entity.Game;
import com.gamemarketplace.catalogservice.repository.GameRepository;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService(gameRepository);
    }

    @Test
    void getAllGames_shouldReturnListOfGames() {
        Game game1 = new Game("The Witcher 3", "RPG", "CD Projekt", 29.99, "2015-05-19");
        Game game2 = new Game("Portal 2", "Puzzle", "Valve", 9.99, "2011-04-19");
        when(gameRepository.findAll()).thenReturn(Arrays.asList(game1, game2));

        List<Game> result = gameService.getAllGames();

        assertEquals(2, result.size());
        verify(gameRepository).findAll();
    }

    @Test
    void getGameById_shouldReturnGame_whenGameExists() {
        Game game = new Game("The Witcher 3", "RPG", "CD Projekt", 29.99, "2015-05-19");
        game.setId("1");
        when(gameRepository.findById("1")).thenReturn(Optional.of(game));

        Game result = gameService.getGameById("1");

        assertEquals("The Witcher 3", result.getTitle());
    }

    @Test
    void createGame_shouldSaveAndReturnGame() {
        Game game = new Game("Portal 2", "Puzzle", "Valve", 9.99, "2011-04-19");
        when(gameRepository.save(game)).thenReturn(game);

        Game result = gameService.createGame(game);

        assertEquals("Portal 2", result.getTitle());
        verify(gameRepository).save(game);
    }
}