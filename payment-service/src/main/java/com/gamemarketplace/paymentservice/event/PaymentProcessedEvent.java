package com.gamemarketplace.paymentservice.event;

public class PaymentProcessedEvent {

    private String orderId;
    private String userId;
    private String gameId;
    private String gameTitle;
    private String status;

    public PaymentProcessedEvent() {
    }

    public PaymentProcessedEvent(String orderId, String userId, String gameId, String gameTitle, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}