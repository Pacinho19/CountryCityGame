package pl.pracinho.countrycitygame.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.pracinho.countrycitygame.model.dto.AnswerDto;
import pl.pracinho.countrycitygame.model.dto.GameDto;
import pl.pracinho.countrycitygame.model.dto.RoundResultDto;
import pl.pracinho.countrycitygame.model.dto.UnknownAnswerDto;
import pl.pracinho.countrycitygame.model.dto.mapper.GameDtoMapper;
import pl.pracinho.countrycitygame.model.entity.memory.Answer;
import pl.pracinho.countrycitygame.model.entity.memory.Game;
import pl.pracinho.countrycitygame.model.entity.memory.Player;
import pl.pracinho.countrycitygame.model.enums.Category;
import pl.pracinho.countrycitygame.model.enums.GameStatus;
import pl.pracinho.countrycitygame.repository.game.GameRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameLogicService gameLogicService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public List<GameDto> getAvailableGames() {
        return gameRepository.getAvailableGames();
    }

    public String newGame(String name, int playersCount) {
        List<GameDto> activeGames = getAvailableGames();
        if (activeGames.size() >= 10)
            throw new IllegalStateException("Cannot create new Game! Active game count : " + activeGames.size());

        String gameId = gameRepository.newGame(name, playersCount);
        simpMessagingTemplate.convertAndSend("/game-created", true);
        return gameId;
    }

    public GameDto findDtoById(String gameId, String name) {
        return GameDtoMapper.parse(gameLogicService.findById(gameId));
    }

    public void joinGame(String name, String gameId) throws IllegalStateException {
        Game game = gameRepository.joinGame(name, gameId);
        if (game.getPlayers().size() == game.getPlayersCount()) game.setStatus(GameStatus.IN_PROGRESS);
    }

    public boolean checkStartGame(String gameId) {
        Game game = gameLogicService.findById(gameId);
        boolean startGame = game.getPlayers().size() == game.getPlayersCount();

        if (startGame)
            game.setLetter(gameLogicService.drawLetter(game.getUsedLetters()));

        return startGame;
    }

    public boolean canJoin(GameDto game, String name) {
        return game.getPlayers().size() < game.getPlayersCount() && game.getPlayers().stream().noneMatch(p -> p.equals(name));
    }

    public void answer(String playerName, String gameId, AnswerDto answerDto) {
        Game game = gameLogicService.findById(gameId);
        Player player = gameLogicService.getPlayerFromGame(playerName, game.getPlayers());

        if (!gameLogicService.checkPlayerCanAnswer(game, player)) return;

        gameLogicService.upperCaseAnswer(answerDto.getAnswers());

        game.addAnswer(
                new Answer(answerDto.getAnswers(), player)
        );

        List<UnknownAnswerDto> unknownAnswers = getUnknownAnswers(game.getLetter(), answerDto.getAnswers());
        if (!unknownAnswers.isEmpty()) game.addUnknownAnswers(unknownAnswers);

        if (gameLogicService.allPlayersFinished(game) && game.getUnknownAnswers().isEmpty())
            nextRound(game);
        else if (gameLogicService.allPlayersFinished(game) && !game.getUnknownAnswers().isEmpty())
            simpMessagingTemplate.convertAndSend("/unknown-answers/" + game.getId(), true);
        else
            simpMessagingTemplate.convertAndSend("/reload-board/" + game.getId(), playerName);
    }

    private List<UnknownAnswerDto> getUnknownAnswers(Character letter, Map<Category, String> answers) {
        return answers.entrySet()
                .stream()
                .filter(entry -> entry.getKey().check(letter, entry.getValue()) == null)
                .map(entry -> new UnknownAnswerDto(entry.getKey(), entry.getValue()))
                .toList();
    }

    private void nextRound(Game game) {
        game.getUnknownAnswersResult().clear();
        game.getUnknownAnswers().clear();

        gameLogicService.checkAnswers(game);
        simpMessagingTemplate.convertAndSend("/round-summary/" + game.getId(), gameLogicService.getLastRoundResult(game).getId());
    }

    public boolean canPlay(String playerName, String gameId) {
        Game game = gameLogicService.findById(gameId);
        return game.getAnswers().get(game.getLetter())
                .stream()
                .noneMatch(a -> a.player().getName().equals(playerName));
    }

    public RoundResultDto getLastRoundSummary(String gameId, String roundId) {
        Game game = gameLogicService.findById(gameId);
        RoundResultDto roundResultDto = gameLogicService.getLastRoundResult(game);

        if (!roundResultDto.getId().equals(roundId))
            throw new IllegalStateException("Invalid round id: " + roundId + " for game " + gameId);

        return roundResultDto;
    }

    public LinkedList<Player> getGamePlayers(String gameId) {
        Game game = gameLogicService.findById(gameId);
        return game.getPlayers();
    }

    public void roundReady(String gameId, String roundId, String playerName) {
        Game game = gameLogicService.findById(gameId);
        RoundResultDto lastRoundResult = gameLogicService.getLastRoundResult(game);

        if (!lastRoundResult.getId().equals(roundId))
            throw new IllegalStateException("Invalid round id: " + roundId + " for game " + gameId);

        lastRoundResult.getReadyPlayers().add(playerName);

        boolean allPlayersReady = gameLogicService.allPlayersReady(lastRoundResult, game.getPlayers());
        if (!allPlayersReady) {
            simpMessagingTemplate.convertAndSend("/player-ready-alert/" + game.getId() + "/round/" + roundId, true);
            return;
        }

        game.setLetter(gameLogicService.drawLetter(game.getUsedLetters()));
        simpMessagingTemplate.convertAndSend("/next-round/" + game.getId(), true);
    }

    public boolean checkPlayerReady(String gameId, String name) {
        Game game = gameLogicService.findById(gameId);
        return gameLogicService.checkPlayerReady(game, name);
    }

    public boolean checkAllPlayersReady(String gameId) {
        Game game = gameLogicService.findById(gameId);
        return gameLogicService.allPlayersReady(gameLogicService.getLastRoundResult(game), game.getPlayers());
    }

    public String getLastRoundId(String gameId) {
        Game game = gameLogicService.findById(gameId);
        return gameLogicService.getLastRoundId(game);
    }

    public List<String> getReadyPlayers(String gameId) {
        Game game = gameLogicService.findById(gameId);
        return gameLogicService.getReadyPlayers(game);
    }

    public Set<UnknownAnswerDto> getUnknownAnswers(String gameId) {
        Game game = gameLogicService.findById(gameId);
        return game.getUnknownAnswers();
    }

    public void confirmUnknownAnswers(String gameId, List<UnknownAnswerDto> unknownAnswers, String playerName) {
        Game game = gameLogicService.findById(gameId);

        boolean roundSummary = gameLogicService.confirmUnknownAnswers(game, unknownAnswers, playerName);
        if (roundSummary)
            nextRound(game);
    }

    public boolean checkPlayerCompetedUnknownAnswersVerification(String name, String gameId) {
        Game game = gameLogicService.findById(gameId);
        return game.getUnknownAnswersResult().stream()
                .anyMatch(uar -> uar.playerName().equals(name));
    }
}
