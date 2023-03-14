package pl.pracingo.countrycitygame.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pracingo.countrycitygame.config.EndpointsConfig;
import pl.pracingo.countrycitygame.model.dto.AnswerDto;
import pl.pracingo.countrycitygame.model.dto.GameDto;
import pl.pracingo.countrycitygame.model.enums.Category;
import pl.pracingo.countrycitygame.model.enums.GameStatus;
import pl.pracingo.countrycitygame.service.game.GameService;

import javax.websocket.server.PathParam;

@RequiredArgsConstructor
@Controller
public class GameController {

    private final GameService gameService;

    @GetMapping(EndpointsConfig.UI_PREFIX)
    public String gameHome(Model model) {
        return "home";
    }

    @PostMapping(EndpointsConfig.UI_GAMES)
    public String availableGames(Model model) {
        model.addAttribute("games", gameService.getAvailableGames());
        return "fragments/available-games :: availableGamesFrag";
    }

    @PostMapping(EndpointsConfig.UI_NEW_GAME)
    public String newGame(Model model, @PathParam("playersCount") int playersCount, Authentication authentication) {
        try {
            return "redirect:" + EndpointsConfig.UI_GAMES + "/" + gameService.newGame(authentication.getName(), playersCount) + "/room";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return gameHome(model);
        }
    }

    @GetMapping(EndpointsConfig.UI_GAME_ROOM)
    public String gameRoom(@PathVariable(value = "gameId") String gameId, Model model, Authentication authentication) {
        try {
            GameDto game = gameService.findDtoById(gameId, authentication.getName());
            if (game.getStatus() == GameStatus.IN_PROGRESS)
                return "redirect:" + EndpointsConfig.UI_GAMES + "/" + gameId;
            if (game.getStatus() == GameStatus.FINISHED)
                throw new IllegalStateException("Game " + gameId + " finished!");

            model.addAttribute("game", game);
            model.addAttribute("joinGame", gameService.canJoin(game, authentication.getName()));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return gameHome(model);
        }
        return "game-room";
    }

    @PostMapping(EndpointsConfig.UI_GAME_ROOM_PLAYERS)
    public String players(@PathVariable(value = "gameId") String gameId,
                          Model model,
                          Authentication authentication) {
        GameDto game = gameService.findDtoById(gameId, authentication.getName());
        model.addAttribute("game", game);
        return "fragments/game-players :: gamePlayersFrag";
    }

    @GetMapping(EndpointsConfig.UI_GAME_PAGE)
    public String gamePage(@PathVariable(value = "gameId") String gameId,
                           Model model,
                           Authentication authentication) {
        GameDto game = gameService.findDtoById(gameId, authentication.getName());
        boolean canPlay = gameService.canPlay(authentication.getName(), gameId);
        model.addAttribute("game", game);
        model.addAttribute("categories", Category.getCategoriesNames());
        if(canPlay) model.addAttribute("answerDto", new AnswerDto());
        return "game";
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping(EndpointsConfig.UI_GAME_ANSWER)
    public void answer(Authentication authentication,
                       @PathVariable(value = "gameId") String gameId,
                       AnswerDto answerDto) {
        gameService.answer(authentication.getName(), gameId, answerDto);
    }

    @GetMapping(EndpointsConfig.UI_GAME_BOARD_RELOAD)
    public String reloadBoard(Authentication authentication,
                              Model model,
                              @PathVariable(value = "gameId") String gameId) {
        model.addAttribute("game", gameService.findDtoById(gameId, authentication.getName()));
        return "fragments/board :: boardFrag";
    }
}