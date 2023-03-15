package pl.pracinho.countrycitygame.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pracinho.countrycitygame.config.EndpointsConfig;
import pl.pracinho.countrycitygame.model.UnknownAnswerInputDto;
import pl.pracinho.countrycitygame.model.dto.AnswerDto;
import pl.pracinho.countrycitygame.model.dto.GameDto;
import pl.pracinho.countrycitygame.model.dto.UnknownAnswerDto;
import pl.pracinho.countrycitygame.model.enums.Category;
import pl.pracinho.countrycitygame.model.enums.GameStatus;
import pl.pracinho.countrycitygame.service.game.GameService;

import javax.websocket.server.PathParam;
import java.util.Set;

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
                           RedirectAttributes redirectAttr,
                           Authentication authentication) {

        GameDto game = gameService.findDtoById(gameId, authentication.getName());

        boolean allPlayersReady = gameService.checkAllPlayersReady(gameId);

        if (game.getRoundResults().isEmpty() || allPlayersReady) {
            boolean canPlay = gameService.canPlay(authentication.getName(), gameId);
            model.addAttribute("game", game);
            model.addAttribute("categories", Category.getCategoriesNames());
            if (canPlay) model.addAttribute("answerDto", new AnswerDto());
            return "game";
        }

        redirectAttr.addAttribute("gameId", gameId);
        redirectAttr.addAttribute("roundId", gameService.getLastRoundId(gameId));
        return "redirect:" + EndpointsConfig.UI_GAME_ROUND_SUMMARY;

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
        model.addAttribute("categories", Category.getCategoriesNames());
        return "fragments/board :: boardFrag";
    }

    @GetMapping(EndpointsConfig.UI_GAME_ROUND_SUMMARY)
    public String roundSummary(Authentication authentication,
                               Model model,
                               @PathVariable(value = "gameId") String gameId,
                               @PathVariable(value = "roundId") String roundId) {
        model.addAttribute("roundResult", gameService.getLastRoundSummary(gameId, roundId));
        model.addAttribute("categories", Category.getCategoriesNames());
        model.addAttribute("gamePlayers", gameService.getGamePlayers(gameId));
        model.addAttribute("gameId", gameId);
        model.addAttribute("playerReady", gameService.checkPlayerReady(gameId, authentication.getName()));
        model.addAttribute("playersReady", gameService.getReadyPlayers(gameId));
        return "round-summary";
    }

    @PostMapping(EndpointsConfig.UI_GAME_ROUND_READY)
    public String roundReady(Authentication authentication,
                             Model model,
                             @PathVariable(value = "gameId") String gameId,
                             @PathVariable(value = "roundId") String roundId) {
        gameService.roundReady(gameId, roundId, authentication.getName());

        model.addAttribute("gameId", gameId);
        model.addAttribute("roundId", roundId);
        return "redirect:" + EndpointsConfig.UI_GAME_ROUND_SUMMARY;
    }

    @GetMapping(EndpointsConfig.UI_GAME_ROUND_RELOAD_RESULTS)
    public String reloadRoundResult(Model model,
                                    @PathVariable(value = "roundId") String roundId,
                                    @PathVariable(value = "gameId") String gameId) {
        model.addAttribute("roundResult", gameService.getLastRoundSummary(gameId, roundId));
        model.addAttribute("categories", Category.getCategoriesNames());
        model.addAttribute("gamePlayers", gameService.getGamePlayers(gameId));
        model.addAttribute("gameId", gameId);
        model.addAttribute("playersReady", gameService.getReadyPlayers(gameId));

        return "fragments/round-result-table :: roundResultTable";
    }

    @GetMapping(EndpointsConfig.UI_GAME_UNKNOWN_ANSWERS)
    public String unknownAnswersPage(RedirectAttributes redirectAttr,
                                     Model model,
                                     Authentication authentication,
                                     @PathVariable(value = "gameId") String gameId) {
        Set<UnknownAnswerDto> unknownAnswers = gameService.getUnknownAnswers(gameId);
        if (unknownAnswers.isEmpty()) {
            redirectAttr.addAttribute("gameId", gameId);
            return "redirect:" + EndpointsConfig.UI_GAME_PAGE;
        }

        model.addAttribute("unknownAnswerInput", new UnknownAnswerInputDto(unknownAnswers.stream().toList()));
        model.addAttribute("gameId", gameId);
        model.addAttribute("roundId", gameService.getLastRoundId(gameId));
        model.addAttribute("completed", gameService.checkPlayerCompetedUnknownAnswersVerification(authentication.getName(), gameId));

        return "unknown-answers";
    }

    @PostMapping(EndpointsConfig.UI_GAME_UNKNOWN_ANSWERS_CONFIRM)
    public String unknownAnswersConfirm(UnknownAnswerInputDto unknownAnswerInput,
                                        Authentication authentication,
                                        @PathVariable(value = "gameId") String gameId) {
        gameService.confirmUnknownAnswers(gameId, unknownAnswerInput.getUnknownAnswers(), authentication.getName());
        return "redirect:" + EndpointsConfig.UI_GAME_UNKNOWN_ANSWERS;
    }
}