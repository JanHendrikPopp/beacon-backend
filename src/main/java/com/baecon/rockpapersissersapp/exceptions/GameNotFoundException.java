package com.baecon.rockpapersissersapp.exceptions;

public class GameNotFoundException extends Exception {

    private long gameId;

    public GameNotFoundException(long gameId) {
        this.gameId = gameId;
    }

    public String getMessage() {
        return "Could not find user for user id: " + gameId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
