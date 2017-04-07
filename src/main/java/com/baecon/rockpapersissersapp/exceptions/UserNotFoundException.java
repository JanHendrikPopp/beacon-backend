package com.baecon.rockpapersissersapp.exceptions;

public class UserNotFoundException extends Exception {

    private long userId;

    public UserNotFoundException(long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return "Could not find user for user id: " + userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
