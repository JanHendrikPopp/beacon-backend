package com.baecon.rockpapersissersapp.util;

/**
 * API endpoint constants.
 */
public final class ApiConstants {

    public static final String API_VERSION = "/api/1";
    public static final String REGISTRATION = API_VERSION + "/registration";
    public static final String MOVE = API_VERSION + "/move";
    public static final String GAME = API_VERSION + "/game/{gameId}/{playerId}";
    public static final String STATS = API_VERSION + "/stats/{playerId}";

}
