package com.gamemarketplace.storecompositeservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gamemarketplace.storecompositeservice.dto.GameDetailsDto;
import com.gamemarketplace.storecompositeservice.dto.OrderDto;
import com.gamemarketplace.storecompositeservice.dto.OwnedGameDto;
import com.gamemarketplace.storecompositeservice.service.StoreCompositeService;

@RestController
@RequestMapping("/api/store")
public class StoreCompositeController {

    private final StoreCompositeService storeCompositeService;

    public StoreCompositeController(StoreCompositeService storeCompositeService) {
        this.storeCompositeService = storeCompositeService;
    }

    @GetMapping("/games/{gameId}")
    public GameDetailsDto getGameDetails(@PathVariable String gameId, @RequestParam String userId) {
        return storeCompositeService.getGameDetails(gameId, userId);
    }

    @GetMapping("/library/{userId}")
    public OwnedGameDto[] getUserLibrary(@PathVariable String userId) {
        return storeCompositeService.getUserLibrary(userId);
    }

    @PostMapping("/purchase")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto purchaseGame(@RequestParam String userId, @RequestParam String gameId) {
        return storeCompositeService.purchaseGame(userId, gameId);
    }
}
