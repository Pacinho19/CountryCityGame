package pl.pracingo.countrycitygame.model.dto.mapper;

import pl.pracingo.countrycitygame.model.dto.GameDto;
import pl.pracingo.countrycitygame.model.entity.memory.Game;
import pl.pracingo.countrycitygame.model.entity.memory.Player;

public class GameDtoMapper {

    public static GameDto parse(Game game) {
        return GameDto.builder()
                .id(game.getId())
                .startTime(game.getStartTime())
                .players(game.getPlayers().stream().map(Player::getName).toList())
                .status(game.getStatus())
                .playersCount(game.getPlayersCount())
                .currentLetter(game.getLetter())
                .build();
    }
}