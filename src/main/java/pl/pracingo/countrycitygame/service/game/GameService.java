package pl.pracingo.countrycitygame.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.pracingo.countrycitygame.model.dto.AnswerDto;
import pl.pracingo.countrycitygame.model.dto.GameDto;
import pl.pracingo.countrycitygame.model.dto.mapper.GameDtoMapper;
import pl.pracingo.countrycitygame.model.entity.memory.Answer;
import pl.pracingo.countrycitygame.model.entity.memory.Game;
import pl.pracingo.countrycitygame.model.entity.memory.Player;
import pl.pracingo.countrycitygame.model.enums.GameStatus;
import pl.pracingo.countrycitygame.repository.game.GameRepository;

import java.util.List;

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
        return gameRepository.newGame(name, playersCount);
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

        if(!gameLogicService.checkPlayerCanAnswer(game, player)) return;

        game.addAnswer(
                new Answer(answerDto.getAnswers(), player)
        );

        if (gameLogicService.allPlayersFinished(game))
            nextRound(game);
        else
            simpMessagingTemplate.convertAndSend("/reload-board/" + game.getId(), playerName);
    }

    private void nextRound(Game game) {
        gameLogicService.checkAnswers(game);
        game.setLetter(gameLogicService.drawLetter(game.getUsedLetters()));
        simpMessagingTemplate.convertAndSend("/next-round/" + game.getId(), true);
    }

    public boolean canPlay(String playerName, String gameId) {
        Game game = gameLogicService.findById(gameId);
        return game.getAnswers().get(game.getLetter())
                .stream()
                .noneMatch(a -> a.player().getName().equals(playerName));
    }
}
