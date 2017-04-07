package com.baecon.rockpapersissersapp.exceptions;

public class DetermineRoundResultException extends Exception {

    private long userId;

    private long gameId;

    private String message;

    public DetermineRoundResultException(long userId, long gameId, String message) {
        this.userId = userId;
        this.gameId = gameId;
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
