package pl.pracingo.countrycitygame.config;

public class EndpointsConfig {

//    ================= API =================

    public static final String API_PREFIX = "/api";
    public static final String API_CITY = API_PREFIX+ "/city";
    public static final String API_COUNTRY = API_PREFIX+ "/country";

//    ================= UI =================
    public static final String PREFIX = "/country-city-game";
    public static final String GAMES = PREFIX + "/games";
    public static final String NEW_GAME = GAMES + "/new";
    public static final String GAME_PAGE = GAMES + "/{gameId}";
    public static final String GAME_ROOM = GAME_PAGE + "/room";
    public static final String PLAYERS = GAME_ROOM + "/players";

}