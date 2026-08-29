package com.gamemarketplace.catalogservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gamemarketplace.catalogservice.entity.Game;

public interface GameRepository extends MongoRepository<Game, String> {

    List<Game> findByGenre(String genre);

    List<Game> findByPublisher(String publisher);
}