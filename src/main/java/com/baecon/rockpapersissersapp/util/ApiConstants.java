package com.baecon.rockpapersissersapp.util;

/**
 * API endpoint constants.
 */
public final class ApiConstants {

    public static final String API_VERSION = "/api/1";
    public static final String REGISTRATION = API_VERSION + "/registration";
    public static final String MOVE = API_VERSION + "/move";
    public static final String GAME = API_VERSION + "/game/{gameId}/{playerId}";
    public static final String LOAD_GAME = API_VERSION + "/game/{gameId}";
    public static final String NEW_GAME = API_VERSION + "/game/newGame";
    public static final String ALL_GAMES = API_VERSION + "/allgamesforplayer/{playerId}";
    public static final String STATS = API_VERSION + "/stats/{playerId}";
    public static final String VALID_BEACON = API_VERSION + "/isvalidbeacon/{beaconId}";

}
