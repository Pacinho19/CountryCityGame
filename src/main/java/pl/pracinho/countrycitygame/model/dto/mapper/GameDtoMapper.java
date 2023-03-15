package pl.pracinho.countrycitygame.model.dto.mapper;

import pl.pracinho.countrycitygame.model.dto.GameDto;
import pl.pracinho.countrycitygame.model.entity.memory.Game;
import pl.pracinho.countrycitygame.model.entity.memory.Player;

public class GameDtoMapper {

    public static GameDto parse(Game game) {
        return GameDto.builder()
                .id(game.getId())
                .startTime(game.getStartTime())
                .players(game.getPlayers().stream().map(Player::getName).sorted().toList())
                .status(game.getStatus())
                .playersCount(game.getPlayersCount())
                .currentLetter(game.getLetter())
                .roundResults(game.getResults())
                .build();
    }
}