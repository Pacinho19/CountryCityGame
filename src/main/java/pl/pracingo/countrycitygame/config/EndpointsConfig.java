package pl.pracingo.countrycitygame.config;

public class EndpointsConfig {

//    ================= API =================

    public static final String API_PREFIX = "/api";
    public static final String API_CITY = API_PREFIX+ "/city";
    public static final String API_COUNTRY = API_PREFIX+ "/country";

//    ================= UI =================
    public static final String UI_PREFIX = "/country-city-game";
    public static final String UI_GAMES = UI_PREFIX + "/games";
    public static final String UI_NEW_GAME = UI_GAMES + "/new";
    public static final String UI_GAME_PAGE = UI_GAMES + "/{gameId}";
    public static final String UI_GAME_ROOM = UI_GAME_PAGE + "/room";
    public static final String UI_GAME_ROOM_PLAYERS = UI_GAME_ROOM + "/players";
}