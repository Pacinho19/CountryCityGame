package pl.pracinho.countrycitygame.config;

public class EndpointsConfig {

//    ================= API =================

    public static final String API_PREFIX = "/api";
    public static final String API_CITY = API_PREFIX + "/city";
    public static final String API_COUNTRY = API_PREFIX + "/country";

    //    ================= UI =================
    public static final String UI_PREFIX = "/country-city-game";
    public static final String UI_GAMES = UI_PREFIX + "/games";
    public static final String UI_NEW_GAME = UI_GAMES + "/new";
    public static final String UI_GAME_PAGE = UI_GAMES + "/{gameId}";
    public static final String UI_GAME_ROOM = UI_GAME_PAGE + "/room";
    public static final String UI_GAME_ROOM_PLAYERS = UI_GAME_ROOM + "/players";
    public static final String UI_GAME_ANSWER = UI_GAME_PAGE + "/answer";
    public static final String UI_GAME_BOARD = UI_GAME_PAGE + "/board";
    public static final String UI_GAME_BOARD_RELOAD = UI_GAME_BOARD + "/reload";
    public static final String UI_GAME_ROUND = UI_GAME_PAGE + "/round/{roundId}";
    public static final String UI_GAME_ROUND_SUMMARY = UI_GAME_ROUND + "/summary";
    public static final String UI_GAME_ROUND_READY = UI_GAME_ROUND + "/ready";
    public static final String UI_GAME_ROUND_RELOAD_RESULTS = UI_GAME_ROUND + "/reload-results";
    public static final String UI_GAME_UNKNOWN_ANSWERS = UI_GAME_PAGE + "/unknown-answers";
    public static final String UI_GAME_UNKNOWN_ANSWERS_CONFIRM = UI_GAME_UNKNOWN_ANSWERS + "/confirm";
}