package com.gamemarketplace.catalogservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamemarketplace.catalogservice.entity.Game;
import com.gamemarketplace.catalogservice.repository.GameRepository;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(String id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
    }

    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    public Game updateGame(String id, Game updatedGame) {
        Game existingGame = getGameById(id);
        existingGame.setTitle(updatedGame.getTitle());
        existingGame.setGenre(updatedGame.getGenre());
        existingGame.setPublisher(updatedGame.getPublisher());
        existingGame.setPrice(updatedGame.getPrice());
        existingGame.setReleaseDate(updatedGame.getReleaseDate());
        return gameRepository.save(existingGame);
    }

    public void deleteGame(String id) {
        gameRepository.deleteById(id);
    }

    public List<Game> getGamesByGenre(String genre) {
        return gameRepository.findByGenre(genre);
    }
}