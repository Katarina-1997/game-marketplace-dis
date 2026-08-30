package com.gamemarketplace.inventoryservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gamemarketplace.inventoryservice.entity.OwnedGame;
import com.gamemarketplace.inventoryservice.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/user/{userId}")
    public List<OwnedGame> getGamesByUser(@PathVariable String userId) {
        return inventoryService.getGamesByUser(userId);
    }

    @GetMapping("/user/{userId}/owns/{gameId}")
    public boolean userOwnsGame(@PathVariable String userId, @PathVariable String gameId) {
        return inventoryService.userOwnsGame(userId, gameId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnedGame addGameToLibrary(@RequestBody OwnedGame ownedGame) {
        return inventoryService.addGameToLibrary(ownedGame);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeGameFromLibrary(@PathVariable String id) {
        inventoryService.removeGameFromLibrary(id);
    }
}