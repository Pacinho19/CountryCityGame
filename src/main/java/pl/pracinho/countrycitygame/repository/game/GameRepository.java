package pl.pracinho.countrycitygame.repository.game;

import org.springframework.stereotype.Repository;
import pl.pracinho.countrycitygame.exception.GameNotFoundException;
import pl.pracinho.countrycitygame.model.dto.GameDto;
import pl.pracinho.countrycitygame.model.dto.mapper.GameDtoMapper;
import pl.pracinho.countrycitygame.model.entity.memory.Game;
import pl.pracinho.countrycitygame.model.entity.memory.Player;
import pl.pracinho.countrycitygame.model.enums.GameStatus;

import java.util.*;

@Repository
public class GameRepository {

    private Map<String, Game> gameMap;

    public GameRepository() {
        gameMap = new HashMap<>();
    }

    public String newGame(String playerName, int playersCount) {
        Game game = new Game(playerName, playersCount);
        gameMap.put(game.getId(), game);
        return game.getId();
    }

    public List<GameDto> getAvailableGames() {
        return gameMap.values()
                .stream()
                .filter(game -> game.getStatus() != GameStatus.FINISHED)
                .map(GameDtoMapper::parse)
                .sorted(Comparator.comparing(GameDto::getStartTime).reversed())
                .toList();
    }

    public Optional<Game> findById(String gameId) {
        return Optional.ofNullable(gameMap.get(gameId));
    }

    public Game joinGame(String name, String gameId) throws IllegalStateException {
        Game game = gameMap.get(gameId);
        if (game == null)
            throw new GameNotFoundException(gameId);

        if (game.getStatus() != GameStatus.NEW)
            throw new IllegalStateException("Cannot join to " + gameId + ". Game status : " + game.getStatus());

        if (game.getPlayers().get(0).getName().equals(name))
            throw new IllegalStateException("Game " + gameId + " was created by you!");

        game.getPlayers().add(new Player(name, game.getPlayers().size() + 1));
        return game;
    }

}
