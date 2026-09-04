package com.gamemarketplace.paymentservice.event;

public class OrderCreatedEvent {

    private String orderId;
    private String userId;
    private String gameId;
    private String gameTitle;
    private double amount;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, String userId, String gameId, String gameTitle, double amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}