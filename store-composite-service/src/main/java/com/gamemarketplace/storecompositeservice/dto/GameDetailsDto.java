package com.gamemarketplace.storecompositeservice.dto;

public class GameDetailsDto {

    private GameDto game;
    private boolean ownedByUser;

    public GameDetailsDto() {
    }

    public GameDetailsDto(GameDto game, boolean ownedByUser) {
        this.game = game;
        this.ownedByUser = ownedByUser;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public boolean isOwnedByUser() {
        return ownedByUser;
    }

    public void setOwnedByUser(boolean ownedByUser) {
        this.ownedByUser = ownedByUser;
    }
}
