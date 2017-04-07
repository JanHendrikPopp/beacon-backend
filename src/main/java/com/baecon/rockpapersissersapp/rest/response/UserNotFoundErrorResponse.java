package com.baecon.rockpapersissersapp.rest.response;

public class UserNotFoundErrorResponse extends ErrorResponse {

    private long userId;

    public UserNotFoundErrorResponse() {
        super();
    }

    public UserNotFoundErrorResponse(String errorCode, String errorMessage, long userId) {
        super(errorCode, errorMessage);
        this.userId = userId;
    }

    public UserNotFoundErrorResponse(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
