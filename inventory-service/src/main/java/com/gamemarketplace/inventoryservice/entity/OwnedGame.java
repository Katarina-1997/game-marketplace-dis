package com.gamemarketplace.inventoryservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "owned_games")
public class OwnedGame {

    @Id
    private String id;

    private String userId;
    private String gameId;
    private String gameTitle;
    private String purchaseDate;

    public OwnedGame() {
    }

    public OwnedGame(String userId, String gameId, String gameTitle, String purchaseDate) {
        this.userId = userId;
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.purchaseDate = purchaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
