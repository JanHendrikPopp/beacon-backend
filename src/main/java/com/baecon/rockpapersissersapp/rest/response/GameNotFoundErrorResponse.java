package com.baecon.rockpapersissersapp.rest.response;

public class GameNotFoundErrorResponse extends ErrorResponse {

    private long gameId;

    public GameNotFoundErrorResponse() {
        super();
    }

    public GameNotFoundErrorResponse(String errorCode, String errorMessage, long gameId) {
        super(errorCode, errorMessage);
        this.gameId = gameId;
    }

    public GameNotFoundErrorResponse(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
